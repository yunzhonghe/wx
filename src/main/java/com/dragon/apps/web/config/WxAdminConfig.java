package com.dragon.apps.web.config;

import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.web.controller.WxAdminController;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Created by Administrator on 2014/11/2.
 */
public class WxAdminConfig {
    public static void configRoute(Routes me){
        me.add("/wx-admin", WxAdminController.class);
    }
    public static void configActiveRecordPlugin(ActiveRecordPlugin arp){
        arp.addMapping(WxAdmin.getTableName(), WxAdmin.ID, WxAdmin.class);
    }
}
