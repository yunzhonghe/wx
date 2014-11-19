package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by Administrator on 2014/11/2.
 */
public class WxAdmin extends Model<WxAdmin> {
    private static final long serialVersionUID = 1L;
    public static WxAdmin dao = new WxAdmin();

    public static String getTableName() {
        return "wx_admin";
    }
    public WxAccount getWxAccount(){
    	Long accountId = getWxAccountId();
    	if(accountId!=null){
    		return WxAccount.dao.findById(accountId);
    	}
    	return null;
    }

    public static final String ID = "id";//long,pk
    public static final String ADMIN_ID = "admin_id";//string
    public static final String PASSWORD = "password";//string
    public static final String EMAIL = "email";//string
    public static final String TELEPHONE = "telephone";//string
    public static final String NAME = "name";//string
    public static final String DEPARTMENT_ID = "department_id";//long
    public static final String POST_ID = "post_id";//long
    public static final String WX_ACCOUNT_ID = "wx_account_id";//long,refers to wxaccount.
    public static final String ISSUPER = "issuper";//char(1),1-超级管理员，0普通，default0.

    public Long getId() {
        return getLong(ID);
    }
    public String getAdminId() {
        return getStr(ADMIN_ID);
    }
    public String getPassword() {
        return getStr(PASSWORD);
    }
    public String getEmail() {
        return getStr(EMAIL);
    }
    public String getTelephone() {
        return getStr(TELEPHONE);
    }
    public String getName() {
        return getStr(NAME);
    }
    public Long getDepartmentId() {
        return getLong(DEPARTMENT_ID);
    }
    public Long getPostId() {
        return getLong(POST_ID);
    }
    public Long getWxAccountId() {
        return getLong(WX_ACCOUNT_ID);
    }
    public WxAdmin setAdminId(String adminId) {
        return set(ADMIN_ID, adminId);
    }
    public WxAdmin setPassword(String password) {
        return set(PASSWORD, password);
    }
    public WxAdmin setEmail(String email) {
        return set(EMAIL, email);
    }
    public WxAdmin setTelephone(String telephone) {
        return set(TELEPHONE, telephone);
    }
    public WxAdmin setName(String name) {
        return set(NAME, name);
    }
    public WxAdmin setDepartmentId(Long departmentId) {
        return set(DEPARTMENT_ID, departmentId);
    }
    public WxAdmin setPostId(Long postId) {
        return set(POST_ID, postId);
    }
    public WxAdmin setWxAccountId(Long wxAccountId) {
        return set(WX_ACCOUNT_ID, wxAccountId);
    }
    public WxAdmin setSuper(String issuper){
    	 return set(ISSUPER, issuper);
    }
    public String getSuper(){
    	return getStr(ISSUPER);
    }
}
