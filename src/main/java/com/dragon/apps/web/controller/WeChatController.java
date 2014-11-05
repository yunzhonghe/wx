package com.dragon.apps.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.dragon.apps.exception.ServiceException;
import com.dragon.apps.web.service.WeChatService;

public class WeChatController extends AbstractAPIController {

	
	public void index() throws ServiceException {		
		HttpServletRequest request = this.getRequest();		
		if (request.getMethod().equals("POST")) {
			
		}else{		
			String echostr = this.getAttrForStr("echostr");// 随机字符串
			WeChatService service = new WeChatService();
			if(service.doSigure("")){
				renderText("fasdfasdaer");
			}else{
				renderText(echostr);
			}
			
		}	
	}
}
