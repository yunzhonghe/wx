package com.dragon.apps.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

import com.dragon.apps.model.WxAdmin;
import com.jfinal.core.Controller;

public class BaseController extends Controller{
	
	public WxAdmin gerCurUser(){
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated() || subject.isRemembered() || subject.getPrincipal()==null){
			throw new  AuthenticationException();
		}
		WxAdmin admin=(WxAdmin) subject.getPrincipal();
		return admin;
	} 
}
