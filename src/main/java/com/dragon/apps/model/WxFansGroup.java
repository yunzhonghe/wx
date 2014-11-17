package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 微信粉丝的标签信息
 */
public class WxFansGroup extends Model<WxFansGroup> {
	public static final String tableName = "wx_fans_tag";

	private static final long serialVersionUID = 1L;
	public static final WxFansGroup dao = new WxFansGroup();
	//多对多，无主键
	public static final String openId = "openid";//String,粉丝id
	public static final String group_id = "group_id";//Long,对应我们的分组表的id
	
}
