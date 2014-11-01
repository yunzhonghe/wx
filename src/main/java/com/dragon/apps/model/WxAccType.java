package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;
/**
 * wx_account_type, use cache to query.
 */
public class WxAccType extends Model<WxAccType>{
	private static final long serialVersionUID = 1L;
	public static WxAccType dao = new WxAccType();
	
	public static String getTableName(){
		return "wx_account_type";
	}
	
	public static final String ID = "id";//int
	private static final String NAME = "name";
	public int getId() {
		return getInt(ID);
	}
	public WxAccType setId(int id) {
		return set(ID,id);
	}
	public String getName() {
		return getStr(NAME);
	}
	public WxAccType setName(String name) {
		return set(NAME,name);
	}
}
