package com.dragon.adapter;

import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.req.BaseEvent;
import com.dragon.spider.message.req.BaseReqMsg;
import com.dragon.spider.message.req.EventType;
import com.dragon.spider.message.req.ImageReqMsg;
import com.dragon.spider.message.req.LinkReqMsg;
import com.dragon.spider.message.req.LocationEvent;
import com.dragon.spider.message.req.LocationReqMsg;
import com.dragon.spider.message.req.MenuEvent;
import com.dragon.spider.message.req.QrCodeEvent;
import com.dragon.spider.message.req.TextReqMsg;
import com.dragon.spider.message.req.VideoReqMsg;
import com.dragon.spider.message.req.VoiceReqMsg;

public class AutoReplyRule {
	/**
	 * FIXME
	 * 1、获取当前管理员帐号信息
	 * 2、获取超级管理员的关键字回复配置信息
	 * 3、获取当前管理员的回复配置信息
	 */
	
	public BaseMsg autoReply(BaseReqMsg message){
		BaseMsg result = null;
		if(message!=null){
			if(message instanceof TextReqMsg){
				TextReqMsg msg = (TextReqMsg)message;
				//FIXME 关键字、默认回复
			}else if(message instanceof ImageReqMsg){
				ImageReqMsg msg = (ImageReqMsg)message;
				//FIXME 默认回复
			}else if(message instanceof VoiceReqMsg){
				VoiceReqMsg msg = (VoiceReqMsg)message;
				//FIXME 默认回复
			}else if(message instanceof VideoReqMsg){
				VideoReqMsg msg = (VideoReqMsg)message;
				//FIXME 默认回复
			}else if(message instanceof LocationReqMsg){
				LocationReqMsg msg = (LocationReqMsg)message;
				//FIXME 默认回复
			}else if(message instanceof LinkReqMsg){
				LinkReqMsg msg = (LinkReqMsg)message;
				//FIXME 默认回复
			}
		}
		return result;
	}
	public BaseMsg autoReply(BaseEvent event){
		BaseMsg result = null;
		if(event!=null){
			if(event instanceof LocationEvent){
	//			LocationEvent evt = (LocationEvent)event;
			}else if(event instanceof MenuEvent){//菜单事件
				MenuEvent evt = (MenuEvent)event;
				String evtType = evt.getEvent();
				if(evtType.equals(EventType.CLICK)){//点击菜单拉取消息时的事件推送
					//FIXME 获取菜单设置
				}else if(evtType.equals(EventType.VIEW)){//点击菜单跳转链接时的事件推送
					//FIXME 获取菜单设置
				}
			}else if(event instanceof QrCodeEvent){
				QrCodeEvent evt = (QrCodeEvent)event;
				String evtType = evt.getEvent();
				if(evtType.equals(EventType.SUBSCRIBE)){//用户未关注时，进行关注后的事件推送
					//FIXME 获取自动关注的回复
				}else if(evtType.equals(EventType.SCAN)){//用户已关注时的事件推送
					//XXX may be ignore.
				}
			}else{
				String evtType = event.getEvent();
				if(evtType.equals(EventType.SUBSCRIBE)){//普通的关注事件
					//FIXME 获取自动关注的回复
				}else if(evtType.equals(EventType.UNSUBSCRIBE)){//普通的取消关注事件
					//ignore
				}
			}
		}
		return result;
	}
	
	
	
	/**
	 * 关键词回复	"
	 *  1、提供关键词包含匹配自动回复，自动回复内容除文本外，还可选择素材库素材匹配（同微信关键词自动回复功能）
		2、超级管理员模式下的关键词具备最高优先权，与子公众账号关键词冲突是，优先返回超级管理员设置的自动回复。"
	 * 
	 * 	菜单设置
		关注回复
		默认回复
		关键词回复
	 */
	
	private static AutoReplyRule instance = null;
	public static AutoReplyRule getInstance(){
		if(instance==null){
			synchronized (AutoReplyRule.class) {
				if(instance==null)
					instance = new AutoReplyRule();
			}
		}
		return instance;
	}
	private AutoReplyRule(){}
}
