package com.dragon.apps.web.module.user;

import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.model.WxTag;
import com.dragon.apps.utils.RoleUtils;
import com.dragon.apps.web.module.base.BaseController;

public class UserController extends BaseController{
	public static String controlerKey = "/user";
	
	public void index() {//个人资料
		WxAdmin admin = RoleUtils.gerCurUser();
		admin = WxAdmin.dao.findById(admin.getId());
		setAttr("admin", admin);
		render("modify.html");
	}
	public void modifydo(){//修改资料
		WxAdmin admin = getModel(WxAdmin.class);
		String result = null;
		if(admin!=null && admin.getId()!=null){
			WxAdmin exists = WxAdmin.dao.findById(admin.getId());
			if(exists!=null){
				exists.set(WxAdmin.NAME, admin.get(WxAdmin.NAME));
				exists.set(WxAdmin.EMAIL, admin.get(WxAdmin.EMAIL));
				exists.set(WxAdmin.TELEPHONE, admin.get(WxAdmin.TELEPHONE));
				if(exists.update()){
					result = "修改成功";
				}else{
					result = "修改失败";
				}
			}
		}else{
			result = "传入参数错误";
		}
		setAttr(OPERATION_RESULT, result);
		render("modify.html");
	}
	public void modifypassword(){
		WxAdmin admin = RoleUtils.gerCurUser();
		admin = WxAdmin.dao.findById(admin.getId());
		setAttr("admin", admin);
		render("modify_password.html");
	}
	public void modifypassworddo(){
		WxAdmin admin = getModel(WxAdmin.class);
		String oldPassword = getPara("password");
		String result = null;
		if(admin!=null && admin.getId()!=null && oldPassword!=null){
			WxAdmin exists = WxAdmin.dao.findById(admin.getId());
			if(exists!=null){
				if(exists.getPassword().equals(oldPassword)){
					exists.set(WxAdmin.PASSWORD, admin.get(WxAdmin.PASSWORD));
					if(exists.update()){
						result = "修改成功";
					}else{
						result = "修改失败";
					}
				}else{
					result = "修改失败，原密码错误";
				}
			}
		}else{
			result = "传入参数错误";
		}
		setAttr(OPERATION_RESULT, result);
		render("modify_password.html");
	}
}
