package com.dragon.adapter.menu;

import java.util.ArrayList;
import java.util.List;

import com.dragon.apps.model.WxMenuModel;
import com.dragon.apps.web.module.wxmenu.WxMenu;
import com.dragon.spider.api.entity.MenuButton;
import com.dragon.spider.api.request.MenuRequest;
import com.dragon.spider.api.response.GetMenuResponse;

public class MenuAdapter {
	public static MenuRequest getRequestByModel(WxMenu wxMenu){
		MenuRequest request = null;
		if(wxMenu!=null){
			request = new MenuRequest();
			if(wxMenu.getWxMenuModels()!=null && wxMenu.getWxMenuModels().size()>0){
				request.setButton(new ArrayList<MenuButton>());
				for(WxMenuModel sup:wxMenu.getWxMenuModels()){
					MenuButton button = new MenuButton();
					setMenuButtonByWxMenuMode(button, sup);
					request.getButton().add(button);
					if(sup.getSubMenuModels()!=null && sup.getSubMenuModels().size()>0){
						button.setSub_button(new ArrayList<MenuButton>());
						for(WxMenuModel sub:sup.getSubMenuModels()){
							MenuButton sub_button = new MenuButton();
							setMenuButtonByWxMenuMode(sub_button, sub);
							button.getSub_button().add(sub_button);
						}
					}
				}
			}
		}
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
	private static void setMenuButtonByWxMenuMode(MenuButton button,WxMenuModel wxMenuModel){
		wxMenuModel.set(WxMenuModel.type, button.getType());
		wxMenuModel.set(WxMenuModel.name, button.getName());
		wxMenuModel.set(WxMenuModel.key, button.getKey());
		wxMenuModel.set(WxMenuModel.url, button.getUrl());
	}
}
