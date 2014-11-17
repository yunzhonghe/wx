package com.dragon.spider.api;

import com.dragon.spider.api.config.ApiConfig;
import com.dragon.spider.api.response.BaseResponse;
import com.dragon.spider.api.response.GetTokenResponse;
import com.dragon.spider.util.BeanUtil;
import com.dragon.spider.util.JSONUtil;
import com.dragon.spider.util.NetWorkCenter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * API基类，提供一些通用方法
 * 包含自动刷新token、通用get post请求等
 * @author peiyu
 */
public abstract class BaseAPI {
    protected static final String BASE_API_URL = "https://api.weixin.qq.com/";

    protected static final String SYS_BUSY = "-1";

    protected static final String SUCCESS = "0";

    protected static final String TOKEN_ERROR = "40001";

    protected static final String TOKEN_TYPE_ERROR = "40002";

    protected static final String TOKEN_TIMEOUT = "42001";

    protected final ApiConfig config;

    //用于刷新token时锁住config，防止多次刷新
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    public BaseAPI(ApiConfig config) {
        this.config = config;
       /* if(!config.refreshing){
        	refreshToken();
        }*/
    }

    /**
     * 刷新token
     */
    protected void refreshToken() {
        writeLock.lock();
        try {
            config.refreshing = true;
            String url = BASE_API_URL + "cgi-bin/token?grant_type=client_credential&appid=" + this.config.getAppid() + "&secret=" + this.config.getSecret();
            NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
                public void onResponse(int resultCode, String resultJson) {
                    if (200 == resultCode) {
                        GetTokenResponse response = JSONUtil.toBean(resultJson, GetTokenResponse.class);
                        String token = response.getAccess_token();
                        System.out.println("token"+ token);
                        BaseAPI.this.config.setAccess_token(token);
                    }
                }
            });
        } finally {
            config.refreshing = false;
            writeLock.unlock();
        }
    }

    /**
     * 通用post请求
     * @param url 地址，其中token用#代替
     * @param json 参数，json格式
     * @return 请求结果
     */
    protected BaseResponse executePost(String url, String json) {
        BaseResponse response = null;
        BeanUtil.requireNonNull(url, "url is null");

        if(null == config.getAccess_token()) {
            refreshToken();
        }
        readLock.lock();
        try {
           //需要传token
           url = url.replace("#",config.getAccess_token());
            response = NetWorkCenter.post(url, json);
        } finally {
            readLock.unlock();
        }

        if(null == response || TOKEN_TIMEOUT.equals(response.getErrcode())) {
            if(!config.refreshing) {
                refreshToken();
            }

            readLock.lock();
            try {
                response = NetWorkCenter.post(url, json);
            } finally {
                readLock.unlock();
            }
        }

        return response;
    }

    /**
     * 通用post请求
     * @param url 地址，其中token用#代替
     * @return 请求结果
     */
    protected BaseResponse executeGet(String url) {
        BaseResponse response = null;
        BeanUtil.requireNonNull(url, "url is null");

        if(null == config.getAccess_token()) {
            refreshToken();
        }
        readLock.lock();
        try {
            //需要传token
            url = url.replace("#",config.getAccess_token());
            response = NetWorkCenter.get(url);
        } finally {
            readLock.unlock();
        }

        if(null == response || TOKEN_TIMEOUT.equals(response.getErrcode())) {
            if (!config.refreshing) {
                refreshToken();
            }
            readLock.lock();
            try {
                response = NetWorkCenter.get(url);
            } finally {
                readLock.unlock();
            }
        }
        return response;
    }
}
