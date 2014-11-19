package com.dragon.apps.web.config;

import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.model.WxAnswerRule;
import com.dragon.apps.model.WxMaterial;
import com.dragon.apps.web.module.wxadmin.WxAdminController;
import com.dragon.apps.web.module.wxautoreply.WxAnwserRuleController;
import com.dragon.apps.web.module.wxmaterial.WxMaterialController;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Created by Administrator on 2014/11/2.
 */
public class LxConfig {
    public static void configRoute(Routes me){
        me.add("/wx-admin", WxAdminController.class);
        me.add("/wx-answer-rule",WxAnwserRuleController.class);
        me.add("/wx-material",WxMaterialController.class);
    }
    public static void configActiveRecordPlugin(ActiveRecordPlugin arp){
        arp.addMapping(WxAdmin.getTableName(), WxAdmin.ID, WxAdmin.class);
        arp.addMapping(WxAnswerRule.getTableName(), WxAnswerRule.ID, WxAnswerRule.class);
        arp.addMapping(WxMaterial.getTableName(), WxMaterial.ID, WxMaterial.class);
    }
}
