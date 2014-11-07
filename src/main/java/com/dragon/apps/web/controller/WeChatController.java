package com.dragon.apps.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.dragon.apps.exception.ServiceException;
import com.jfinal.aop.ClearInterceptor;

@ClearInterceptor
public class WeChatController extends WxAbstractAPIController {

	
	public void index() throws ServiceException {		
		HttpServletRequest request = this.getRequest();		
		if (request.getMethod().equalsIgnoreCase("POST")) {
			if (!isLegal(request)) {
				renderText("post");
	        }
			String text = processRequest(request);
			System.out.println(text);
			renderText(text);
		}else{
			if (isLegal(request)) {
	            //绑定微信服务器成功
				renderText(this.getAttrForStr("echostr"));
	        } else {
	            //绑定微信服务器失败
	        	renderText("get");
	        }
		}	
	}

	@Override
	protected String getToken() {
		return null;
	}
}
