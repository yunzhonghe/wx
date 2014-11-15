package com.dragon.apps.web.config;

import com.dragon.apps.model.DicRegion;
//import com.dragon.apps.model.WxAccStat;
import com.dragon.apps.model.WxAccType;
import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxFansInfo;
import com.dragon.apps.model.WxTag;
import com.dragon.apps.web.controller.WxAccountController;
import com.dragon.apps.web.controller.WxFansController;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

public class LjConfig {
	public static void configRoute(Routes me){
		//FIXME
		me.add(WxAccountController.controlerKey,WxAccountController.class);
		me.add(WxFansController.controlerKey,WxFansController.class);
	}
	public static void configActiveRecordPlugin(ActiveRecordPlugin arp){
		//FIXME
		arp.addMapping(DicRegion.tableName, DicRegion.ID, DicRegion.class);
        arp.addMapping(WxAccount.tableName, WxAccount.ID, WxAccount.class);
//        arp.addMapping(WxAccStat.getTableName(), WxAccStat.ID, WxAccStat.class);
        arp.addMapping(WxAccType.tableName, WxAccType.ID, WxAccType.class);
        arp.addMapping(WxFansInfo.tableName, WxFansInfo.id, WxFansInfo.class);
        arp.addMapping(WxTag.tableName, WxTag.id, WxTag.class);
	}
}
