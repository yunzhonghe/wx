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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragon.apps.model.WxFansModel;
import com.dragon.apps.model.WxMessageModel;
import com.dragon.apps.model.WxMsgTextModel;
import com.dragon.apps.utils.Logger;
import com.dragon.spider.api.config.HttpClientApiConfig;
import com.dragon.spider.api.response.BaseResponse;
import com.dragon.spider.api.response.GetUsersResponse;
import com.dragon.spider.httpclient.RequestModel;
import com.dragon.spider.message.Article;
import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.ImageMsg;
import com.dragon.spider.message.MusicMsg;
import com.dragon.spider.message.NewsMsg;
import com.dragon.spider.message.TextMsg;
import com.dragon.spider.message.VideoMsg;
import com.dragon.spider.message.VoiceMsg;
import com.dragon.spider.util.BeanUtil;
import com.dragon.spider.util.JSONUtil;
import com.dragon.spider.util.StrUtil;

public class HttpClientMsgApi extends HttpClientBaseAPI {

	private static String msgListUrl = "https://mp.weixin.qq.com/cgi-bin/message?t=message/list&count=20&day=7&lang=zh_CN&token=";

	private static String uMsgListUrl = "https://mp.weixin.qq.com/cgi-bin/singlesendpage?tofakeid=#1&t=message/send&action=index&token=#2&lang=zh_CN";

	private static String msgRspUrl = "https://mp.weixin.qq.com/cgi-bin/singlesend?t=ajax-response&f=json&token=#1&lang=zh_CN";

	// private static String UserListUrl =
	// "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&lang=zh_CN&token=";

	public HttpClientMsgApi(HttpClientApiConfig config) {
		super(config);
	}

