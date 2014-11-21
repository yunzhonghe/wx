package com.dragon.apps.web.module.user;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.dragon.apps.web.module.wxaccount.WxAccountController;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.Controller;

@ClearInterceptor
public class LoginController extends Controller{
	
	public void index() {
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated() || subject.isRemembered()){
			this.redirect(WxAccountController.controlerKey);
			return;
		}
		render("user/login.html");
	}
	
	public void login(){
		String userName=this.getPara("username");
		String password=this.getPara("password");
		boolean rememberMe=this.getParaToBoolean("rememberMe",false);
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password,rememberMe);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (Exception e) {
			this.redirect("/");
			return;
		}
		this.redirect(WxAccountController.controlerKey);
	}
	
	public void logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		this.redirect("/");
	}
}