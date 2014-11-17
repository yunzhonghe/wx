package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 微信粉丝的标签信息
 */
public class WxFansTag extends Model<WxFansTag> {
	public static final String tableName = "wx_fans_tag";

	private static final long serialVersionUID = 1L;
	public static final WxFansTag dao = new WxFansTag();
	//多对多，无主键
	public static final String openid = "openid";//String,粉丝id
	public static final String tagid = "tagid";//Long,标签id
	
}
