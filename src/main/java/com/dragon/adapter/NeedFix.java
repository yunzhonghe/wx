package com.dragon.adapter;

import com.dragon.apps.utils.RoleUtils;
import com.dragon.spider.api.BaseAPI;
import com.dragon.spider.api.UserAPI;
import com.dragon.spider.api.config.ApiConfig;

public class NeedFix {
	/**
	 * 测试数据
	 * @return
	 */
	public static ApiConfig getApiConfig() {
		ApiConfig config = new ApiConfig();
		config.setAppid("wx08aeb1f8fb438313");
		config.setSecret("8c2206b596fad9fc452d94a8b60d5ab1");
		UserAPI api = new UserAPI(config);
		System.out.println(api.getUserInfo("oW1vAjofPSt-y613aDPpE7Vf2UhE"));
		return config;
	}
	/**
	 * 根据微信帐号的originalId查询数据库的api配置数据
	 * @param originalId
	 * @return
	 */
	public static ApiConfig getApiConfig(String originalId) {
		//for test:
		//return getApiConfig();
		
		//for real:
		return ApiConfig.getApiConfigByOriginalId(originalId);
	}

	public static Long getApiAccountId(BaseAPI api) {
		//for test:
//		return RoleUtils.gerCurUser().getWxAccountId();
		
		return api.getConfig().getAccountId();
	}
}
