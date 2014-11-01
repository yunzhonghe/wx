package com.dragon.apps.web.config;

import com.dragon.apps.model.DicRegion;
//import com.dragon.apps.model.WxAccStat;
import com.dragon.apps.model.WxAccType;
import com.dragon.apps.model.WxAccount;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

public class LjConfig {
	public static void configRoute(Routes me){
		//FIXME
	}
	public static void configActiveRecordPlugin(ActiveRecordPlugin arp){
		//FIXME
		arp.addMapping(DicRegion.getTableName(), DicRegion.CODE, DicRegion.class);
        arp.addMapping(WxAccount.getTableName(), WxAccount.ACCOUNT, WxAccount.class);
//        arp.addMapping(WxAccStat.getTableName(), WxAccStat.ID, WxAccStat.class);
        arp.addMapping(WxAccType.getTableName(), WxAccType.ID, WxAccType.class);
	}
}
