package com.dragon.apps.web.module.wxmenu;

import java.util.ArrayList;
import java.util.List;

import com.dragon.apps.model.WxMenuModel;
import com.dragon.apps.web.module.base.BaseController;

public class WxMenuController extends BaseController {
	public static String controlerKey = "/wx_menu";

	public void index() {
		WxMenu wxMenu = getService().getWxMenu();
		setAttr("menu", wxMenu);
		setAttr("rspTypes", getService().getKeyRspTypeList());
		render("menu_modify.html");
	}

	public void modifydo() {
		String wxMenuModelSize = getPara("wxMenuModelSize");
		int iwxMenuModelSize = Integer.parseInt(wxMenuModelSize);
		List<WxMenuModel> wxMenuModels = null;
		if(iwxMenuModelSize>0){
			wxMenuModels = new ArrayList<WxMenuModel>();
			for(int i=0;i<iwxMenuModelSize;i++){
				wxMenuModels.add(getModel(WxMenuModel.class, "wxMenuModel["+i+"]"));
			}
		}
		setAttr(OPERATION_RESULT, getService().updateWxMenus(wxMenuModels));
		render("menu_modify.html");
	}

	private WxMenuService getService() {
		return WxMenuService.getInstance();
	}
}
