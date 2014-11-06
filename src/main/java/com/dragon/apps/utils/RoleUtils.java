package com.dragon.apps.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class RoleUtils {
	private static String role_admin = "admin";
	/**
	 * 判断当前是否是超级管理员角色
	 * @return
	 */
	public static boolean isCurrentSuperAdmin(){
		Subject subject = SecurityUtils.getSubject();
		if(subject!=null && subject.hasRole(role_admin)){
			return true;
		}
		return false;
	}
	/**
	 * 获取当前用户的id.
	 * @return
	 */
	public static Long getCurrentUserId(){
		//FIXME
		return 1l;
	}
}
