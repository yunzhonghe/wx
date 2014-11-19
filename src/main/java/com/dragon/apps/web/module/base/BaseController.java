package com.dragon.apps.web.module.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.utils.PageSet;
import com.jfinal.core.Controller;

public class BaseController extends Controller{
	protected String OPERATION_RESULT = "_opresult";//by ljsnake
	protected String BEAN_ENTITY = "_bean";//by ljsnake
	
	public static WxAdmin gerCurUser(){
		Subject subject = SecurityUtils.getSubject();
		if(subject.getPrincipal()==null){
			throw new  AuthenticationException();
		}
		WxAdmin admin=(WxAdmin) subject.getPrincipal();
		return admin;
	} 
	public static Long gerCurAccountId(){
		WxAdmin admin = gerCurUser();
		if(admin!=null){
			return admin.getWxAccountId();
		}
		return null;
	}
	public static WxAccount getCurAccount(){
		Long accountId = gerCurAccountId();
		if(accountId!=null){
			return WxAccount.dao.findById(accountId);
		}
		return null;
	}
	public static String getCurAccountOriginalId(){
		WxAccount account = getCurAccount();
		if(account!=null){
			return account.getOriginalid();
		}
		return null;
	}
	
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
