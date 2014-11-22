package com.dragon.apps.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAdmin;

public class RoleUtils {
	public static String role_super_admin = "superadmin";
	public static String role_admin = "admin";
	/**
	 * 判断当前是否是超级管理员角色
	 * @return
	 */
	public static boolean isCurrentSuperAdmin(){
		Subject subject = SecurityUtils.getSubject();
		if(subject!=null && subject.hasRole(role_super_admin)){
			return true;
		}
		return false;
	}
	/**
	 * 获取当前用户对象
	 * @return
	 */
	public static WxAdmin gerCurUser(){
		Subject subject = SecurityUtils.getSubject();
		if(subject.getPrincipal()==null){
			throw new  AuthenticationException();
		}
		WxAdmin admin=(WxAdmin) subject.getPrincipal();
		return admin;
	}
	/**
	 * 获取当前关联的微信id
	 * @return
	 */
	public static Long gerCurAccountId(){
		WxAdmin admin = gerCurUser();
		if(admin!=null){
			return admin.getWxAccountId();
		}
		return null;
	}
	/**
	 * 获取当前关联的微信帐号
	 * @return
	 */
	public static WxAccount getCurAccount(){
		Long accountId = gerCurAccountId();
		if(accountId!=null){
			return WxAccount.dao.findById(accountId);
		}
		return null;
	}
	/**
	 * 获取当前关联的微信的原始id.
	 * @return
	 */
	public static String getCurAccountOriginalId(){
		WxAccount account = getCurAccount();
		if(account!=null){
			return account.getOriginalid();
		}
		return null;
	}
}
