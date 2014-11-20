package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgImageModel extends Model<WxMsgImageModel> {

	public static WxMsgImageModel dao = new WxMsgImageModel();
	
	private static final long serialVersionUID = 1L;
	private String picUrl ="pic_url";//string
	private String mediaId = "media_id";//string
	private String id ="id";//long

	public String getPicUrl() {
		return getStr(picUrl);
	}
	public WxMsgImageModel setPicUrl(String picUrl) {		
		return set(this.picUrl,picUrl);
	}
	public String getMediaId() {
		return getStr(mediaId);
	}
	public WxMsgImageModel setMediaId(String mediaId) {
		return set(this.mediaId,mediaId);		
	}
	public Long getId() {
		return getLong(id);
	}
	public WxMsgImageModel setId(Long id) {
		return set(this.id,id);
	}
}
