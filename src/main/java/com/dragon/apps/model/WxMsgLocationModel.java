package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgLocationModel extends Model<WxMsgLocationModel> {

	public static WxMsgLocationModel dao = new WxMsgLocationModel();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String locationX ="location_x";
	private String locationY = "location_y";
	private String scale = "scale";
	private String label = "label";
	private String id ="id";

/*	private WxMsgLocationModel() {
	};

	private static WxMsgLocationModel instance = new WxMsgLocationModel();

	public static WxMsgLocationModel getInstance() {
		return instance;
	}*/

	public String getLocationX() {
		return getStr(locationX);
	}

	public WxMsgLocationModel setLocationX(String locationX) {		
		return set(this.locationX,locationX);
	}

	public String getLocationY() {
		return getStr(locationY);
	}

	public WxMsgLocationModel setLocationY(String locationY) {
		return set(this.locationY,locationY);		
	}
	
	public int getScale() {
		return getInt(scale);
	}

	public WxMsgLocationModel setScale(int scale) {
		return set(this.scale,scale);		
	}
	
	
	public String getLabel() {
		return getStr(label);
	}

	public WxMsgLocationModel setLabel(String label) {
		return set(this.label,label);		
	}

	public long getId() {
		return getLong(id);
	}

	public WxMsgLocationModel setId(long id) {
		return set(this.id,id);
	}

}
