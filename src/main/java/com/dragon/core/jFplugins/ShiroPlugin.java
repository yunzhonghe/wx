package com.dragon.core.jFplugins;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;

import com.jfinal.plugin.IPlugin;

public class ShiroPlugin implements IPlugin{

	public boolean start() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager=factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		return true;
	}

	public boolean stop() {
		return false;
	}

}
