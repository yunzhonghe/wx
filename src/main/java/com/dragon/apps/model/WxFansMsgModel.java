package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxFansMsgModel extends Model<WxFansMsgModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id = "id";
	private String fansId = "fans_id";
	private String city = "city";
	private String province = "province";
	private String country = "country";
	private String gender = "gender";
	private String signature = "signature";

	public long getId() {
		return getLong(id);
	}

	public WxFansMsgModel setId(long id) {
		return set(this.id, id);
	}

	public String getFansId() {
		return getStr(fansId);
	}

	public WxFansMsgModel setFansId(String fansId) {
		return set(this.fansId, fansId);
	}

	public String getCity() {
		return getStr(city);
	}

	public WxFansMsgModel setCity(String city) {
		return set(this.city, city);
	}

	public String getProvince() {
		return getStr(province);
	}

	public WxFansMsgModel setProvince(String province) {
		return set(this.province, province);
	}

	public String getCountry() {
		return getStr(country);
	}

	public WxFansMsgModel setCountry(String country) {
		return set(this.country, country);
	}

	public int getGender() {
		return getInt(gender);
	}

	public WxFansMsgModel setGender(int gender) {
		return set(this.gender, gender);
	}

	public String getSignature() {
		return getStr(signature);
	}

	public WxFansMsgModel setSignature(String signature) {
		return set(this.signature, signature);
	}
}
