package com.dragon.adapter.service;

import java.util.List;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAnswerRule;
import com.dragon.apps.utils.DateUtils;
import com.dragon.apps.utils.StrUtils;
import com.dragon.apps.web.module.wxautoreply.WxAnswerRuleService;
import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.TextMsg;
import com.dragon.spider.message.req.BaseEvent;
import com.dragon.spider.message.req.BaseReqMsg;
import com.dragon.spider.message.req.EventType;
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

public class AutoReplyRule {
	/**
	 * FIXME
	 * 1、获取当前管理员帐号信息
	 * 2、获取超级管理员的关键字回复配置信息
	 * 3、获取当前管理员的回复配置信息
	 */
	
	public BaseMsg autoReply(BaseReqMsg message){
		BaseMsg msg = null;
		if(message!=null){
			if(message instanceof TextReqMsg){
				TextReqMsg txt = (TextReqMsg)message;
				String content = txt.getContent();
				//关键字回复
				msg = getKeyWordReply(message.getToUserName(), content);
				if(msg==null){//默认回复
					msg = getDefaultReply(message.getToUserName());
				}
			}else if(message instanceof ImageReqMsg){
//				ImageReqMsg image = (ImageReqMsg)message;
				//默认回复
				msg = getDefaultReply(message.getToUserName());
			}else if(message instanceof VoiceReqMsg){
//				VoiceReqMsg voice = (VoiceReqMsg)message;
				//默认回复
				msg = getDefaultReply(message.getToUserName());
			}else if(message instanceof VideoReqMsg){
//				VideoReqMsg video = (VideoReqMsg)message;
				//默认回复
				msg = getDefaultReply(message.getToUserName());
			}else if(message instanceof LocationReqMsg){
//				LocationReqMsg location = (LocationReqMsg)message;
				//默认回复
				msg = getDefaultReply(message.getToUserName());
			}else if(message instanceof LinkReqMsg){
//				LinkReqMsg link = (LinkReqMsg)message;
				//默认回复
				msg = getDefaultReply(message.getToUserName());
			}
		}
		if(msg!=null){
			msg.setCreateTime(DateUtils.getCurrentTimeSeconds());
			msg.setFromUserName(message.getToUserName());
			msg.setToUserName(message.getFromUserName());
		}
		return msg;
	}
	public BaseMsg autoReply(BaseEvent event){
		BaseMsg msg = null;
		if(event!=null){
			if(event instanceof LocationEvent){
	//			LocationEvent evt = (LocationEvent)event;
			}else if(event instanceof MenuEvent){//菜单事件
				MenuEvent evt = (MenuEvent)event;
				String evtType = evt.getEvent();
				if(evtType.equals(EventType.CLICK)){//点击菜单拉取消息时的事件推送
					//FIXME 获取菜单设置
					//获取数据库中菜单click设置的消息返回
				}else if(evtType.equals(EventType.VIEW)){//点击菜单跳转链接时的事件推送
					//获取菜单设置
				}
			}else if(event instanceof QrCodeEvent){
				QrCodeEvent evt = (QrCodeEvent)event;
				String evtType = evt.getEvent();
				if(evtType.equals(EventType.SUBSCRIBE)){//用户未关注时，进行关注后的事件推送
					//获取自动关注的回复
					msg = getSubscribeReply(event.getToUserName());
				}else if(evtType.equals(EventType.SCAN)){//用户已关注时的事件推送
					//may be ignore.
				}
			}else{
				String evtType = event.getEvent();
				if(evtType.equals(EventType.SUBSCRIBE)){//普通的关注事件
					//获取自动关注的回复
					msg = getSubscribeReply(event.getToUserName());
				}else if(evtType.equals(EventType.UNSUBSCRIBE)){//普通的取消关注事件
					//ignore
				}
			}
		}
		if(msg!=null){
			msg.setCreateTime(System.currentTimeMillis()/1000);//secs.
			msg.setFromUserName(event.getToUserName());
			msg.setToUserName(event.getFromUserName());
		}
		return msg;
	}
	private BaseMsg getKeyWordReply(String originalId,String text){
		if(StrUtils.isEmpty(text)){
			return null;
		}
		List<WxAnswerRule> rules = WxAnswerRuleService.getInstance().getKerWordRuleList(null);//super rules.
		WxAnswerRule war = getRelateKeyWordWxAnswerRule(text, rules);
		if(war==null){
			Long accountId = WxAccount.dao.getIdByOriginalId(originalId);
			rules = WxAnswerRuleService.getInstance().getKerWordRuleList(accountId);//sub rules.
			war = getRelateKeyWordWxAnswerRule(text, rules);
		}
		if(war!=null){
			return getMsgByAnswerRule(war);
		}
		return null;
	}
	private WxAnswerRule getRelateKeyWordWxAnswerRule(String text,List<WxAnswerRule> rules){
		if(rules!=null && rules.size()>0){
			for(WxAnswerRule war : rules){
				String keywords = war.getStr(WxAnswerRule.KEYWORD);
				if(StrUtils.isNotEmpty(keywords)){
					keywords = keywords.trim();
					int index = keywords.indexOf('|');
					if(index>-1){
						String [] keys = keywords.split("\\|");
						for(String key : keys){
							key = key.trim();
							if(StrUtils.isEmpty(key) && text.indexOf(key)>-1){
								return war;
							}
						}
					}else{
						if(text.indexOf(keywords)>-1){
							return war;
						}
					}
					
				}
			}
			
		}
		return null;
	}
	private BaseMsg getDefaultReply(String originalId){
		Long accountId = WxAccount.dao.getIdByOriginalId(originalId);
		WxAnswerRule war = WxAnswerRuleService.getInstance().getRuleById(null, WxAnswerRule.RULE_TYPE_DEFAULTA,accountId);
		return getMsgByAnswerRule(war);
	}
	private BaseMsg getSubscribeReply(String originalId){
		Long accountId = WxAccount.dao.getIdByOriginalId(originalId);
		WxAnswerRule war = WxAnswerRuleService.getInstance().getRuleById(null, WxAnswerRule.RULE_TYPE_SUBSCRIBE,accountId);
		return getMsgByAnswerRule(war);
	}
	private BaseMsg getMsgByAnswerRule(WxAnswerRule answerRule){
		if(answerRule!=null){
			Integer answerType = answerRule.getInt(WxAnswerRule.ANSWER_TYPE);
			if(answerType!=null){
				switch(answerType){
				case ReqType.TEXTID:
					TextMsg txt = new TextMsg();
					txt.setContent(answerRule.getStr(WxAnswerRule.ANSWER));
					txt.setMsgType(ReqType.TEXT);
					return txt;
				}
			}
		}
		return null;
	}
//	public static void main(String[] args) {
//		System.out.println("a|b".indexOf('|'));
//	}
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
