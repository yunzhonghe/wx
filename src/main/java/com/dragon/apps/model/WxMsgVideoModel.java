package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgVideoModel extends Model<WxMsgVideoModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String thunbMediaId ="thunb_media_id";
	private String mediaId = "media_id";
	private String id ="id";

/*	private WxMsgVideoModel() {
	};

	private static WxMsgVideoModel instance = new WxMsgVideoModel();

	public static WxMsgVideoModel getInstance() {
		return instance;
	}
*/
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

	public long getId() {
		return getLong(id);
	}

	public WxMsgVideoModel setId(long id) {
		return set(this.id,id);
	}

}
