package com.dragon.apps.model;

import com.dragon.apps.web.config.ClConfig;
import com.jfinal.plugin.activerecord.Model;

public class WxMsgTextModel extends Model<WxMsgTextModel> {

	public static WxMsgTextModel dao = new WxMsgTextModel();
	private static final long serialVersionUID = 1L;
	private String content ="content";//string
	private String id ="id";//long

	public WxMsgTextModel getMsgByContentId(String contentId){
		return findFirst("select * from "+ClConfig.WX_MESSAGE_TEXT_TABLE +" where "+id+"="+contentId);
	}
	
	public String getContent() {
		return getStr(content);
	}
	public WxMsgTextModel setContent(String content) {		
		return set(this.content,content);
	}
	public Long getId() {
		return getLong(id);
	}
	public WxMsgTextModel setId(Long id) {
		return set(this.id,id);
	}
}

