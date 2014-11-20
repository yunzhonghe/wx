package com.dragon.apps.web.interceptor;

import com.dragon.core.freemarker.shiro.ShiroTags;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

public class ShiroFreeMarkerInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation invok) {
		Controller c = invok.getController();
		c.setAttr("shiro", new ShiroTags());
		
	}

}
