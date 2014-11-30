package com.dragon.apps.web.config;

import com.dragon.apps.model.WxFansModel;
import com.dragon.apps.model.WxMessageModel;
import com.dragon.apps.model.WxMsgImageModel;
import com.dragon.apps.model.WxMsgLinkModel;
import com.dragon.apps.model.WxMsgLocationModel;
import com.dragon.apps.model.WxMsgTextModel;
import com.dragon.apps.model.WxMsgVideoModel;
import com.dragon.apps.model.WxMsgVoiceModel;
import com.dragon.apps.web.module.wechat.TestController;
import com.dragon.apps.web.module.wechat.WeChatController;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

public class ClConfig {
	
	public static String WECHAT ="/wechat";
	
	public static String WX_FANS_TABLE = "wx_fans";
	public static String WX_ACCOUNT_TABLE = "wx_account";
	public static String WX_MESSAGE_TABLE = "wx_message";
	public static String WX_MESSAGE_IMAGE_TABLE = "wx_message_image";
	public static String WX_MESSAGE_LINK_TABLE = "wx_message_link";
	public static String WX_MESSAGE_LOCATION_TABLE = "wx_message_location";
	public static String WX_MESSAGE_TEXT_TABLE = "wx_message_text";
	public static String WX_MESSAGE_VIDEO_TABLE = "wx_message_video";
	public static String WX_MESSAGE_VOICE_TABLE = "wx_message_voice";
	public static void configRoute(Routes me){
		//FIXME
		me.add(WECHAT,WeChatController.class);
		me.add("/test",TestController.class);
	}
	public static void configActiveRecordPlugin(ActiveRecordPlugin arp){
		//FIXME
		arp.addMapping(WX_FANS_TABLE, WxFansModel.class); 
		arp.addMapping(WX_MESSAGE_TABLE, WxMessageModel.class); 
		arp.addMapping(WX_MESSAGE_IMAGE_TABLE, WxMsgImageModel.class); 
		arp.addMapping(WX_MESSAGE_LINK_TABLE, WxMsgLinkModel.class); 
		arp.addMapping(WX_MESSAGE_LOCATION_TABLE, WxMsgLocationModel.class); 
		arp.addMapping(WX_MESSAGE_TEXT_TABLE, WxMsgTextModel.class); 
		arp.addMapping(WX_MESSAGE_VIDEO_TABLE, WxMsgVideoModel.class); 
		arp.addMapping(WX_MESSAGE_VOICE_TABLE, WxMsgVoiceModel.class); 
		
	}
}
