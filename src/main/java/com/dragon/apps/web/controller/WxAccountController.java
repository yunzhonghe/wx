package com.dragon.apps.web.controller;

import java.util.List;

import com.dragon.apps.model.WxAccType;
import com.dragon.apps.model.WxAccount;
import com.dragon.apps.utils.PageSet;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

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
		List<WxAccount> accountList = WxAccount.dao.find("select * from " + WxAccount.tableName);
		// FIXME get WxAccStat for each WxAccount.
		setAttr("accountList", accountList);
		render("index.html");
	}
	public void list() {// 帐号列表
		PageSet pageSet = getPageSet();
		//FIXME may have list condition.
		
		int count = Db.queryLong("select count(1) from "+WxAccount.tableName).intValue();
		pageSet.setTotalSize(count);
		if (count > 0) {
			int startSize = (pageSet.getCurrPage() - 1) * pageSet.getPageSize();
			String sql = "select * from "+WxAccount.tableName+ " order by "+WxAccount.ID+" asc limit " + startSize + "," + pageSet.getPageSize();
			pageSet.setResultList(WxAccount.dao.find(sql));
		}
		
		setAttr("pageSet", pageSet);
		render("list.html");
	}
	public void add() {// 添加帐号
		List<WxAccType> accountTypes = WxAccType.dao.getWxAccTypeList();
		setAttr("accountTypes", accountTypes);
		render("add.html");
	}
	public void adddo() {// 保存帐号
		WxAccount wxAccount = getModel(WxAccount.class);
		WxAccount exists = WxAccount.dao.getWxAccountByAccount(wxAccount.getAccount());
		String msg = null;
		if(exists!=null){
			msg = "【"+wxAccount.getAccount()+"】对应微信帐号已存在！";
//			renderText();
		}else{
			if(wxAccount.save()){
				msg = "添加成功";
//				renderText("添加成功");
			}else{
				msg = "添加失败";
//				renderText("添加失败");
			}
		}
//		renderText(operationResult);
		setAttr(operationResult, msg);
		render("add.html");
	}

	public void modify() {// 修改帐号
		String sid = getPara();
		if(sid!=null&&!"".equals(sid)){
			WxAccount wxAccount = WxAccount.dao.findById(Long.parseLong(sid));
			if(wxAccount!=null){
				List<WxAccType> accountTypes = WxAccType.dao.getWxAccTypeList();
				setAttr("accountTypes", accountTypes);
				setAttr("wxAccount", wxAccount);
				render("modify.html");
				return;
			}
		}
		setAttr(operationResult, "传入参数错误");
		render("modify.html");
	}
	public void modifydo() {// 保存修改
		WxAccount wxAccount = getModel(WxAccount.class);
		String msg = null;
		if(wxAccount.getId()!=null){
			WxAccount exists = WxAccount.dao.findById(wxAccount.getId());
			if(exists!=null){
				exists.setPassword(wxAccount.getPassword());
				exists.setToken(wxAccount.getToken());
				if(exists.update()){
					msg = "修改成功";
//					renderText("修改成功");
				}else{
					msg = "修改失败";
//					renderText("修改失败");
				}
				setAttr(operationResult, msg);
				render("modify.html");
				return;
			}
		}
		setAttr(operationResult, "传入参数错误");
		render("modify.html");
	}
	public void removedo() {// 执行删除
		String sid = getPara();
		String msg = null;
		if(sid!=null&&!"".equals(sid)){
			WxAccount wxAccount = WxAccount.dao.findById(Long.parseLong(sid));
			if(wxAccount!=null){
				if(wxAccount.delete()){
					msg = "删除成功";
//					renderText("删除成功");
				}else{
					msg = "删除失败";
//					renderText("删除失败");
				}
				setAttr(operationResult, msg);
				render("list.html");
				return;
			}
		}
		setAttr(operationResult, "传入参数错误");
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
}
