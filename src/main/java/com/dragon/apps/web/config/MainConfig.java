package com.dragon.apps.web.config;

import com.dragon.apps.web.controller.UserController;
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
//		loadPropertyFile("a_little_config_lj.txt");
		me.setBaseViewPath("/WEB-INF/pages");
//		me.setFreeMarkerViewExtension(".ftl");
		me.setDevMode(getPropertyToBoolean("devMode", false));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/",UserController.class);

		LjConfig.configRoute(me);
        WxAdminConfig.configRoute(me);
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
        
        LjConfig.configActiveRecordPlugin(arp);
        WxAdminConfig.configActiveRecordPlugin(arp);
		
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers han) {
		han.add(new ContextPathHandler("BASE_PATH"));// in freemarker: <img src="${BASE_PATH}/images/logo.png" />
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 12345, "/", 5);
	}
}
