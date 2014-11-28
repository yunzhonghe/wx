package com.dragon.apps.web.module.wechat;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dragon.adapter.event.EventService;
import com.dragon.adapter.message.MessageService;
import com.dragon.apps.exception.ServiceException;
import com.dragon.apps.web.module.base.WxAbstractAPIController;
import com.dragon.spider.handle.EventHandle;
import com.dragon.spider.handle.MessageHandle;
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
			//if (isLegal(request)) {//XXX
			if(true){
	            //绑定微信服务器成功
				renderText(this.getPara("echostr"));
	        } else {
	            //绑定微信服务器失败
	        	System.out.println(this.getPara("echostr"));
	        	renderText(this.getPara("echostr"));
	        }
		}	
	}
	protected List<MessageHandle> getMessageHandles() {
		List<MessageHandle> ls = new ArrayList<MessageHandle>();
		ls.add(MessageService.getInstance());
		return ls;
	}
	protected List<EventHandle> getEventHandles() {
		List<EventHandle> ls = new ArrayList<EventHandle>();
		ls.add(EventService.getInstance());
		return ls;
	}

	@Override
	protected String getToken() {
		return null;
	}
}
