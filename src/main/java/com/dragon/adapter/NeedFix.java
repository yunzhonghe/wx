package com.dragon.adapter;

import com.dragon.apps.web.controller.BaseController;
import com.dragon.spider.api.BaseAPI;
import com.dragon.spider.api.config.ApiConfig;

public class NeedFix {
	public static ApiConfig getApiConfig(){
		//FIXME
		return null;
	}
	public static Long getApiAccountId(BaseAPI api){
		//FIXME 
		return BaseController.gerCurUser().getWxAccountId();
//		return null;
	}
}
