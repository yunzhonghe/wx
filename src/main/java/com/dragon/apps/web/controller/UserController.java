package com.dragon.apps.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.Controller;

@ClearInterceptor
public class UserController extends Controller{

	
	public void index() {
		render("user/login.html");
	}
	
	public void login(){
		String userName=this.getPara("username");
		String password=this.getPara("password");
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (Exception e) {
			this.redirect("/");
			return;
		}
		render("user/home.html");
	}
	
	public void logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}
}
