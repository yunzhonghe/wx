package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgVoiceModel extends Model<WxMsgVoiceModel> {

	public static WxMsgVoiceModel dao = new WxMsgVoiceModel();
	private static final long serialVersionUID = 1L;
	private String format ="format";//string
	private String mediaId = "media_id";//string
	private String id ="id";//long

	public String getFormat() {
		return getStr(format);
	}
	public WxMsgVoiceModel setFormat(String format) {		
		return set(this.format,format);
	}
	public String getMediaId() {
		return getStr(mediaId);
	}
	public WxMsgVoiceModel setMediaId(String mediaId) {
		return set(this.mediaId,mediaId);		
	}
	public Long getId() {
		return getLong(id);
	}
	public WxMsgVoiceModel setId(Long id) {
		return set(this.id,id);
	}
}
