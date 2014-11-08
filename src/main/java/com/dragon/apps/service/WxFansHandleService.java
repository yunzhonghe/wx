package com.dragon.apps.service;

import com.dragon.apps.model.WxFansModel;
import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.TextMsg;
import com.dragon.spider.message.req.BaseEvent;
import com.dragon.spider.message.req.QrCodeEvent;
import com.dragon.spider.service.ConstantWx;

public class WxFansHandleService {

	/**
	 * 处理扫描二维码事件，有需要时子类重写
	 * 
	 * @param event
	 *            扫描二维码事件对象
	 * @return 响应消息对象
	 */
	public BaseMsg handleQrCodeEvent(QrCodeEvent event) {
		WxFansModel model = WxFansModel.getInstance();		
		String openId = event.getFromUserName();		
		model = model.getByOpenId(openId);
		if (null != model) {
			model.setSubscribe(ConstantWx.subscribe);
			model.setSourceFrom(event.getTicket());
			model.update();
		}else{
			model = WxFansModel.getInstance();	
			model.setName(event.getFromUserName());
			model.setCreateTime(event.getCreateTime());
			model.setOpenId(event.getFromUserName());
			model.setSubscribe(ConstantWx.subscribe);
			model.setSourceFrom(event.getTicket());
			model.save();
		}	
		return new TextMsg("感谢您通过二维码进行关注!");
	}

	/**
	 * 处理添加关注事件，有需要时子类重写
	 * 
	 * @param event
	 *            添加关注事件对象
	 * @return 响应消息对象
	 */
	public BaseMsg handleSubscribe(BaseEvent event) {
		WxFansModel model = WxFansModel.getInstance();
		model.setName(event.getFromUserName());
		model.setCreateTime(event.getCreateTime());
		model.setOpenId(event.getFromUserName());
		model.setSubscribe(ConstantWx.subscribe);
		model.save();
		return new TextMsg("感谢您的关注!");
	}

	/**
	 * 处理取消关注事件，有需要时子类重写
	 * 
	 * @param event
	 *            取消关注事件对象
	 * @return 响应消息对象
	 */
	public BaseMsg handleUnsubscribe(BaseEvent event) {
		if (event != null) {
			String openId = event.getFromUserName();
			WxFansModel model = WxFansModel.getInstance();
			model = model.getByOpenId(openId);
			if (null != model) {
				model.setSubscribe(ConstantWx.unSubscribe);
				model.update();
			}
		}
		return null;
	}
}
