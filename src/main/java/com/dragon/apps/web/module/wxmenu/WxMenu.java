package com.dragon.apps.web.module.wxmenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dragon.apps.model.WxMenuModel;

public class WxMenu implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 一级菜单对象
	 */
	private List<WxMenuModel> wxMenuModels;
	/**
	 * 获取一级菜单列表
	 * @return
	 */
	public List<WxMenuModel> getWxMenuModels() {
		return wxMenuModels;
	}
	public void setWxMenuModels(List<WxMenuModel> wxMenuModels) {
		this.wxMenuModels = wxMenuModels;
	}
	/**
	 * 获取所有菜单对象
	 * @return
	 */
	public List<WxMenuModel> getAllWxMenuModels(){
		if(wxMenuModels!=null && wxMenuModels.size()>0){
			List<WxMenuModel> ls = new ArrayList<WxMenuModel>();
			for(WxMenuModel oneLevelMenu : wxMenuModels){
				ls.add(oneLevelMenu);
				List<WxMenuModel> subMenus = oneLevelMenu.getSubMenuModels();
				if(subMenus!=null && subMenus.size()>0){
					for(WxMenuModel twoLevelMenu : subMenus){
						ls.add(twoLevelMenu);
					}
				}
			}
			return ls;
		}
		return null;
	}
}
