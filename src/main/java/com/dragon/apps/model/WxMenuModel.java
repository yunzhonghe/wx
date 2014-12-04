package com.dragon.apps.model;

import java.util.List;

import com.dragon.apps.utils.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 菜单
 */
public class WxMenuModel extends Model<WxMenuModel> {
	public static final String tableName = "wx_menu";
	private static final long serialVersionUID = 1L;
	public static final WxMenuModel dao = new WxMenuModel();
	
	
	//http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E5%88%9B%E5%BB%BA%E6%8E%A5%E5%8F%A3
//	public static final class MenuType{
	////refers to com.dragon.spider.api.entity.MenuType
//		public static final String click = "click";//点击推事件
//		public static final String view = "view";//跳转URL
//		public static final String scancode_push = "scancode_push";//扫码推事件
//		public static final String scancode_waitmsg = "scancode_waitmsg";//扫码推事件且弹出“消息接收中”提示框
//		public static final String pic_sysphoto = "pic_sysphoto";//扫码推事件且弹出“消息接收中”提示框
//		public static final String pic_photo_or_album = "pic_photo_or_album";//弹出拍照或者相册发图
//		public static final String pic_weixin = "pic_weixin";//弹出微信相册发图器
//		public static final String location_select = "location_select";//弹出微信相册发图器
//	}
	
	public static final String id = "id";//主键,Long
	public static final String parentid = "parentid";//Long
	public static final String type = "type";//String
	public static final String name = "name";//String
	public static final String key = "key";//String,菜单的key值，菜单为click时，该字段必须
	public static final String url = "url";//String
	public static final String keyRspType = "key_rsp_type";//Integer,对应key的回复消息类型
	public static final String keyRspContent = "key_rsp_content";//String,对应key的回复消息内容，如果类型为文本，则为内容；类型为其它，则为关联id
	public static final String accountId = "account_id";//Long关联到微信公众号的主键
	/**
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `parentid` BIGINT NULL,
  `type` VARCHAR(50) NULL,
  `name` VARCHAR(20) NULL,
  `key` VARCHAR(64) NULL,
  `url` VARCHAR(1024) NULL,
  key_rsp_type smallint,
  key_rsp_content varchar(1024),
  'account_id' BIGINT NULL,
	 */
	
	public List<WxMenuModel> getAllMenus(Long accountId){
		if(accountId==null){
			return null;
		}
		return find("select * from "+tableName+" where "+WxMenuModel.accountId+"="+accountId+" order by "+parentid);
	}
	public void deleteAllMenus(Long accountId){
		if(accountId==null){
			return;
		}
		String sql = "delete from "+tableName+" where "+WxMenuModel.accountId+"="+accountId;
		Db.update(sql);
	}
	public WxMenuModel getMenuByKeyAndAccount(Long accountId,String key){
		if(StrUtils.isEmpty(key)||accountId==null){
			return null;
		}
		return findFirst("select * from "+tableName+" where "+WxMenuModel.accountId+"="+accountId+" and "+WxMenuModel.key+"='"+key+"'");
	}
	/**
	 * 子菜单对象
	 */
	private List<WxMenuModel> subMenuModels;
	public List<WxMenuModel> getSubMenuModels() {
		return subMenuModels;
	}
	public void setSubMenuModels(List<WxMenuModel> subMenuModels) {
		this.subMenuModels = subMenuModels;
	}
	private Long _tempid;//在新增菜单时使用；页面新增菜单的id为负数与数据库的id作区别。
	public Long get_tempid() {
		return _tempid;
	}
	public void set_tempid(Long _tempid) {
		this._tempid = _tempid;
	}
}
