package com.dragon.apps.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * 标签
 */
public class WxTag extends Model<WxTag> {
	public static final String tableName = "wx_tag";

	private static final long serialVersionUID = 1L;
	public static final WxTag dao = new WxTag();
	
	public static final String id = "id";//主键,Long
	public static final String name = "name";//String
	public static final String accountId = "account_id";//Long关联到微信公众号的主键
	
	public List<WxTag> getList(Long accountId){
		if(accountId==null){
			return null;
		}
		return find("select * from "+WxTag.tableName+" where "+WxTag.accountId+"="+accountId);
	}
}
