package com.dragon.core.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.utils.RoleUtils;
import com.dragon.apps.utils.StrUtils;

public class WXRealm extends AuthorizingRealm{

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.fromRealm(getName()).iterator().next();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		if(StrUtils.isNotEmpty(userName)){
			WxAdmin admin = WxAdmin.dao.getWxAdminByUserName(userName);
			Set<String> roles=new HashSet<String>();
			if(admin.getSuper().equals(WxAdmin.SUPERADMIN)){//超级管理员角色
				roles.add(RoleUtils.role_super_admin);
			}else{
				roles.add(RoleUtils.role_admin);//普通管理员角色
			}
			authorizationInfo.setRoles(roles);
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		WxAdmin admin=WxAdmin.dao.getWxAdminByUserName(username);
		if(admin==null  || StringUtils.isEmpty(username)){
			throw new UnknownAccountException();
		}
		if (!StringUtils.equals(password,admin.getPassword())) {
			throw new IncorrectCredentialsException();
		}
		return new SimpleAuthenticationInfo(admin, password, getName());
	}

}
