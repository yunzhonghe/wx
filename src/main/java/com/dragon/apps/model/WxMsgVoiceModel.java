package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgVoiceModel extends Model<WxMsgVoiceModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String format ="format";
	private String mediaId = "media_id";
	private String id ="id";

	private WxMsgVoiceModel() {
	};

	private static WxMsgVoiceModel instance = new WxMsgVoiceModel();

	public static WxMsgVoiceModel getInstance() {
		return instance;
	}

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

	public long getId() {
		return getLong(id);
	}

	public WxMsgVoiceModel setId(long id) {
		return set(this.id,id);
	}

}
