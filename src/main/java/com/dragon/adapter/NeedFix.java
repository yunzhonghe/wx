package com.dragon.adapter;

import com.dragon.apps.web.controller.BaseController;
import com.dragon.spider.api.BaseAPI;
import com.dragon.spider.api.UserAPI;
import com.dragon.spider.api.config.ApiConfig;

public class NeedFix {
	public static ApiConfig getApiConfig(){
		//FIXME
		 ApiConfig config = new ApiConfig();
	        config.setAppid("wx08aeb1f8fb438313");
	        config.setSecret("8c2206b596fad9fc452d94a8b60d5ab1");
	        UserAPI api = new UserAPI(config);
	        System.out.println(api.getUserInfo("oW1vAjofPSt-y613aDPpE7Vf2UhE"));
		return config;
	}
	public static Long getApiAccountId(BaseAPI api){
		//FIXME 
		return BaseController.gerCurUser().getWxAccountId();
//		return null;
	}
}
