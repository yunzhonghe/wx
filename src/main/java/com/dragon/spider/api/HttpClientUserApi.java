package com.dragon.spider.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dragon.apps.model.WxFansModel;
import com.dragon.apps.utils.Logger;
import com.dragon.spider.api.config.HttpClientApiConfig;
import com.dragon.spider.api.response.BaseResponse;
import com.dragon.spider.api.response.GetUsersResponse;
import com.dragon.spider.httpclient.RequestModel;
import com.dragon.spider.util.JSONUtil;
import com.dragon.spider.util.StrUtil;

public class HttpClientUserApi extends HttpClientBaseAPI {

	private static String UserDetailUrl = "https://mp.weixin.qq.com/cgi-bin/getcontactinfo?t=ajax-getcontactinfo&lang=zh_CN&fakeid=";

	private static String UserListUrl = "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&lang=zh_CN&token=";
	
	public HttpClientUserApi(HttpClientApiConfig config) {
		super(config);
	}

	
	/**
	 * 获得对应的粉丝的消息内容
	 * @param 
	 * @return  粉丝列表
	 * */
	public List<WxFansModel> getUsers(){
		
		if (null == config.getToken()) {
			refreshToken();
		}
		Logger.info(this, "begin to init fans list " + config.toString());
		List<WxFansModel> fans = new ArrayList<WxFansModel>();
		String url = UserListUrl+config.getToken();		
		RequestModel model = new RequestModel();
		Map<String,String> paras = new HashMap<String,String>();
		paras.put("Referer", "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&groupid=0&token="+config.getToken()+"&lang=zh_CN");
		model.setHeaders(paras);
		model.setUrl(url);
		try {
			String returnMsg = simpleGetInvoke(model);
			Pattern p = Pattern.compile("{\"id\":[\\s\\S]{1,},\"nick_name\":\"[\\s\\S]{0,}\",\"remark_name\":\"[\\s\\S]{0,}\",\"group_id\":[\\s\\S]{0,}}");
			Matcher m = p.matcher(returnMsg);
			while(m.find()){
				WxFansModel fansModel = new WxFansModel();
				String temp = m.group();
				JSONObject jOb =  JSON.parseObject(temp);
				int id = jOb.getIntValue("id");
				String name = jOb.getString("nick_name");
				String remarkName = jOb.getString("remark_name");
				int groupId = jOb.getIntValue("group_id");
				fansModel.setOpenId(String.valueOf(id));
				fansModel.setName(name);
				fansModel.setMarkName(remarkName);
				fansModel.setGroupId(groupId);
				fans.add(fansModel);
			}
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
	
	
	/**
	 * 获得对应的粉丝的详细信息
	 * @param 粉丝的ID
	 * @return  
	 * */	
	public WxFansModel getUserDetail(WxFansModel fans) {
		if (null == fans || fans.getOpenId()==null) {
			return fans;
		}

		if (null == config.getToken()) {
			refreshToken();
		}

		String fakeId = fans.getOpenId();
		
		Logger.info(this, "begin to init fans detail " + config.toString());	
		String url = UserDetailUrl + fakeId;
		RequestModel model = new RequestModel();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Referer", "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&groupid=0&token="+config.getToken()+"&lang=zh_CN");
		//paras.put("Cookie", "remember_acct=2334999724%40qq.com; ptcz=5128ea257c52201cb67bd865713d12edfbc6ad178806802c180592b3a293fa51; pt2gguin=o0303006084; uin=o0303006084; skey=@YkOIXmqNy; qm_username=303006084; qm_sid=f2a2d1dadad0d27711c59f67842127f1,qek5mKkh3bWRVUG9EWm5jSlVuKmhEVUZNMnFPTFdVcmNKUEZ3bkMyallBZ18.; ptisp=ctc; pgv_pvi=8179736576; pgv_si=s6531849216; data_bizuin=2391977781; data_ticket=AgWIHF9cE3wj6NldKLKryy4LAwHfCNJBGbqEpVxqtSA=; remember_acct=2334999724%40qq.com; slave_user=gh_61efe29f21fd; slave_sid=cFl2YmJjczdIY2U0Rk10dEpYUFpNdkRYbncwSlZtcTVsRDNxdWdKVjk2S1FTal9GU0MxQmhrOWNTNkxkU2F0M2xaZmM2cmhrM1k4ZzJYUzNFMHFVdV9qX19kQU1FSzhiNWJjZEl1TWJsQVplRlBjREJVNEVNK3FoQUZ6dFdQSVk=; bizuin=2394970971");
		model.setHeaders(headers);
		
		Map<String,String> paras = new HashMap<String,String>();
		paras.put("token", config.getToken());
		paras.put("lang", "zh_CN");
		paras.put("f", "json");
		paras.put("ajax", "1");
		paras.put("random", "0.8983551932033151");
		
		model.setParas(paras);
		
		model.setUrl(url);
		try {
			String returnMsg = simplePostInvoke(model);
			System.out.println(returnMsg);
			if(null!=returnMsg){
				JSONObject jOb = JSON.parseObject(returnMsg);				
				JSONObject contactInfoJOb = jOb.getJSONObject("contact_info");
				String  signature = contactInfoJOb.getString("signature");
				fans.setSignature(signature);
				String province = contactInfoJOb.getString("province");
				String city = contactInfoJOb.getString("city");
				String country = contactInfoJOb.getString("country");
				String location = country+":"+province +":"+city;
				fans.setLocation(location);
				fans.setGender(contactInfoJOb.getIntValue("gender"));
				//String userName = contactInfoJOb.getString("user_name");
				return fans;
			}
			
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
