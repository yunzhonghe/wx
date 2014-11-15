package com.dragon.apps.model;

import com.dragon.apps.utils.StrUtils;
import com.jfinal.plugin.activerecord.Model;

/**
 * 微信粉丝的一些信息
 */
public class WxFansInfo extends Model<WxFansInfo> {
	public static final String tableName = "wx_fans_info";

	private static final long serialVersionUID = 1L;
	public static final WxFansInfo dao = new WxFansInfo();
	
	/**
	 * 参数	说明
subscribe	用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
openid	用户的标识，对当前公众号唯一
nickname	用户的昵称
sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
city	用户所在城市
country	用户所在国家
province	用户所在省份
language	用户的语言，简体中文为zh_CN
headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	 */
	public static final String id = "id";//主键,Long
	public static final String openId = "openid";//String
	public static final String nickname = "nickname";//String
	public static final String sex = "sex";//String,default 0
	public static final String city = "city";//String
	public static final String country = "country";//String
	public static final String province = "province";//String
	public static final String language = "language";//String
	public static final String headimgurl = "headimgurl";//String
	public static final String subscribe_time = "subscribe_time";//TimeStamp
	public static final String unionid = "unionid";////String
	
	public WxFansInfo findByOpenId(String openId){
		if(StrUtils.isEmpty(openId)){
			return null;
		}
		return findFirst("select * from "+tableName + " where " +WxFansInfo.openId+"='"+openId+"'");
	}
}
