package com.dragon.apps.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.dragon.apps.exception.ServiceException;

public class WeChatController extends WxAbstractAPIController {

	
	public void index() throws ServiceException {		
		HttpServletRequest request = this.getRequest();		
		if (request.getMethod().equals("POST")) {
			if (!isLegal(request)) {
				renderText("post");
	        }
			renderText(processRequest(request));
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
