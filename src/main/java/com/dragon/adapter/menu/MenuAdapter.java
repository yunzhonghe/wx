package com.dragon.adapter.menu;

import java.util.ArrayList;
import java.util.List;

import com.dragon.apps.model.WxMenuModel;
import com.dragon.apps.web.module.wxmenu.WxMenu;
import com.dragon.spider.api.entity.MenuButton;
import com.dragon.spider.api.request.MenuRequest;
import com.dragon.spider.api.response.GetMenuResponse;

public class MenuAdapter {
	public static MenuRequest getRequestByModel(Object menuModel){
		MenuRequest request = null;
		//FIXME
		return request;
	}
	public static WxMenu getModelsByResponse(GetMenuResponse response){
		WxMenu wxMenu = null;
		if(response!=null){
			if(response.getMenu()!=null){
				List<MenuButton> buttons = response.getMenu().getButton();
				if(buttons!=null && buttons.size()>0){
					wxMenu = new WxMenu();
					wxMenu.setWxMenuModels(new ArrayList<WxMenuModel>());
					for(MenuButton button : buttons){
						WxMenuModel wxMenuModel = new WxMenuModel();
						setWxMenuModelByMenuButton(wxMenuModel, button);
						wxMenu.getWxMenuModels().add(wxMenuModel);
						
						List<MenuButton> sub_buttons = button.getSub_button();
						if(sub_buttons!=null && sub_buttons.size()>0){
							wxMenuModel.setSubMenuModels(new ArrayList<WxMenuModel>());
							for(MenuButton sub_button : sub_buttons){
								WxMenuModel sub_wxMenuModel = new WxMenuModel();
								setWxMenuModelByMenuButton(sub_wxMenuModel, sub_button);
								wxMenuModel.getSubMenuModels().add(sub_wxMenuModel);
							}
						}
					}
				}
			}
		}
		return wxMenu;
	}
	private static void setWxMenuModelByMenuButton(WxMenuModel wxMenuModel,MenuButton button){
		wxMenuModel.set(WxMenuModel.type, button.getType());
		wxMenuModel.set(WxMenuModel.name, button.getName());
		wxMenuModel.set(WxMenuModel.key, button.getKey());
		wxMenuModel.set(WxMenuModel.url, button.getUrl());
	}
}
