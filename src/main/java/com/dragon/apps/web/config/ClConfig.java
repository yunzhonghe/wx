package com.dragon.apps.web.config;

import com.dragon.apps.model.WxMessageModel;
import com.dragon.apps.web.controller.WeChatController;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

public class ClConfig {
	
	public static String WECHAT ="/wechat";
	
	public static String WX_FANS_TABLE = "wx_fans";
	public static String WX_ACCOUNT_TABLE = "wx_account";
	public static String WX_MESSAGE_TABLE = "wx_message";
	public static void configRoute(Routes me){
		//FIXME
		me.add(WECHAT,WeChatController.class);
	}
	public static void configActiveRecordPlugin(ActiveRecordPlugin arp){
		//FIXME
		arp.addMapping(WX_MESSAGE_TABLE, WxMessageModel.class);      
	}
}
