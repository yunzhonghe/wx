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
	
	public static final String ID = "id";//bigint
	private static final String NAME = "name";//VARCHAR
	private static final String TYPE = "type";//INT
	private static final String PARENTID = "parentid";//bigint,can be null.
	public Long getId() {
		return getLong(ID);
	}
	public String getName() {
		return getStr(NAME);
	}
	public int getType() {
		return getInt(TYPE);
	}
	public Long getParentid() {
		return getLong(PARENTID);
	}
	public DicRegion setId(long id) {
		return set(ID,id);
	}
	public DicRegion setName(String name) {
		return set(NAME,name);
	}
	public DicRegion setType(int type) {
		return set(TYPE,type);
	}
	public DicRegion setParentcode(Long parentid) {
		return set(PARENTID,parentid);
	}
}
