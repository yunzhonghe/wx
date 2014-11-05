package com.dragon.apps.web.controller;

import com.dragon.apps.model.WxAccType;
import com.dragon.apps.model.WxAccount;
import com.dragon.apps.utils.PageSet;
import com.dragon.apps.web.service.WxAccountService;
import com.jfinal.core.Controller;

public class WxAccountController extends Controller {
	public static String controlerKey = "/wxaccount";
	private String operationResult = "_opresult";
	

	public void index() {// 默认位置，当作总概况
		/**
		 * FIXME: 
		 * if(is sub account){ 
		 * 		forwardAction(controlerKey+"/subindex");
		 * }
		 * return; 
		 */
		// FIXME get WxAccStat for each WxAccount.
		setAttr("accountList", getService().getAllAccountList());
		render("index.html");
	}
	public void list() {// 帐号列表
		PageSet pageSet = getPageSet();
		//FIXME may have list condition.
		
		setAttr("pageSet", getService().getAccountList(pageSet));
		render("list.html");
	}
	public void add() {// 添加帐号
		setAttr("accountTypes", WxAccType.dao.getWxAccTypeList());
		render("add.html");
	}
	public void adddo() {// 保存帐号
		WxAccount wxAccount = getModel(WxAccount.class);
		setAttr(operationResult, getService().getAdddoResult(wxAccount));
		render("add.html");
	}

	public void modify() {// 修改帐号
		String sid = getPara();
		WxAccount wxAccount = getService().getWxAccountBySid(sid);
		if(wxAccount==null){
			setAttr(operationResult, WxAccountService.IN_PRARM_ERROR);
		}else{
			setAttr("accountTypes", WxAccType.dao.getWxAccTypeList());
			setAttr("wxAccount", wxAccount);
		}
		render("modify.html");
	}
	public void modifydo() {// 保存修改
		WxAccount wxAccount = getModel(WxAccount.class);
		setAttr(operationResult, getService().getModifydoResult(wxAccount));
		render("modify.html");
	}
	public void removedo() {// 执行删除
		String sid = getPara();
		setAttr(operationResult, getService().getDeletedoResult(sid));
		render("list.html");
	}
	// 子账号操作
	public void subindex() {
		renderText("开发中...");
	}
	public void modifyself() {
		renderText("开发中...");
	}
	public void modifyselfdo() {
		renderText("开发中...");
	}
	private PageSet getPageSet() {
		PageSet pageSet = null;
		//FIXME parameters name may be others.
		String page_pageSize = getPara("page_pageSize");
		String page_currPage = getPara("page_currPage");
		if (page_pageSize != null && page_currPage != null) {
			pageSet = new PageSet(Integer.parseInt(page_currPage), Integer.parseInt(page_pageSize));
		} else {
			pageSet = new PageSet();
		}
		return pageSet;
	}
	
	private WxAccountService getService(){
		return WxAccountService.getInstance();
	}
}
