package com.dragon.adapter.message;

import com.dragon.adapter.NeedFix;
import com.dragon.adapter.service.AutoReplyRule;
import com.dragon.adapter.service.WxHandleCache;
import com.dragon.apps.model.WxMessageModel;
import com.dragon.apps.service.MessageHandleService;
import com.dragon.apps.service.MessageRspHandleService;
import com.dragon.spider.handle.MessageHandle;
import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.req.BaseReqMsg;
import com.dragon.spider.message.req.ImageReqMsg;
import com.dragon.spider.message.req.LinkReqMsg;
import com.dragon.spider.message.req.LocationReqMsg;
import com.dragon.spider.message.req.TextReqMsg;
import com.dragon.spider.message.req.VideoReqMsg;
import com.dragon.spider.message.req.VoiceReqMsg;

/**
 *  需要在WxAbstractAPIController中去掉WxFansHandleService的定义，并添加当前类的引用
 */
public class MessageService implements MessageHandle{
	private MessageHandleService hanleService = null;//接受消息、自动回复
//	private MessageRspHandleService service = null;//主动发送消息
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=发送被动响应消息
	 * 收到消息触发:
	 * 1, 保存到db
	 * 2, 检查自动回复
	 * @return BaseMsg 已经封装好toXml
	 */
	public BaseMsg handle(BaseReqMsg message) {
		if(WxHandleCache.getInstance().hasMessageBeenHandled(message)){
			return null;
		}
		//获取自动回复内容
		BaseMsg reply = AutoReplyRule.getInstance().autoReply(message);
		//消息处理，持久化
		if(message instanceof TextReqMsg){
			TextReqMsg msg = (TextReqMsg)message;
			hanleService.handleTextMsg(msg,reply);
		}else if(message instanceof ImageReqMsg){
			ImageReqMsg msg = (ImageReqMsg)message;
			hanleService.handleImageMsg(msg,reply);
		}else if(message instanceof VoiceReqMsg){
			VoiceReqMsg msg = (VoiceReqMsg)message;
			hanleService.handleVoiceMsg(msg,reply);
		}else if(message instanceof VideoReqMsg){
			VideoReqMsg msg = (VideoReqMsg)message;
			hanleService.handleVideoMsg(msg,reply);
		}else if(message instanceof LocationReqMsg){
			LocationReqMsg msg = (LocationReqMsg)message;
			hanleService.handleLocationMsg(msg,reply);
		}else if(message instanceof LinkReqMsg){
			LinkReqMsg msg = (LinkReqMsg)message;
			hanleService.handleLinkMsg(msg,reply);
		}
		return reply;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=发送客服消息
	 * 主动 发送/回复 消息
	 * @param toUserOpenid 发给的用户(粉丝、关注着)
	 * @param message
	 * @return
	 */
	public boolean sendMessage(String toUserOpenid,WxMessageModel message,String originalId){
		boolean result = true;
		MessageRspHandleService service = new MessageRspHandleService(NeedFix.getApiConfig(originalId));
		//FIXME 1, does service.sendCustomMessage should return error?
		service.sendCustomMessage(toUserOpenid, MessageAdapter.getMsgByModel(message));
		return result;
	}
	
	private static MessageService instance = null;
	public static MessageService getInstance(){
		if(instance==null){
			synchronized (MessageService.class) {
				if(instance==null)
					instance = new MessageService();
			}
		}
		return instance;
	}
	private MessageService(){
		hanleService = new MessageHandleService();
//		service = new MessageRspHandleService(NeedFix.getApiConfig());
	}
}
