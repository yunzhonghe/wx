package com.dragon.apps.web.controller;

import java.util.List;

import com.dragon.apps.model.WxAccount;
import com.jfinal.core.Controller;

public class WxAccountController extends Controller {
	public static String controlerKey = "/wxaccount";

	public void index() {// 默认位置，当作总概况
		/**
		 * FIXME: 
		 * if(is sub account){ 
		 * 		forwardAction(controlerKey+"/subindex");
		 * }
		 * return; 
		 */
		List<WxAccount> accountList = WxAccount.dao.find("select * from " + WxAccount.tableName);
		// FIXME get WxAccStat for each WxAccount.
		setAttr("accountList", accountList);
		render("index.html");
	}

	public void list() {// 帐号列表
		renderText("开发中...");
	}

	public void add() {// 添加帐号
		renderText("开发中...");
	}

	public void adddo() {// 保存帐号
		renderText("开发中...");
	}

	public void modify() {// 修改帐号
		renderText("开发中...");
	}

	public void modifydo() {// 保存修改
		renderText("开发中...");
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

}
