package com.dragon.apps.web.module.wxmenu;

import com.dragon.apps.web.module.base.BaseController;

public class WxMenuController extends BaseController {
	public static String controlerKey = "/wx_menu";

	public void index() {
		WxMenu wxMenu = getService().getWxMenu();
		setAttr("menu", wxMenu);
		render("menu_modify.html");
	}

	public void modifydo() {
		//FIXME
//		render("menu_modify.html");
		renderText("开发中...");
	}

	private WxMenuService getService() {
		return WxMenuService.getInstance();
	}
}
