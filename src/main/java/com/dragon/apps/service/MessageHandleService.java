package com.dragon.apps.service;

import com.dragon.apps.model.WxMessageModel;
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
		WxMessageModel model =WxMessageModel.getInstance();
		model = handerBase(msg,model);		
		model.setContent(msg.getContent());
		model.setType(ReqType.TEXTID);
		return model.save();		
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
	protected boolean handleImageMsg(ImageReqMsg msg) {
		WxMessageModel model =WxMessageModel.getInstance();
		model = handerBase(msg,model);		
		model.setContent(msg.getPicUrl());		
		model.setType(ReqType.TEXTID);
		return model.save();
	}

	/**
	 * 处理语音消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleVoiceMsg(VoiceReqMsg msg) {
		return handleDefaultMsg(msg);
	}

	/**
	 * 处理视频消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleVideoMsg(VideoReqMsg msg) {
		return handleDefaultMsg(msg);
	}

	/**
	 * 处理地理位置消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleLocationMsg(LocationReqMsg msg) {
		return handleDefaultMsg(msg);
	}

	/**
	 * 处理链接消息，有需要时子类重写
	 * 
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleLinkMsg(LinkReqMsg msg) {
		return handleDefaultMsg(msg);
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

	/**
	 * 处理添加关注事件，有需要时子类重写
	 * 
	 * @param event
	 *            添加关注事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleSubscribe(BaseEvent event) {
		return new TextMsg("感谢您的关注!");
	}

	/**
	 * 处理取消关注事件，有需要时子类重写
	 * 
	 * @param event
	 *            取消关注事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleUnsubscribe(BaseEvent event) {
		return null;
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
