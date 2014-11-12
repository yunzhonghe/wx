package com.dragon.adapter.event;

import com.dragon.apps.service.WxFansHandleService;
import com.dragon.spider.handle.EventHandle;
import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.req.BaseEvent;

public class EventService implements EventHandle{
	private WxFansHandleService hanleService = null;////接受事件、自动回复
	/**
	 * 收到事件触发:
	 * 1, 检查自动回复
	 */
	public BaseMsg handle(BaseEvent event) {
		// TODO Auto-generated method stub
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
