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
		
		/*HttpClientMsgApi msg = new HttpClientMsgApi(config);
		 TextMsg tMsg = new  TextMsg();
		tMsg.setContent("【每日一笑】关羽：“大哥，我再也不想跟军师出去了。”刘备：“怎么了，二弟？”关羽：“每次军师出去都放屁。”刘备：“这有什么啊？”关羽：“每次他放完屁，都扇扇子装无辜，而旁边人一看我的脸色都以为是我放的。”【重庆隆盛服饰感谢您的关注】");
		msg.sendAllMsg( tMsg);*/
		
		/*HttpClientMsgApi msg = new HttpClientMsgApi(config);
		msg.getFirstPageMsgs();*/
		renderText("helloworld");
		
	
		
	}
}
