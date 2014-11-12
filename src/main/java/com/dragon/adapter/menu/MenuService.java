package com.dragon.adapter.menu;

import com.dragon.apps.service.MenuHandleService;
/**
 * 菜单维护
 * 支持创建、查询、删除菜单，没有修改子菜单的功能
 * @author LiuJian
 */
public class MenuService {
	private MenuHandleService service = null;
	
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
	 * @param menuModel 菜单总对象,包含所有一级菜单及子菜单
	 * @return
	 */
	public boolean createMenu(Object menuModel){
		boolean result = true;
		//FIXME 1, does service.createMenu should return error?
		service.createMenu(MenuAdapter.getRequestByModel(menuModel));
		return result;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
	 * @return
	 */
	public Object getMenu(){
		Object menuModel = null;
		menuModel = MenuAdapter.getModelByResponse(service.getMenu());
		//if return null means getFailed.
		return menuModel;
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
		//FIXME need ApiConfig.
//		service = new MenuHandleService();
	}
}
