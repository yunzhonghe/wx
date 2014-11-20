package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgLinkModel extends Model<WxMsgLinkModel> {

	public static WxMsgLinkModel dao = new WxMsgLinkModel();
	private static final long serialVersionUID = 1L;
	private String title ="title";//string
	private String description = "description";//string
	private String url = "url";//string
	private String id ="id";//long

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
	public Long getId() {
		return getLong(id);
	}
	public WxMsgLinkModel setId(Long id) {
		return set(this.id,id);
	}
}
