package com.dragon.adapter.event;

import com.dragon.adapter.AutoReplyRule;
import com.dragon.apps.service.WxFansHandleService;
import com.dragon.spider.handle.EventHandle;
import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.req.BaseEvent;
import com.dragon.spider.message.req.EventType;
import com.dragon.spider.message.req.LocationEvent;
import com.dragon.spider.message.req.MenuEvent;
import com.dragon.spider.message.req.QrCodeEvent;
/**
 *  FIXME
 *  需要在WxAbstractAPIController中去掉MessageHandleService的定义，并添加当前类的引用
 *  另外，WxAbstractAPIController中
 *  从 if (isNotBlank(ticket)) 到 if (eventType.equals(EventType.SUBSCRIBE)) 是否需要加上else.
 */
public class EventService implements EventHandle{
	private WxFansHandleService hanleService = null;////接受事件、自动回复
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
	 * 收到事件触发:
	 * 1, 检查自动回复
	 */
	public BaseMsg handle(BaseEvent event) {
		if(event instanceof LocationEvent){
//			LocationEvent evt = (LocationEvent)event;
		}else if(event instanceof MenuEvent){//菜单事件
			MenuEvent evt = (MenuEvent)event;
			String evtType = evt.getEvent();
			if(evtType.equals(EventType.CLICK)){//点击菜单拉取消息时的事件推送
				//FIXME does hanleService need to handle this ?
			}else if(evtType.equals(EventType.VIEW)){//点击菜单跳转链接时的事件推送
				//FIXME does hanleService need to handle this ?
			}
		}else if(event instanceof QrCodeEvent){
			QrCodeEvent evt = (QrCodeEvent)event;
			/**
			 * 用户扫描带场景值二维码时，可能推送以下两种事件：
					如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
					如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
			 */
			String evtType = evt.getEvent();
			if(evtType.equals(EventType.SUBSCRIBE)){//用户未关注时，进行关注后的事件推送
				hanleService.handleQrCodeEvent(evt);
			}else if(evtType.equals(EventType.SCAN)){//用户已关注时的事件推送
				//FIXME
			}
		}else{
			String evtType = event.getEvent();
			if(evtType.equals(EventType.SUBSCRIBE)){//普通的关注事件
				hanleService.handleSubscribe(event);
			}else if(evtType.equals(EventType.UNSUBSCRIBE)){//普通的取消关注事件
				hanleService.handleUnsubscribe(event);
			}
		}
		return AutoReplyRule.getInstance().autoReply(event);
	}
	
	private static EventService instance = null;
	public static EventService getInstance(){
		if(instance==null){
			synchronized (EventService.class) {
				if(instance==null)
					instance = new EventService();
			}
		}
		return instance;
	}
	private EventService(){
		hanleService = new WxFansHandleService();
	}
}
