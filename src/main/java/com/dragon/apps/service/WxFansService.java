package com.dragon.apps.service;

import java.io.Serializable;
import java.util.List;

import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.model.WxTag;
import com.dragon.apps.model.bean.WxFansListCon;
import com.dragon.apps.utils.PageSet;
import com.dragon.apps.utils.StrUtils;
import com.dragon.apps.web.controller.BaseController;
import com.dragon.spider.service.ConstantWx;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
/**
 * 微信粉丝用户的业务操作，供wxfanscontroller调用
 * @author LiuJian
 *
 */
public class WxFansService implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String IN_PRARM_ERROR = "传入参数错误";
	
	public PageSet getFansList(WxFansListCon con ,PageSet pageSet){
		WxAdmin admin = BaseController.gerCurUser();
		if(admin!=null){
			long accountId = admin.getWxAccountId();
			if(accountId!=0){
				String select = "select wf.mark_name,wfi.nickname,wfi.sex,wfi.city,wfi.country,wfi.province,wfi.headimgurl";
				String center = " from wx_fans wf left join wx_fans_info wfi on wfi.openid=wf.open_id"
						+" where wf.subscribe='"+ConstantWx.subscribe+"' and wf.wx_account_id="+accountId;
				if(con!=null){
					if(StrUtils.isNotEmpty(con.getMarkName())){
						center += " and wf.mark_name like '"+con.getMarkName()+"%'";
					}
					if(StrUtils.isNotEmpty(con.getNickName())){
						center += " and wfi.nickname like '"+con.getNickName()+"%'";
					}
				}
				String orderby = " order by wfi.subscribe_time desc";
				int count = Db.queryLong("select count(1) from "+center).intValue();
				if (count > 0) {
					int startSize = (pageSet.getCurrPage() - 1) * pageSet.getPageSize();
					String sql = select + center + orderby + " limit " + startSize + "," + pageSet.getPageSize();;
					List<Record> ls = Db.find(sql);
					pageSet.setResultList(ls);
				}else{
					//FIXME call service to get fans in new thread.
				}
			}
		}
		return pageSet;
	}
	public List<WxTag> getWxTagList(){
		WxAdmin admin = BaseController.gerCurUser();
		if(admin!=null){
			long accountId = admin.getWxAccountId();
			if(accountId!=0){
				return WxTag.dao.getList(accountId);
			}
		}
		return null;
	}
	public WxTag getWxTagById(String id){
		if(id==null){
			return null;
		}
		return WxTag.dao.findById(id);
	}
	public String saveWxTag(WxTag bean){
		if(bean!=null && bean.save()){
			return "添加成功";
		}else{
			return "添加失败";
		}
	}
	public String modifyWxTag(WxTag bean){
		String result = null;
		if(bean!=null && bean.get("BoxTypeID")!=null){
			WxTag exists = WxTag.dao.findById(bean.get(WxTag.id));
			if(exists!=null){
				exists.set(WxTag.name, bean.get(WxTag.name));
				if(exists.update()){
					result = "修改成功";
				}else{
					result = "修改失败";
				}
			}
		}else{
			result = IN_PRARM_ERROR;
		}
		return result;
	}
	
	
	
	
	
	public static WxFansService getInstance(){
		if(instance==null){
			synchronized (WxFansService.class) {
				if(instance==null)
					instance = new WxFansService();
			}
		}
		return instance;
	}
	private static WxFansService instance = null;
	private WxFansService(){}
}
