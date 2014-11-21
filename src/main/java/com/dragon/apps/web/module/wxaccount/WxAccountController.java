package com.dragon.apps.web.module.wxaccount;

import com.dragon.apps.model.WxAccType;
import com.dragon.apps.model.WxAccount;
import com.dragon.apps.utils.PageSet;
import com.dragon.apps.utils.RoleUtils;
import com.dragon.apps.web.module.base.BaseController;

public class WxAccountController extends BaseController {
	public static String controlerKey = "/wx_account";
	
	public void index() {// 默认位置，当作总概况
		if(!RoleUtils.isCurrentSuperAdmin()){//不是超级管理员
			forwardAction(controlerKey+"/subindex");
			return;
		}
		setAttr("accountList", getService().getAllAccountList());
		render("index.html");
	}
	public void list() {// 帐号列表
		PageSet pageSet = getPageSet();
		
		setAttr("pageSet", getService().getAccountList(pageSet));
		render("list.html");
	}
	public void add() {// 添加帐号
		setAttr("accountTypes", WxAccType.dao.getWxAccTypeList());
		render("add.html");
	}
	public void adddo() {// 保存帐号
		WxAccount wxAccount = getModel(WxAccount.class);
		setAttr(OPERATION_RESULT, getService().getAdddoResult(wxAccount));
		render("add.html");
	}

	public void modify() {// 修改帐号
		String sid = getPara();
		WxAccount wxAccount = getService().getWxAccountBySid(sid);
		if(wxAccount==null){
			setAttr(OPERATION_RESULT, WxAccountService.IN_PRARM_ERROR);
		}else{
			setAttr("accountTypes", WxAccType.dao.getWxAccTypeList());
			setAttr("wxAccount", wxAccount);
		}
		render("modify.html");
	}
	public void modifydo() {// 保存修改
		WxAccount wxAccount = getModel(WxAccount.class);
		setAttr(OPERATION_RESULT, getService().getModifydoResult(wxAccount));
		render("modify.html");
	}
	public void removedo() {// 执行删除
		String sid = getPara();
		setAttr(OPERATION_RESULT, getService().getDeletedoResult(sid));
		render("list.html");
	}
	// 子账号操作
	public void subindex() {
		setAttr("wxAccount", getService().getCurrentWxAccount());
		render("subindex.html");
	}
	public void modifyself() {
		setAttr("wxAccount", getService().getCurrentWxAccount());
		setAttr("accountTypes", WxAccType.dao.getWxAccTypeList());
		render("modifyself.html");
	}
	public void modifyselfdo() {
		WxAccount wxAccount = getModel(WxAccount.class);
		setAttr(OPERATION_RESULT, getService().getModifydoResult(wxAccount));
		render("modifyself.html");
	}
	
	private WxAccountService getService(){
		return WxAccountService.getInstance();
	}
}
