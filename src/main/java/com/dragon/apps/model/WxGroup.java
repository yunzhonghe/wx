package com.dragon.apps.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * 粉丝分组对象
 */
public class WxGroup extends Model<WxGroup> {
	public static final String tableName = "wx_group";

	private static final long serialVersionUID = 1L;
	public static final WxGroup dao = new WxGroup();
	
	public static final String id = "id";//主键,Long
	public static final String name = "name";//String
	public static final String groupid = "groupid";//String,微信服务器上的组id
	public static final String accountId = "account_id";//Long关联到微信公众号的主键
	
	public List<WxGroup> getList(Long accountId){
		if(accountId==null){
			return null;
		}
		return find("select * from "+WxGroup.tableName+" where "+WxGroup.accountId+"="+accountId);
	}
}
