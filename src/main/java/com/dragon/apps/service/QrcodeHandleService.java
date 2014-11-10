package com.dragon.apps.service;

import com.dragon.spider.api.BaseAPI;
import com.dragon.spider.api.config.ApiConfig;
import com.dragon.spider.api.response.BaseResponse;
import com.dragon.spider.api.response.QrcodeResponse;
import com.dragon.spider.util.BeanUtil;
import com.dragon.spider.util.JSONUtil;

/**
 * 二维码相关API
 * @author peiyu
 */
public class QrcodeHandleService extends BaseAPI {
   	public QrcodeHandleService(ApiConfig config) {
        super(config);
    }

    /**
     * 创建二维码
     * @param actionName
     * @param actionInfo
     * @param expireSeconds
     * @return
     */
    public QrcodeResponse createQrcode(String actionName, String actionInfo, Integer expireSeconds) {
        BeanUtil.requireNonNull(actionName, "actionName is null");
        BeanUtil.requireNonNull(actionInfo, "actionInfo is null");

        QrcodeResponse response = null;
        String url = BASE_API_URL + "cgi-bin/qrcode/create?access_token=#";

        BaseResponse r = executePost(url, null);
        if(null == r.getErrcode() || "".equals(r.getErrcode())) {
            response = JSONUtil.toBean(r.getErrmsg(), QrcodeResponse.class);
        }
        return response;
    }
}
