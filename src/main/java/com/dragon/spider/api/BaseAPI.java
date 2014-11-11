package com.dragon.spider.api;

import com.dragon.spider.api.config.ApiConfig;
import com.dragon.spider.api.response.BaseResponse;
import com.dragon.spider.api.response.GetTokenResponse;
import com.dragon.spider.util.BeanUtil;
import com.dragon.spider.util.JSONUtil;
import com.dragon.spider.util.NetWorkCenter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
    }

    public static HttpClient client = new DefaultHttpClient();	
    
    /**
     * 
     */
    protected void refreshToken() {
        writeLock.lock();
        try {
            config.refreshing = true;
            try {
				String token  = getTokenByLogins(config.getName(),config.getPassword());
				if(token ==null){
					return;
				}else{
					 BaseAPI.this.config.setAccess_token(token);
				}
			} catch (Exception e) {			
				e.printStackTrace();
			}
        } finally {
            config.refreshing = false;
            writeLock.unlock();
        }
    }
    
    /**MD5加密
	 * @param 加密前密码
	 * @return 加密后密码
	 * */
	private String MD5Encode(String password) {
		MessageDigest md5 = null;
		byte[] bs = password.getBytes();
		StringBuffer buffer = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(bs);
			bs = md5.digest();
			for (int i = 0; i < bs.length; i++) {

				if ((bs[i] & 0xff) < 0x10) {
					buffer.append("0");
				}
				buffer.append(Long.toString(bs[i] & 0xff, 16));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 获取Token
	 * @param reposne返回的数据
	 * @return Token
	 * */
	private String getToken(String response){
		String [] bs=response.split("&token=")[1].split("','");	
		String token=bs[0].split(",")[0];
		token=token.substring(0, token.length()-2);
		return token;
	}
	
	/**
	 * 请求登入
	 * @param 用户名,加密后密码,验证码
	 * @return  reposne返回的数据,包含Token的String字段
	 * */
	private String getTokenByLogins(String name,String pwd)throws Exception{
		String resultCont="";
		// 请求登录
		HttpPost post = new HttpPost("https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN");
		post.addHeader("Referer", "https://mp.weixin.qq.com/");
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("f", "json"));
		nvp.add(new BasicNameValuePair("pwd", pwd));
		nvp.add(new BasicNameValuePair("username", name));
		post.setEntity(new UrlEncodedFormEntity(nvp));
		resultCont=EntityUtils.toString(client.execute(post).getEntity());	
		String token = getToken(resultCont);
		System.out.println(token);		
		return token;
	}

	
    /**
     * 刷新token
     */
    /*protected void refreshToken() {
        writeLock.lock();
        try {
            config.refreshing = true;
            String url = BASE_API_URL + "cgi-bin/token?grant_type=client_credential&appid=" + this.config.getAppid() + "&secret=" + this.config.getSecret();
            NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
                @Override
                public void onResponse(int resultCode, String resultJson) {
                    if (200 == resultCode) {
                        GetTokenResponse response = JSONUtil.toBean(resultJson, GetTokenResponse.class);
                        BaseAPI.this.config.setAccess_token(response.getAccess_token());
                    }
                }
            });
        } finally {
            config.refreshing = false;
            writeLock.unlock();
        }
    }*/

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
