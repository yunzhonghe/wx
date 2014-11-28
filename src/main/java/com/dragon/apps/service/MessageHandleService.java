package com.dragon.apps.service;

import com.dragon.apps.model.WxMessageModel;
import com.dragon.apps.model.WxMsgImageModel;
import com.dragon.apps.model.WxMsgLinkModel;
import com.dragon.apps.model.WxMsgLocationModel;
import com.dragon.apps.model.WxMsgTextModel;
import com.dragon.apps.model.WxMsgVideoModel;
import com.dragon.apps.model.WxMsgVoiceModel;
import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.TextMsg;
import com.dragon.spider.message.req.BaseEvent;

import java.util.Map;

import com.dragon.spider.message.req.BaseReq;
import com.dragon.spider.message.req.BaseReqMsg;
import com.dragon.spider.message.req.ImageReqMsg;
import com.dragon.spider.message.req.LinkReqMsg;
import com.dragon.spider.message.req.LocationEvent;
import com.dragon.spider.message.req.LocationReqMsg;
import com.dragon.spider.message.req.MenuEvent;
import com.dragon.spider.message.req.QrCodeEvent;
import com.dragon.spider.message.req.ReqType;
import com.dragon.spider.message.req.TextReqMsg;
import com.dragon.spider.message.req.VideoReqMsg;
import com.dragon.spider.message.req.VoiceReqMsg;

/**
 * 进行数据库的操作，增删改查
 * @author dfb365005
 *
 */
public class MessageHandleService {
	/**
	 * 处理文本进来的消息，存储数据
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	public boolean handleTextMsg(TextReqMsg msg) {
		WxMessageModel model =new WxMessageModel();
		model = handerBase(msg,model);
		WxMsgTextModel text =new WxMsgTextModel();
		text.setContent(msg.getContent());
		boolean isSave = text.save();
		if(isSave){
			model.setContentId(text.getId());
			model.setType(ReqType.TEXTID);
			return model.save();
		}
		return false;
	}

	private WxMessageModel handerBase(BaseReqMsg msg, WxMessageModel model) {
		model.setFrom(msg.getFromUserName());
		model.setTo(msg.getToUserName());
		model.setCreateTime(msg.getCreateTime());
		model.setMessageId(msg.getMsgId());
		return model;
	}

	/**
	 * 处理图片消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	public boolean handleImageMsg(ImageReqMsg msg) {
		WxMessageModel model =new WxMessageModel();
		model = handerBase(msg,model);	
		WxMsgImageModel image = new WxMsgImageModel();
		image.setMediaId(msg.getMediaId());
		image.setPicUrl(msg.getPicUrl());	
		boolean isSave = image.save();
		if(isSave){
			model.setContentId(image.getId());
			model.setType(ReqType.IMAGEID);
			return model.save();	
		}
		return false;	
	}

	/**
	 * 处理语音消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	public boolean handleVoiceMsg(VoiceReqMsg msg) {
		WxMessageModel model =new WxMessageModel();
		model = handerBase(msg,model);	
		WxMsgVoiceModel voice = new WxMsgVoiceModel();
		voice.setMediaId(msg.getMediaId());
		voice.setFormat(msg.getFormat());
		boolean isSave = voice.save();
		if(isSave){
			model.setContentId(voice.getId());
			model.setType(ReqType.VOICEID);
			return model.save();	
		}
		return false;	
	}

	/**
	 * 处理视频消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	public boolean handleVideoMsg(VideoReqMsg msg) {
		WxMessageModel model =new WxMessageModel();
		model = handerBase(msg,model);	
		WxMsgVideoModel vedio =new WxMsgVideoModel();
		vedio.setMediaId(msg.getMediaId());
		vedio.setThunbMediaId(msg.getThumbMediaId());
		boolean isSave = vedio.save();
		if(isSave){
			model.setContentId(vedio.getId());
			model.setType(ReqType.VIDEOID);
			return model.save();	
		}
		return false;	
	}

	/**
	 * 处理地理位置消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	public boolean handleLocationMsg(LocationReqMsg msg) {
		WxMessageModel model =new WxMessageModel();
		model = handerBase(msg,model);	
		WxMsgLocationModel location =new WxMsgLocationModel();
		location.setLocationX(String.valueOf(msg.getLocationX()));
		location.setLocationY(String.valueOf(msg.getLocationY()));
		location.setScale(msg.getScale());
		location.setLabel(msg.getLabel());
		boolean isSave = location.save();
		if(isSave){
			model.setContentId(location.getId());
			model.setType(ReqType.LOCATIONID);
			return model.save();	
		}
		return false;	
	}

	/**
	 * 处理链接消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	public boolean handleLinkMsg(LinkReqMsg msg) {
		WxMessageModel model =new WxMessageModel();
		model = handerBase(msg,model);	
		WxMsgLinkModel link =new WxMsgLinkModel();
		link.setTitle(msg.getTitle());
		link.setDescription(msg.getDescription());
		link.setUrl(msg.getUrl());
		boolean isSave = link.save();
		if(isSave){
			model.setContentId(link.getId());
			model.setType(ReqType.LINKID);
			return model.save();	
		}
		return false;	
	}

	/**
	 * 处理扫描二维码事件，有需要时子类重写
	 * 
	 * @param event
	 *            扫描二维码事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleQrCodeEvent(QrCodeEvent event) {
		return handleDefaultEvent(event);
	}

	/**
	 * 处理地理位置事件，有需要时子类重写
	 * 
	 * @param event
	 *            地理位置事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleLocationEvent(LocationEvent event) {
		return handleDefaultEvent(event);
	}

	/**
	 * 处理菜单点击事件，有需要时子类重写
	 * 
	 * @param event
	 *            菜单点击事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleMenuClickEvent(MenuEvent event) {
		return handleDefaultEvent(event);
	}

	/**
	 * 处理菜单跳转事件，有需要时子类重写
	 * 
	 * @param event
	 *            菜单跳转事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleMenuViewEvent(MenuEvent event) {
		return handleDefaultEvent(event);
	}

	

	protected BaseMsg handleDefaultMsg(BaseReqMsg msg) {
		BaseMsg base = new BaseMsg();
		base.setCreateTime(msg.getCreateTime());
		base.setFromUserName(msg.getToUserName());
		base.setToUserName(msg.getToUserName());
		return base;
	}

	protected BaseMsg handleDefaultEvent(BaseEvent event) {
		return null;
	}

	private void buildBasicReqMsg(Map<String, String> reqMap, BaseReqMsg reqMsg) {
		addBasicReqParams(reqMap, reqMsg);
		reqMsg.setMsgId(reqMap.get("MsgId"));
	}

	private void addBasicReqParams(Map<String, String> reqMap, BaseReq req) {
		req.setMsgType(reqMap.get("MsgType"));
		req.setFromUserName(reqMap.get("FromUserName"));
		req.setToUserName(reqMap.get("ToUserName"));
		req.setCreateTime(Long.parseLong(reqMap.get("CreateTime")));
	}
}
