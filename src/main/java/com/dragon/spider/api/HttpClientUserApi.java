package com.dragon.spider.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.dragon.apps.model.WxFansModel;
import com.dragon.spider.api.config.HttpClientApiConfig;
import com.dragon.spider.api.response.BaseResponse;
import com.dragon.spider.api.response.GetUsersResponse;
import com.dragon.spider.httpclient.RequestModel;
import com.dragon.spider.util.JSONUtil;
import com.dragon.spider.util.StrUtil;

public class HttpClientUserApi extends HttpClientBaseAPI {

	private static String UserDetailUrl = "https://mp.weixin.qq.com/cgi-bin/getcontactinfo?t=ajax-getcontactinfo&lang=zh_CN&fakeid=";

	public HttpClientUserApi(HttpClientApiConfig config) {
		super(config);
	}

	public WxFansModel getUsers(String fakeId) {
		if (null == fakeId) {
			return null;
		}

		if (null == config.getToken()) {
			refreshToken();
		}

		WxFansModel fans = new WxFansModel();
		String url = UserDetailUrl + fakeId;
		RequestModel model = new RequestModel();
		Map<String,String> paras = new HashMap<String,String>();
		paras.put("Referer", "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&groupid=0&token="+config.getToken()+"&lang=zh_CN");
		model.setHeaders(paras);
		model.setUrl(url);
		try {
			String returnMsg = simplePostInvoke(model);
			System.out.println(returnMsg);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
