package com.dragon.apps.web.module.wechat;

import javax.servlet.http.HttpServletRequest;

import com.dragon.apps.exception.ServiceException;
import com.dragon.apps.model.WxFansModel;
import com.dragon.spider.api.HttpClientAccountApi;
import com.dragon.spider.api.HttpClientUserApi;
import com.dragon.spider.api.PublicWxManager;
import com.dragon.spider.api.config.HttpClientApiConfig;
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
		HttpClientAccountApi account = new HttpClientAccountApi(config);
		System.out.println(account.initAccount());
		renderText("helloworld");
		
		
	}
}
