package com.dragon.apps.web.module.wxaccount;

import java.io.Serializable;
import java.util.List;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.utils.PageSet;
import com.dragon.apps.utils.RoleUtils;
import com.dragon.apps.web.module.wxadmin.WxAdminService;
import com.jfinal.plugin.activerecord.Db;

public class WxAccountService implements Serializable{
	private static final long serialVersionUID = 1L;
	private WxAdminService adminService = new WxAdminService();
	public static final String IN_PRARM_ERROR = "传入参数错误";
	/**
	 * get all account info for index to show.
	 */
	public List<WxAccount> getAllAccountList(){
		List<WxAccount> ls = WxAccount.dao.find("select * from " + WxAccount.tableName);
		setWxAdminForWxAccountList(ls);
		return ls;
	}
	/**
	 * get page account by query condition and pageSet.
	 */
	public PageSet getAccountList(PageSet pageSet){
		int count = Db.queryLong("select count(1) from "+WxAccount.tableName).intValue();
		pageSet.setTotalSize(count);
		if (count > 0) {
			int startSize = (pageSet.getCurrPage() - 1) * pageSet.getPageSize();
			String sql = "select * from "+WxAccount.tableName+ " order by "+WxAccount.ID+" asc limit " + startSize + "," + pageSet.getPageSize();
			List<WxAccount> ls = WxAccount.dao.find(sql);
			setWxAdminForWxAccountList(ls);
			pageSet.setResultList(ls);
		}
		return pageSet;
	}
//	/**
//	 * get accounts whose escrowuser is null, means that accounts have not been related with admin-user.
//	 */
//	public List<WxAccount> getUnEscrowAccount(){
//		return getUnEscrowAccount(null);
//	}
//	public List<WxAccount> getUnEscrowAccount(String input){
//		String sql = "select * from " + WxAccount.tableName + " where "+WxAccount.ESCROWUSER + " is null";
//		if(StrUtils.isNotEmpty(input)){
//			input = StrUtils.checkRemoveQuoteMark(input);
//			sql += " and ("+WxAccount.ACCOUNT+" like '"+input+"%' or "+WxAccount.NAME+" like '"+input+"%')";
//		}
//		//sql += " limit 0,20";
//		return WxAccount.dao.find(sql);
//	}
//	/**
//	 * get accounts`s json-array string, whose escrowuser is null.
//	 */
//	public String getUnEscrowAccountJson(){
//		return getUnEscrowAccountJson(null);
//	}
//	public String getUnEscrowAccountJson(String input){
//		List<WxAccount> ls = getUnEscrowAccount(input);
//		StringBuilder json = new StringBuilder();
//		if(ls!=null && ls.size()>0){
//			//[{"id":"1","name":"amao"},{"id":"2","name":"amao"}]
//			json.append("[");
//			for(int i=0;i<ls.size();i++){
//				WxAccount wa = ls.get(i);
//				if(i>0){
//					json.append(",");
//				}
//				json.append("{");
//				json.append("\"id\":\""+wa.getId()+"\",");
//				json.append("\"name\":\""+wa.getName()+"\"");
//				json.append("}");
//			}
//			json.append("]");
//		}
//		return json.toString();
//	}
	
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
				if(wxAccount.getTypeid()!=null){
					exists.setTypeid(wxAccount.getTypeid());
				}
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
	/**
	 * 获取当前账户对应的微信帐号
	 * @return
	 */
	public WxAccount getCurrentWxAccount(){
		return RoleUtils.getCurAccount();
	}
	
	private List<WxAccount> setWxAdminForWxAccountList(List<WxAccount> ls){
		if(ls!=null && ls.size()>0){
			for(WxAccount wxAccount : ls){
				setWxAdminForWxAccount(wxAccount);
			}
		}
		return ls;
	}
	private void setWxAdminForWxAccount(WxAccount wxAccount){
		wxAccount.put("wxadmin", adminService.getWxAdminByWxAccountId(wxAccount.getId()));
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
