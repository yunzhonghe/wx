package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;
/**
 * wx_dic_region, use cache to query.
 */
public class DicRegion extends Model<DicRegion>{
	private static final long serialVersionUID = 1L;
	public static DicRegion dao = new DicRegion();
	
	public static String getTableName(){
		return "wx_dic_region";
	}
	
	public static final String CODE = "code";//VARCHAR
	private static final String NAME = "name";//VARCHAR
	private static final String TYPE = "type";//INT
	private static final String PARENTCODE = "parentcode";//
	public String getCode() {
		return getStr(CODE);
	}
	public String getName() {
		return getStr(NAME);
	}
	public int getType() {
		return getInt(TYPE);
	}
	public String getParentcode() {
		return getStr(PARENTCODE);
	}
	public DicRegion setCode(String code) {
		return set(CODE,code);
	}
	public DicRegion setName(String name) {
		return set(NAME,name);
	}
	public DicRegion setType(int type) {
		return set(TYPE,type);
	}
	public DicRegion setParentcode(String parentcode) {
		return set(PARENTCODE,parentcode);
	}
}
