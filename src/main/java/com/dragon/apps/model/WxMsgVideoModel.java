package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgVideoModel extends Model<WxMsgVideoModel> {

	public static WxMsgVideoModel dao = new WxMsgVideoModel();
	private static final long serialVersionUID = 1L;
	private String thunbMediaId ="thunb_media_id";//string
	private String mediaId = "media_id";//string
	private String id ="id";//long

	public String getThunbMediaId() {
		return getStr(thunbMediaId);
	}
	public WxMsgVideoModel setThunbMediaId(String thunbMediaId) {		
		return set(this.thunbMediaId,thunbMediaId);
	}
	public String getMediaId() {
		return getStr(mediaId);
	}
	public WxMsgVideoModel setMediaId(String mediaId) {
		return set(this.mediaId,mediaId);		
	}
	public Long getId() {
		return getLong(id);
	}
	public WxMsgVideoModel setId(Long id) {
		return set(this.id,id);
	}
}
