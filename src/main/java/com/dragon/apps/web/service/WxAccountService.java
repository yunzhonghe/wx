package com.dragon.apps.web.service;

import java.io.Serializable;
import java.util.List;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.utils.PageSet;
import com.jfinal.plugin.activerecord.Db;

public class WxAccountService implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String IN_PRARM_ERROR = "传入参数错误";
	/**
	 * get all account info for index to show.
	 */
	public List<WxAccount> getAllAccountList(){
		return WxAccount.dao.find("select * from " + WxAccount.tableName);
	}
	/**
	 * get page account by query condition and pageSet.
	 */
	public PageSet getAccountList(PageSet pageSet){
		//FIXME may have list-query condition.
		int count = Db.queryLong("select count(1) from "+WxAccount.tableName).intValue();
		pageSet.setTotalSize(count);
		if (count > 0) {
			int startSize = (pageSet.getCurrPage() - 1) * pageSet.getPageSize();
			String sql = "select * from "+WxAccount.tableName+ " order by "+WxAccount.ID+" asc limit " + startSize + "," + pageSet.getPageSize();
			pageSet.setResultList(WxAccount.dao.find(sql));
		}
		return pageSet;
	}
	/**
	 * get accounts whose escrowuser is null, means that accounts have not been related with admin-user.
	 */
	public List<WxAccount> getUnEscrowAccount(){
		return WxAccount.dao.find("select * from " + WxAccount.tableName + " where "+WxAccount.ESCROWUSER + " is null");
	}
	/**
	 * get WxAccount by its account.
	 */
	public WxAccount getWxAccountByAccount(String account){
		if(account==null || "".equals(account)){
			return null;
		}
		return WxAccount.dao.findFirst("select * from "+WxAccount.tableName+" where "+WxAccount.ACCOUNT+"='"+account+"'");
	}
	public WxAccount getWxAccountBySid(String sid){
		if(sid!=null&&!"".equals(sid)){
			return WxAccount.dao.findById(Long.parseLong(sid));
		}
		return null;
	}
	public String getAdddoResult(WxAccount wxAccount){
		String result = null;
		if(wxAccount!=null){
			WxAccount exists = getWxAccountByAccount(wxAccount.getAccount());
			if(exists!=null){
				result = "【"+wxAccount.getAccount()+"】对应微信帐号已存在！";
			}else{
				if(wxAccount.save()){
					result = "添加成功";
				}else{
					result = "添加失败";
				}
			}
		}else{
			result = IN_PRARM_ERROR;
		}
		return result;
	}
	public String getModifydoResult(WxAccount wxAccount){
		String result = null;
		if(wxAccount!=null && wxAccount.getId()!=null){
			WxAccount exists = WxAccount.dao.findById(wxAccount.getId());
			if(exists!=null){
				exists.setPassword(wxAccount.getPassword());
				exists.setToken(wxAccount.getToken());
				//FIXME may need to update other columns.
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
	public String getDeletedoResult(String sid){
		String result = null;
		if(sid!=null&&!"".equals(sid)){
			WxAccount wxAccount = WxAccount.dao.findById(Long.parseLong(sid));
			if(wxAccount!=null){
				if(wxAccount.delete()){
					result = "删除成功";
				}else{
					result = "删除失败";
				}
			}
		}else{
			result = IN_PRARM_ERROR;
		}
		return result;
	}
	
	public static WxAccountService getInstance(){
		if(instance==null){
			synchronized (WxAccountService.class) {
				if(instance==null)
					instance = new WxAccountService();
			}
		}
		return instance;
	}
	private static WxAccountService instance = null;
	private WxAccountService(){}
}
