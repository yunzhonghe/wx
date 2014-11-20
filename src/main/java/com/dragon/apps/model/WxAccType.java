package com.dragon.apps.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

/**
 * wx_account_type, use cache to query.
 * 微信帐号类型(公众服务号、订阅号等)
 * @author LiuJian
 */
public class WxAccType extends Model<WxAccType>{
	/**
		insert into wx_account_type(id,name) values(10,'订阅号');
		insert into wx_account_type(id,name) values(11,'认证订阅号');
		insert into wx_account_type(id,name) values(20,'服务号');
		insert into wx_account_type(id,name) values(21,'认证服务号');
	 */
	private static final long serialVersionUID = 1L;
	public static WxAccType dao = new WxAccType();
	public static final String tableName = "wx_account_type";
	//缓存
	public static Map<Integer,WxAccType> accTypeCache = new HashMap<Integer,WxAccType>();
	//初始载入
	static{
		List<WxAccType> ls = dao.find("select * from "+tableName);
		if(ls!=null && ls.size()>0){
			for(WxAccType wt : ls){
				accTypeCache.put(wt.getId(), wt);
			}
		}
	}
	public WxAccType getWxAccTypeByCache(Integer id){
		WxAccType acct = accTypeCache.get(id);
		if(acct==null){
			acct = findById(id);
			accTypeCache.put(id, acct);
		}
		return acct;
	}
	public List<WxAccType> getWxAccTypeList(){
		return new ArrayList<WxAccType>(accTypeCache.values());
	}
	
	public static final String ID = "id";//int
	private static final String NAME = "name";
	public Integer getId() {
		return getInt(ID);
	}
	public WxAccType setId(Integer id) {
		return set(ID,id);
	}
	public String getName() {
		return getStr(NAME);
	}
	public WxAccType setName(String name) {
		return set(NAME,name);
	}
}
