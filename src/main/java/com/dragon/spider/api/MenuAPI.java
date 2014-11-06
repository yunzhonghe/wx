package com.dragon.spider.api;

import com.dragon.spider.api.config.ApiConfig;
import com.dragon.spider.api.request.MenuRequest;
import com.dragon.spider.api.response.BaseResponse;
import com.dragon.spider.api.response.GetMenuResponse;
import com.dragon.spider.util.BeanUtil;
import com.dragon.spider.util.JSONUtil;

/**
 * 菜单相关API
 * @author peiyu
 */
public class MenuAPI extends BaseAPI {
    public MenuAPI(ApiConfig config) {
        super(config);
    }

    /**
     * 创建菜单
     * @param request 请求对象
     */
    public void createMenu(MenuRequest request) {
        BeanUtil.requireNonNull(request, "request is null");
        String url = BASE_API_URL + "cgi-bin/menu/create?access_token=#";
        executePost(url, request.toJsonString());
    }

    /**
     * 获取所有菜单
     * @return 菜单列表对象
     */
    public GetMenuResponse getMenu() {
        GetMenuResponse response = null;
        String url = BASE_API_URL + "cgi-bin/menu/get?access_token=#";

        BaseResponse r = executeGet(url);
        if(null == r.getErrcode() || "".equals(r.getErrcode())) {
            response = JSONUtil.toBean(r.getErrmsg(), GetMenuResponse.class);
        }
        return response;
    }

    /**
     * 删除所有菜单
     */
    public void deleteMenu() {
        String url = BASE_API_URL + "cgi-bin/menu/delete?access_token=#";
        executeGet(url);
    }
}
