package com.dragon.apps.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.utils.PageSet;
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
	
	protected String operationResult = "_opresult";//by ljsnake
	protected PageSet getPageSet() {//by ljsnake
		PageSet pageSet = null;
		//FIXME parameters name may be other names.
		String page_pageSize = getPara("page_pageSize");
		String page_currPage = getPara("page_currPage");
		if (page_pageSize != null && page_currPage != null) {
			pageSet = new PageSet(Integer.parseInt(page_currPage), Integer.parseInt(page_pageSize));
		} else {
			pageSet = new PageSet();
		}
		return pageSet;
	}
}
