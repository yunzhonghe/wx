package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgLinkModel extends Model<WxMsgLinkModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title ="title";
	private String description = "description";
	private String url = "url";
	private String id ="id";

	private WxMsgLinkModel() {
	};

	private static WxMsgLinkModel instance = new WxMsgLinkModel();

	public static WxMsgLinkModel getInstance() {
		return instance;
	}

	public String getTitle() {
		return getStr(title);
	}

	public WxMsgLinkModel setTitle(String title) {		
		return set(this.title,title);
	}

	public String getUrl() {
		return getStr(url);
	}

	public WxMsgLinkModel setUrl(String url) {
		return set(this.url,url);		
	}
	
	public String getDescription() {
		return getStr(description);
	}

	public WxMsgLinkModel setDescription(String description) {
		return set(this.description,description);		
	}

	public long getId() {
		return getLong(id);
	}

	public WxMsgLinkModel setId(long id) {
		return set(this.id,id);
	}

}
