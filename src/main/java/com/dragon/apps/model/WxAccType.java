package com.dragon.apps.model;

/**
 * wx_account_type, use cache to query.
 */
public class WxAccType extends BaseModel<WxAccType>{
	private static final long serialVersionUID = 1L;
	public static WxAccType dao = new WxAccType();
	public static final String tableName = "wx_account_type";
	
	@Override
	protected String geyPrimaryKeyName() {
		return ID;
	}
	protected String getTableName(){
		return tableName;
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
