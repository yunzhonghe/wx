package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgImageModel extends Model<WxMsgImageModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String picUrl ="pic_url";
	private String mediaId = "media_id";
	private String id ="id";

/*	private WxMsgImageModel() {
	};

	private static WxMsgImageModel instance = new WxMsgImageModel();

	public static WxMsgImageModel getInstance() {
		return instance;
	}
*/
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

	public long getId() {
		return getLong(id);
	}

	public WxMsgImageModel setId(long id) {
		return set(this.id,id);
	}

}
