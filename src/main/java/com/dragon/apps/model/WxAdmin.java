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

    public static final String ID = "id";
    public static final String ADMIN_ID = "admin_id";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String TELEPHONE = "telephone";
    public static final String NAME = "name";
    public static final String DEPARTMENT_ID = "department_id";
    public static final String POST_ID = "post_id";
    public static final String WX_ACCOUNT_ID = "wx_account_id";
    public static final String WX_ACCOUNT_NAME = "wx_account_name";

    public long getId() {
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

    public long getDepartmentId() {
        return getLong(DEPARTMENT_ID);
    }

    public long getPostId() {
        return getLong(POST_ID);
    }

    public Long getWxAccountId() {
        return getLong(WX_ACCOUNT_ID);
    }

    public String getWxAccountName() {
        return getStr(WX_ACCOUNT_NAME);
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

    public WxAdmin setDepartmentId(long departmentId) {
        return set(DEPARTMENT_ID, departmentId);
    }

    public WxAdmin setPostId(long postId) {
        return set(POST_ID, postId);
    }

    public WxAdmin setWxAccountId(long wxAccountId) {
        return set(WX_ACCOUNT_ID, wxAccountId);
    }

    public WxAdmin setWxAccountName(String wxAccountName) {
        return set(WX_ACCOUNT_NAME, wxAccountName);
    }

}
