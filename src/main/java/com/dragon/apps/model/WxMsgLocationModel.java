package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxMsgLocationModel extends Model<WxMsgLocationModel> {

	public static WxMsgLocationModel dao = new WxMsgLocationModel();
	private static final long serialVersionUID = 1L;
	private String locationX ="location_x";//string
	private String locationY = "location_y";//string
	private String scale = "scale";//int
	private String label = "label";//string
	private String id ="id";//long

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
	public Integer getScale() {
		return getInt(scale);
	}
	public WxMsgLocationModel setScale(Integer scale) {
		return set(this.scale,scale);		
	}
	public String getLabel() {
		return getStr(label);
	}
	public WxMsgLocationModel setLabel(String label) {
		return set(this.label,label);		
	}
	public Long getId() {
		return getLong(id);
	}
	public WxMsgLocationModel setId(Long id) {
		return set(this.id,id);
	}

}
