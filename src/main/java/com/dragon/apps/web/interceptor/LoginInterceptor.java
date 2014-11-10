package com.dragon.apps.web.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

public class LoginInterceptor implements Interceptor{

	public void intercept(ActionInvocation ai) {
		
		Controller controller = ai.getController();
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated() || subject.isRemembered()){
			ai.invoke();
		}else{
			controller.redirect("/");
		}
	}

}
