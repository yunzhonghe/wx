package com.dragon.apps.web.module.wechat;

import javax.servlet.http.HttpServletRequest;

import com.dragon.apps.exception.ServiceException;
import com.dragon.apps.model.WxFansModel;
import com.dragon.spider.api.HttpClientAccountApi;
import com.dragon.spider.api.HttpClientMsgApi;
import com.dragon.spider.api.HttpClientUserApi;
import com.dragon.spider.api.PublicWxManager;
import com.dragon.spider.api.config.HttpClientApiConfig;
import com.dragon.spider.message.TextMsg;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.Controller;

@ClearInterceptor
public class TestController extends Controller{
	public void index() throws ServiceException {		
		
		HttpClientApiConfig config = PublicWxManager.getConfig("2334999724@qq.com", "zhouzhouzhou");
		/*HttpClientUserApi api = new HttpClientUserApi(config);
		//System.out.println(api.getUsers());
		WxFansModel model = new WxFansModel();
		model.setOpenId("1255063424");
		System.out.println(api.getUserDetail(model));
		*/
		/*HttpClientAccountApi account = new HttpClientAccountApi(config);
		System.out.println(account.initAccount());*/
		
	HttpClientMsgApi msg = new HttpClientMsgApi(config);
		 TextMsg tMsg = new  TextMsg();
		tMsg.setContent("auto send ");
		msg.rspMsg("1255063424", tMsg);
		
		/*HttpClientMsgApi msg = new HttpClientMsgApi(config);
		msg.getUserFirstPageMsgs("1255063424");*/
		renderText("helloworld");
		
	
		
	}
}
