package com.dragon.spider.api.request;

import com.dragon.spider.api.entity.Model;
import com.dragon.spider.util.JSONUtil;

/**
 * @author peiyu
 */
public class BaseRequest implements Model {

    public final String toJsonString() {
        return JSONUtil.toJson(this);
    }
}
