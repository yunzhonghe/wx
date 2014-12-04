package com.dragon.apps.service;

import com.dragon.adapter.fans.FansService;
import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxFansInfo;
import com.dragon.apps.model.WxFansModel;
import com.dragon.apps.utils.ModelUtils;
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
		WxFansModel model =new WxFansModel();		
		String openId = event.getFromUserName();		
		model = model.getByOpenId(openId);
		if (null != model) {
			model.setSubscribe(ConstantWx.subscribe);
			model.setSourceFrom(event.getTicket());
			model.update();
		}else{
			model = new WxFansModel();	
			model.setName(event.getFromUserName());
			model.setCreateTime(event.getCreateTime());
			model.setWxAccountId(WxAccount.dao.getIdByOriginalId(event.getToUserName()));
			model.setOpenId(event.getFromUserName());
			model.setSubscribe(ConstantWx.subscribe);
			model.setSourceFrom(event.getTicket());
			model.save();
		}
		handleWxFansInfo(openId,event.getToUserName());
		return new TextMsg("感谢您通过二维码进行关注!");
	}
	/**
	 * 加载粉丝的详细信息
	 * @param openid
	 */
	private void handleWxFansInfo(String openid,String originalId){
		System.out.println("handleWxFansInfo-openid:"+openid);
		WxFansModel wfm = FansService.getInstance().getFansInfo(openid,originalId);
		System.out.println("handleWxFansInfo-wfm:"+wfm.toJson());
		WxFansInfo info = wfm.getWxFansInfo();
		WxFansInfo exists = WxFansInfo.dao.findByOpenId(openid);
		if(exists==null){
			info.save();
		}else{
			if(info.get(WxFansInfo.nickname)!=null){//防止没拉取到数据，用null更新了数据库.
				ModelUtils.setModelProperty(exists, info, WxFansInfo.id);
				info.update();
			}
		}
	}
	/**
	 * 处理添加关注事件，有需要时子类重写
	 * 
	 * @param event
	 *            添加关注事件对象
	 * @return 响应消息对象
	 */
	public BaseMsg handleSubscribe(BaseEvent event) {
		WxFansModel model = new WxFansModel();
		model.setName(event.getFromUserName());
		model.setCreateTime(event.getCreateTime());
		model.setOpenId(event.getFromUserName());
		model.setSubscribe(ConstantWx.subscribe);
		model.setWxAccountId(WxAccount.dao.getIdByOriginalId(event.getToUserName()));
		model.save();
		handleWxFansInfo(event.getFromUserName(),event.getToUserName());
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
			WxFansModel model = new WxFansModel();
			model = model.getByOpenId(openId);
			if (null != model) {
				model.setSubscribe(ConstantWx.unSubscribe);
				model.update();
			}
		}
		return null;
	}
}
