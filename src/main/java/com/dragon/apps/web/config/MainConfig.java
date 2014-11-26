package com.dragon.apps.web.config;

import com.dragon.apps.model.WxAccType;
import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.model.WxAnswerRule;
import com.dragon.apps.model.WxFansInfo;
import com.dragon.apps.model.WxMaterial;
import com.dragon.apps.model.WxTag;
import com.dragon.apps.web.interceptor.LoginInterceptor;
import com.dragon.apps.web.interceptor.ShiroFreeMarkerInterceptor;
import com.dragon.apps.web.module.user.LoginController;
import com.dragon.apps.web.module.user.UserController;
import com.dragon.apps.web.module.wxaccount.WxAccountController;
import com.dragon.apps.web.module.wxactivity.WxActivityController;
import com.dragon.apps.web.module.wxadmin.WxAdminController;
import com.dragon.apps.web.module.wxautoreply.WxAnswerRuleController;
import com.dragon.apps.web.module.wxchannel.WxChannelController;
import com.dragon.apps.web.module.wxfans.WxFansController;
import com.dragon.apps.web.module.wxmaterial.WxMaterialController;
import com.dragon.apps.web.module.wxmenu.WxMenuController;
import com.dragon.core.jFplugins.ShiroPlugin;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class MainConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("a_little_config.txt");
		me.setBaseViewPath("/WEB-INF/pages");
		me.setDevMode(getPropertyToBoolean("devMode",true));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/",LoginController.class);
		
		//XXX
		ClConfig.configRoute(me);
        
		me.add(WxAccountController.controlerKey,WxAccountController.class);//微信账户
		me.add(WxFansController.controlerKey,WxFansController.class);//微信粉丝
		me.add(WxAdminController.controlerKey, WxAdminController.class);//管理员
        me.add(WxMaterialController.controlerKey,WxMaterialController.class);//素材
        me.add(WxMenuController.controlerKey,WxMenuController.class);//菜单
        me.add(WxAnswerRuleController.controlerKey,WxAnswerRuleController.class);//应答规则
        me.add(WxActivityController.controlerKey,WxActivityController.class);//活动
        me.add(WxChannelController.controlerKey,WxChannelController.class);//渠道
        me.add(UserController.controlerKey,UserController.class);//系统功能
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins plu) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		c3p0Plugin.setDriverClass(getProperty("driver"));
		plu.add(c3p0Plugin);
		
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.setDialect(new MysqlDialect());//设置mysql方言
        arp.setContainerFactory(new CaseInsensitiveContainerFactory());//大小写不敏感
        plu.add(arp);
        
        plu.add(new ShiroPlugin());
        //XXX
        ClConfig.configActiveRecordPlugin(arp);
		
        arp.addMapping(WxAccount.tableName, WxAccount.ID, WxAccount.class);
        arp.addMapping(WxAccType.tableName, WxAccType.ID, WxAccType.class);
        arp.addMapping(WxFansInfo.tableName, WxFansInfo.id, WxFansInfo.class);
        arp.addMapping(WxTag.tableName, WxTag.id, WxTag.class);
        arp.addMapping(WxAdmin.getTableName(), WxAdmin.ID, WxAdmin.class);
        arp.addMapping(WxAnswerRule.getTableName(), WxAnswerRule.ID, WxAnswerRule.class);
        arp.addMapping(WxMaterial.getTableName(), WxMaterial.ID, WxMaterial.class);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new LoginInterceptor());
		me.add(new ShiroFreeMarkerInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers han) {
		han.add(new ContextPathHandler("base"));
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 5);
//		JFinal.start("src/main/webapp", 81, "/", 5);
	}
}
