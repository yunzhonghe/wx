package com.dragon.apps.web.module.wxmenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.dragon.adapter.menu.MenuService;
import com.dragon.apps.model.WxMenuModel;
import com.dragon.apps.utils.RoleUtils;

public class WxMenuService implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 防止同时多个同一账号的菜单初始持久化.
     */
    public static ConcurrentMap<Long,Boolean> saveMenuMap = new ConcurrentHashMap<Long,Boolean>();
    private static int maxTryTimes = 2;
    
    public WxMenu getWxMenu(){
    	Long accountId = RoleUtils.gerCurAccountId();
    	WxMenu wxMenu = getWxMenu(accountId);
    	if(wxMenu==null){
    		wxMenu = MenuService.getInstance().getMenu();
    		if(wxMenu!=null && wxMenu.getWxMenuModels()!=null && wxMenu.getWxMenuModels().size()>0){
    			boolean saveResult = saveMenuToDb(wxMenu);
    			if(wxMenu.getWxMenuModels().get(0).getLong(WxMenuModel.accountId)!=accountId){
    				//后台数据不是当前人员的
    				wxMenu = null;
    			}
    			if(!saveResult && wxMenu!=null){//碰上别的线程在保存，需要等待获取菜单的id
    				wxMenu = tryGetWxMenu(accountId);
    			}
    		}
    	}
    	return wxMenu;
    }
    /**
     * 数据库获取对应账号的菜单信息
     * @param accountId
     * @return
     */
    public WxMenu getWxMenu(Long accountId){
    	WxMenu wxMenu = null;
    	if(accountId!=null){
    		List<WxMenuModel> wxMenuModels = WxMenuModel.dao.getAllMenus(accountId);
    		if(wxMenuModels!=null && wxMenuModels.size()>0){
    			wxMenu = new WxMenu();
    			wxMenu.setWxMenuModels(new ArrayList<WxMenuModel>());
    			for(int i=0;i<wxMenuModels.size();i++){
    				WxMenuModel wxMenuModel = wxMenuModels.get(i);
    				Long parentId = wxMenuModel.getLong(WxMenuModel.parentid);
    				if(parentId==null){//super
    					wxMenu.getWxMenuModels().add(wxMenuModel);
    				}else{//sub
    					WxMenuModel superModel = getSuperWxMenuModel(wxMenuModels, parentId);
    					if(superModel!=null){
    						if(superModel.getSubMenuModels()==null){
    							superModel.setSubMenuModels(new ArrayList<WxMenuModel>());
    						}
    						superModel.getSubMenuModels().add(wxMenuModel);
    					}
    				}
    			}
    		}
    	}
    	return wxMenu;
    }
    private WxMenu tryGetWxMenu(Long accountId){
    	int i=0;
    	while(i++ < maxTryTimes){
    		try{
    			Thread.sleep(60);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		WxMenu wxMenu = getWxMenu(accountId);
    		if(wxMenu!=null){
    			return wxMenu;
    		}
    	}
    	return null;
    }
    private WxMenuModel getSuperWxMenuModel(List<WxMenuModel> wxMenuModels,Long parentId){
    	for(WxMenuModel wxMenuModel : wxMenuModels){
    		if(wxMenuModel.getLong(WxMenuModel.id)==parentId){
    			return wxMenuModel;
    		}
    	}
    	return null;
    }
    private boolean saveMenuToDb(final WxMenu wxMenu){
    	if(wxMenu!=null && wxMenu.getWxMenuModels()!=null && wxMenu.getWxMenuModels().size()>0){
    		final Long account_id = wxMenu.getWxMenuModels().get(0).getLong(WxMenuModel.accountId);
    		Boolean saveIng = saveMenuMap.get(account_id);
    		if(saveIng==null || !saveIng){
    			saveMenuMap.put(account_id, true);
    		}else{//正在初始化
    			return false;
    		}
			try{
				for(WxMenuModel oneLevelMenu : wxMenu.getWxMenuModels()){
					if(oneLevelMenu.save()){
						List<WxMenuModel> twoLevelMenus = oneLevelMenu.getSubMenuModels();
						if(twoLevelMenus!=null && twoLevelMenus.size()>0){
							Long parentId = oneLevelMenu.getLong(WxMenuModel.id);
							for(WxMenuModel twoLevelMenu : twoLevelMenus){
								twoLevelMenu.set(WxMenuModel.parentid, parentId);
								twoLevelMenu.save();
							}
						}
					}
				}
			}finally{
				saveIng = saveMenuMap.get(account_id);
				if(saveIng!=null && saveIng){
					saveMenuMap.put(account_id, false);
				}
			}
    	}
    	return true;
    }
    
    public static WxMenuService getInstance(){
		if(instance==null){
			synchronized (WxMenuService.class) {
				if(instance==null)
					instance = new WxMenuService();
			}
		}
		return instance;
	}
	private static WxMenuService instance = null;
	private WxMenuService(){}
}
