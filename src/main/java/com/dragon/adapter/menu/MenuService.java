package com.dragon.adapter.menu;

import java.util.List;

import com.dragon.adapter.NeedFix;
import com.dragon.apps.model.WxMenuModel;
import com.dragon.apps.service.MenuHandleService;
import com.dragon.apps.web.module.wxmenu.WxMenu;
/**
 * 菜单维护
 * 支持创建、查询、删除菜单，没有修改子菜单的功能
 * 目前自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。
 * 一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。
 * @author LiuJian
 */
public class MenuService {
	private MenuHandleService service = null;
	
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
	 * @param menuModel 菜单总对象,包含所有一级菜单及子菜单
	 * @return
	 */
	public boolean createMenu(WxMenu wxMenu){
		boolean result = true;
		//FIXME 1, does service.createMenu should return error?
		service.createMenu(MenuAdapter.getRequestByModel(wxMenu));
		return result;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
	 * @return
	 */
	public WxMenu getMenu(){
		WxMenu wxMenu = MenuAdapter.getModelsByResponse(service.getMenu());
		setAccountIdToWxMenu(wxMenu);
		return wxMenu;
	}
	private void setAccountIdToWxMenu(WxMenu wxMenu){
		if(wxMenu!=null){
			List<WxMenuModel> allMenus = wxMenu.getAllWxMenuModels();
			if(allMenus!=null && allMenus.size()>0){
				Long account_id = NeedFix.getApiAccountId(service);
				for(WxMenuModel wxMenuModel : allMenus){
					wxMenuModel.set(WxMenuModel.accountId, account_id);
				}
			}
		}
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
	 * @return
	 */
	public boolean deleteMenu(){
		boolean result = true;
		//FIXME 1, does service.deleteMenu should return error?
		service.deleteMenu();
		return result;
	}
	
	private static MenuService instance = null;
	public static MenuService getInstance(){
		if(instance==null){
			synchronized (MenuService.class) {
				if(instance==null)
					instance = new MenuService();
			}
		}
		return instance;
	}
	private MenuService(){
		service = new MenuHandleService(NeedFix.getApiConfig());
	}
}
