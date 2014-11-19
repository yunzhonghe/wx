package com.dragon.apps.model;

import java.util.List;

import com.dragon.apps.model.base.BaseModel;

/**
 * wx_account_type, use cache to query.
 * 微信帐号类型(公众服务号、订阅号等)
 * @author LiuJian
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
	public List<WxAccType> getWxAccTypeList(){
		return find("select * from "+tableName);
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
