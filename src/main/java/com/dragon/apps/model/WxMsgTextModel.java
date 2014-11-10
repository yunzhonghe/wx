package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgTextModel extends Model<WxMsgTextModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content ="content";
	private String id ="id";

	/*private WxMsgTextModel() {
	};

	private static WxMsgTextModel instance = new WxMsgTextModel();

	public static WxMsgTextModel getInstance() {
		return instance;
	}*/

	public String getContent() {
		return getStr(content);
	}

	public WxMsgTextModel setContent(String content) {		
		return set(this.content,content);
	}

	
	public long getId() {
		return getLong(id);
	}

	public WxMsgTextModel setId(long id) {
		return set(this.id,id);
	}

}

