package com.dragon.adapter.event;

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
 */
public class EventService implements EventHandle{
	private WxFansHandleService hanleService = null;////接受事件、自动回复
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
	 * 收到事件触发:
	 * 1, 检查自动回复
	 */
	public BaseMsg handle(BaseEvent event) {
		// TODO Auto-generated method stub
		if(event instanceof LocationEvent){
			LocationEvent evt = (LocationEvent)event;
			//FIXME does hanleService need to handle this ?
		}else if(event instanceof MenuEvent){
			MenuEvent evt = (MenuEvent)event;
			//FIXME does hanleService need to handle this ?
		}else if(event instanceof QrCodeEvent){
			QrCodeEvent evt = (QrCodeEvent)event;
			hanleService.handleQrCodeEvent(evt);
			//FIXME
			//含有关注事件，自动回复
		}else{
			String evtType = event.getEvent();
			if(evtType.equals(EventType.SUBSCRIBE)){
				//FIXME
				//关注，自动回复
				hanleService.handleSubscribe(event);
			}else if(evtType.equals(EventType.UNSUBSCRIBE)){
				hanleService.handleUnsubscribe(event);
			}else if(evtType.equals(EventType.CLICK)){//点击菜单拉取消息时的事件推送
				//FIXME does hanleService need to handle this ?
			}else if(evtType.equals(EventType.VIEW)){//点击菜单跳转链接时的事件推送
				//FIXME does hanleService need to handle this ?
			}
		}
		return null;
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
