package com.dragon.adapter.message;

import com.dragon.apps.model.WxMessageModel;
import com.dragon.apps.model.WxMsgTextModel;
import com.dragon.spider.message.BaseMsg;
import com.dragon.spider.message.ImageMsg;
import com.dragon.spider.message.TextMsg;
import com.dragon.spider.message.VideoMsg;
import com.dragon.spider.message.VoiceMsg;
import com.dragon.spider.message.req.ReqType;

public class MessageAdapter {
	public static BaseMsg getMsgByModel(WxMessageModel messageModel){
		BaseMsg msg = null;
		if(messageModel!=null){
			switch(messageModel.getType()){
			case ReqType.TEXTID:
				TextMsg tm = new TextMsg();
				WxMsgTextModel wtm = WxMsgTextModel.dao.getMsgById(messageModel.getContentId());
				if(wtm!=null){
					tm.setContent(wtm.getContent());
				}
				msg = tm;
				msg.setMsgType(ReqType.TEXT);
				break;
			case ReqType.IMAGEID:
				msg = new ImageMsg();
				//FIXME
				msg.setMsgType(ReqType.IMAGE);
				break;
			case ReqType.LINKID:
				//FIXME
				break;
			case ReqType.LOCATIONID:
				//FIXME
				break;
			case ReqType.VOICEID:
				msg = new VoiceMsg();
				//FIXME
				msg.setMsgType(ReqType.VOICE);
				break;
			case ReqType.VIDEOID:
				msg = new VideoMsg();
				//FIXME
				msg.setMsgType(ReqType.VIDEO);
				break;
			}
			if(msg!=null){
				msg.setCreateTime(messageModel.getCreateTime());
				msg.setFromUserName(messageModel.getFrom());
				msg.setToUserName(messageModel.getTo());
			}
		}
		return msg;
	}
}
