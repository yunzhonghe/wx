package com.dragon.apps.web.module.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.utils.RoleUtils;
import com.dragon.apps.utils.SessionConfig;
import com.dragon.apps.web.module.base.IdValueBean;
import com.dragon.apps.web.module.wxaccount.WxAccountController;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

@ClearInterceptor
public class LoginController extends Controller{
	
	public void index() {
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated() || subject.isRemembered()){
			this.redirect(WxAccountController.controlerKey);
			return;
		}
		render("user/login.html");
	}
	@Before({ SessionInViewInterceptor.class })
	public void login(){
		String userName=this.getPara("username");
		String password=this.getPara("password");
		boolean rememberMe=this.getParaToBoolean("rememberMe",false);
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password,rememberMe);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (Exception e) {
			this.redirect("/");
			return;
		}
		/**
		 * get all admin-accounts, put into session.
		 */
		if(subject.hasRole(RoleUtils.role_super_admin)){//有超级权限，则支持切换权限
			setSessionAttr(SessionConfig.KEY_SWAP_USERS, getSubAdminUsers());
//			setSessionAttr(SessionConfig.KEY_SUPER_ADMIN,SessionConfig.VALUE_SUPER_ADMIN);
		}
		this.redirect(WxAccountController.controlerKey);
	}
	
	public void logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		this.redirect("/");
	}
	
	private List<IdValueBean> getSubAdminUsers(){
		String sql = "select d."+WxAdmin.ID+",d."+WxAdmin.ADMIN_ID+",c."+WxAccount.NAME
				+" from "+WxAdmin.getTableName()+" d"
				+" join "+WxAccount.tableName+" c on c."+WxAccount.ID+"=d."+WxAdmin.WX_ACCOUNT_ID;
		List<Record> ls = Db.find(sql);
		if(ls!=null && ls.size()>0){
			List<IdValueBean> result = new ArrayList<IdValueBean>();
			for(Record r : ls){
				IdValueBean bean = new IdValueBean();
				bean.setId(r.get(WxAdmin.ID));
				bean.setValue(WxAdmin.ADMIN_ID+"("+WxAccount.NAME+")");
				result.add(bean);
			}
			return result;
		}
		return null;
	}
}