	/**
	 * 获得首页的消息列表，用来在有新消息的会后，获得该页的消息内容
	 * 
	 * @param
	 * @return 消息列表
	 * */
	public List<WxMessageModel> getFirstPageMsgs() {

		if (null == config.getToken()) {
			refreshToken();
		}
		Logger.info(this, "begin to get msg list " + config.toString());
		List<WxMessageModel> fans = new ArrayList<WxMessageModel>();
		String url = msgListUrl + config.getToken();
		RequestModel model = new RequestModel();
		Map<String, String> paras = new HashMap<String, String>();
		model.setHeaders(paras);
		model.setUrl(url);
		try {
			String returnMsg = simpleGetInvoke(model);
			Pattern p = Pattern.compile("msg_item\":[\\s\\S]{1,}.msg_item");
			Matcher m = p.matcher(returnMsg);
			while (m.find()) {
				String temp = m.group();
				temp = temp.replace("msg_item\":", "");
				temp = temp.replace("}).msg_item", "");
				JSONArray jList = JSON.parseArray(temp);
				for (int i = 0; i < jList.size(); i++) {
					WxMessageModel msgModel = new WxMessageModel();
					JSONObject jOb = jList.getJSONObject(i);
					String id = jOb.getString("id");// 消息ID
					int type = jOb.getIntValue("type"); // 消息类型， 1---文本
					String openId = jOb.getString("fakeid"); // 发送人
					String content = jOb.getString("content");
					String toUid = jOb.getString("to_uin");
					msgModel.setMessageId(id);
					msgModel.setFrom(openId);
					msgModel.setType(type);
					msgModel.setTo(toUid);
					switch (type) {
					case 1: {
						WxMsgTextModel textModel = new WxMsgTextModel();
						textModel.setContent(content);
						textModel.save();
						long msgId = textModel.getId();
						msgModel.setContentId(msgId);
					}
					}
					msgModel.save();
					fans.add(msgModel);
				}

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return fans;
	}

	/**
	 * 获得用户的消息列表
	 * 
	 * @param
	 * @return 消息列表
	 * */
	public List<WxMessageModel> getUserFirstPageMsgs(String fakeId) {

		if (null == config.getToken()) {
			refreshToken();
		}
		Logger.info(this, "begin to get msg list " + config.toString());
		List<WxMessageModel> fans = new ArrayList<WxMessageModel>();
		String url = uMsgListUrl.replace("#1", fakeId).replace("#2", config.getToken());
		RequestModel model = new RequestModel();
		Map<String, String> paras = new HashMap<String, String>();
		model.setHeaders(paras);
		model.setUrl(url);
		try {
			String returnMsg = simpleGetInvoke(model);
			
			Pattern p = Pattern.compile("msg_item\":[\\s\\S]{1,}]");
			Matcher m = p.matcher(returnMsg);
			while (m.find()) {
				String temp = m.group();
				temp = temp.replace("msg_item\":", "");
				// temp = temp.replace("}).msg_item", "");
				JSONArray jList = JSON.parseArray(temp);
				for (int i = 0; i < jList.size(); i++) {
					WxMessageModel msgModel = new WxMessageModel();
					JSONObject jOb = jList.getJSONObject(i);
					String id = jOb.getString("id");// 消息ID
					int type = jOb.getIntValue("type"); // 消息类型， 1---文本
					String openId = jOb.getString("fakeid"); // 发送人
					String content = jOb.getString("content");
					String toUid = jOb.getString("to_uin");
					msgModel.setMessageId(id);
					msgModel.setFrom(openId);
					msgModel.setType(type);
					msgModel.setTo(toUid);
					switch (type) {
					case 1: {
						WxMsgTextModel textModel = new WxMsgTextModel();
						textModel.setContent(content);
						textModel.save();
						long msgId = textModel.getId();
						msgModel.setContentId(msgId);
					}
					}
					msgModel.save();
					fans.add(msgModel);
				}

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return fans;
	}

	public void rspMsg(String fakeId, BaseMsg message) {
		BeanUtil.requireNonNull(fakeId, "openid is null");
		BeanUtil.requireNonNull(message, "message is null");

		if (null == config.getToken()) {
			refreshToken();
		}
		String url = msgRspUrl.replace("#1", config.getToken());
		RequestModel model = new RequestModel();
		model.setUrl(url);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Referer", "https://mp.weixin.qq.com/cgi-bin/singlesendpage?tofakeid=" + fakeId + "&t=message/send&action=index&token="
				+ config.getToken() + "&lang=zh_CN");
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		model.setHeaders(headers);

		Map<String, String> params = new HashMap<String, String>();
		params.put("tofakeid", fakeId);
		params.put("ajax", "1");
		params.put("f", "json");
		params.put("token", config.getToken());
		params.put("t", "ajax-response");
		params.put("lang", "zh_CN");
		params.put("random", "0.0015822553541511297");
		params.put("imgcode", "");

		if (message instanceof TextMsg) {
			TextMsg msg = (TextMsg) message;				
			params.put("type", "1");
			params.put("content", msg.getContent());
		} else if (message instanceof ImageMsg) {
			ImageMsg msg = (ImageMsg) message;
			params.put("msgtype", "image");
			Map<String, String> image = new HashMap<String, String>();
			image.put("media_id", msg.getMediaId());
			// params.put("image", image);
		} else if (message instanceof VoiceMsg) {
			VoiceMsg msg = (VoiceMsg) message;
			params.put("msgtype", "voice");
			Map<String, String> voice = new HashMap<String, String>();
			voice.put("media_id", msg.getMediaId());
			// params.put("voice", voice);
		} else if (message instanceof VideoMsg) {
			VideoMsg msg = (VideoMsg) message;
			params.put("msgtype", "video");
			Map<String, String> video = new HashMap<String, String>();
			video.put("media_id", msg.getMediaId());
			video.put("thumb_media_id", msg.getMediaId());
			video.put("title", msg.getTitle());
			video.put("description", msg.getDescription());
			// params.put("video", video);
		} else if (message instanceof MusicMsg) {
			MusicMsg msg = (MusicMsg) message;
			params.put("msgtype", "music");
			Map<String, String> music = new HashMap<String, String>();
			music.put("thumb_media_id", msg.getThumbMediaId());
			music.put("title", msg.getTitle());
			music.put("description", msg.getDescription());
			music.put("musicurl", msg.getMusicUrl());
			music.put("hqmusicurl", msg.getHqMusicUrl());
			// params.put("music", music);
		} else if (message instanceof NewsMsg) {
			NewsMsg msg = (NewsMsg) message;
			params.put("msgtype", "news");
			Map<String, Object> news = new HashMap<String, Object>();
			List<Object> articles = new ArrayList<Object>();
			List<Article> list = msg.getArticles();
			for (Article article : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", article.getTitle());
				map.put("description", article.getDescription());
				map.put("url", article.getUrl());
				map.put("picurl", article.getPicUrl());
				articles.add(map);
			}
			news.put("articles", articles);
			// params.put("news", news);
		}

		model.setParas(params);
		try {
			String result = simplePostInvoke(model);
			System.out.println("get the user send result:" + result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// executePost(url, JSONUtil.toJson(params));
	}
}
