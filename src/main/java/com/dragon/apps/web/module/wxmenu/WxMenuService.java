package com.dragon.apps.web.module.wxmenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.dragon.adapter.menu.MenuService;
import com.dragon.apps.model.WxMenuModel;
import com.dragon.apps.utils.RoleUtils;
import com.dragon.apps.utils.StrUtils;
import com.dragon.apps.web.module.base.IdValueBean;
import com.dragon.spider.api.entity.MenuType;
import com.dragon.spider.message.req.ReqType;

public class WxMenuService implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 防止同时多个同一账号的菜单初始持久化.
     */
    public static ConcurrentMap<Long,Boolean> saveMenuMap = new ConcurrentHashMap<Long,Boolean>();
    private static int maxTryTimes = 2;
    
    public WxMenu getWxMenu(){
    	//for test:
//    	List<WxMenuModel> oneMenu = new ArrayList<WxMenuModel>();
//    	WxMenuModel model = new WxMenuModel();
//    	model.put("id", 1);
//    	model.put("parentid", null);
//    	model.put("name", "一级菜单一");
//    	WxMenuModel sub = new WxMenuModel();
//    	sub.put("id", 11);
//    	sub.put("parentid", 1);
//    	sub.put("type", "view");
//    	sub.put("name", "二级菜单一");
//    	sub.put("url", "www.baidu.com");
//    	List<WxMenuModel> twoMenu = new ArrayList<WxMenuModel>();
//    	twoMenu.add(sub);
//    	model.setSubMenuModels(twoMenu);
//    	oneMenu.add(model);
//    	
//    	
//    	model = new WxMenuModel();
//    	model.put("id", 2);
//    	model.put("parentid", null);
//    	model.put("type", "click");
//    	model.put("name", "一级菜单二");
//    	model.put("key", "a");
//    	model.put("key_rsp_type", 1);
//    	model.put("key_rsp_content", "我很好那么你呢？");
//    	oneMenu.add(model);
//    	
//    	WxMenu wxMenu = new WxMenu();
//    	wxMenu.setWxMenuModels(oneMenu);
    	
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
    public List<IdValueBean> getKeyRspTypeList(){
    	List<IdValueBean> ls = new ArrayList<IdValueBean>();
    	ls.add(new IdValueBean(ReqType.TEXTID, ReqType.TEXT_NAME));
    	//FIXME add others, 如素材
    	return ls;
    }
    //更新菜单设置
    public String updateWxMenus(List<WxMenuModel> wxMenuModels){
    	String checkName = checkWxMenuModels(wxMenuModels);
    	if(checkName!=null){
    		return "菜单设置失败：<br/>"+checkName;
    	}
    	Long accountId = RoleUtils.gerCurAccountId();
    	WxMenu wxMenu = getWxMenu(accountId);
    	String result = null;
    	if(wxMenu==null || wxMenu.getWxMenuModels()==null){//尚无菜单
    		result = createMenu(wxMenuModels, accountId);
    	}else{//已经有菜单，需要删除原有菜单，再新增现有菜单
    		if(MenuService.getInstance().deleteMenu()){
    			WxMenuModel.dao.deleteAllMenus(accountId);
    			result = createMenu(wxMenuModels, accountId);
    			if(result==null){
    				result = "菜单删除成功";
    			}
    		}else{
    			result = "菜单更新失败";
    		}
    	}
    	return result;
    }
    private String createMenu(List<WxMenuModel> wxMenuModels,Long accountId){
    	String result = null;
    	if(wxMenuModels!=null && wxMenuModels.size()>0){
    		WxMenu wxMenu = new WxMenu();
			//遍历一级菜单
			for(WxMenuModel wxMenuModel : wxMenuModels){
				Long parentId = wxMenuModel.getLong(WxMenuModel.parentid);
				if(parentId==null){
					if(wxMenu.getWxMenuModels()==null){
						wxMenu.setWxMenuModels(new ArrayList<WxMenuModel>());
					}
					wxMenuModel.set(WxMenuModel.id, null);//重置id
					wxMenuModel.set(WxMenuModel.accountId, accountId);
					wxMenuModel.save();
					wxMenu.getWxMenuModels().add(wxMenuModel);
				}else{
					continue;
				}
			}
			//遍历二级菜单
			for(WxMenuModel wxMenuModel : wxMenuModels){
				Long parentId = wxMenuModel.getLong(WxMenuModel.parentid);
				if(parentId!=null){
					WxMenuModel sup = getParentMenuModelFromFrontEnd(wxMenu.getWxMenuModels(),wxMenuModel);
					if(sup!=null){
						if(sup.getSubMenuModels()==null){
							sup.setSubMenuModels(new ArrayList<WxMenuModel>());
						}
						sup.getSubMenuModels().add(wxMenuModel);
						wxMenuModel.set(WxMenuModel.id, null);//重置id
						wxMenuModel.set(WxMenuModel.parentid, sup.getLong(WxMenuModel.id));
						wxMenuModel.set(WxMenuModel.accountId, accountId);
						wxMenuModel.save();
					}
				}else{
					continue;
				}
			}
			if(MenuService.getInstance().createMenu(wxMenu)){
				result = "菜单创建成功";
			}else{
				result = "菜单创建失败";
			}
		}
    	return result;
    }
    private WxMenuModel getParentMenuModelFromFrontEnd(List<WxMenuModel> wxMenuModels,WxMenuModel subWxMenuModel){
    	for(WxMenuModel sup:wxMenuModels){
    		if(sup.get_tempid()==subWxMenuModel.getLong(WxMenuModel.parentid)){
    			return sup;
    		}
    	}
    	return null;
    }
    //检查菜单名称的长度
    private String checkWxMenuModels(List<WxMenuModel> wxMenuModels){
    	StringBuilder sb = new StringBuilder();
    	if(wxMenuModels!=null && wxMenuModels.size()>0){
    		try{
				for(WxMenuModel wxMenuModel : wxMenuModels){
					String name = wxMenuModel.getStr(WxMenuModel.name);
					int nameLength = name.getBytes("GBK").length;
					Long parentId = wxMenuModel.getLong(WxMenuModel.parentid);
					if(parentId==null){//一级菜单
						if(nameLength>8){//4个汉字长度
							sb.append("一级菜单["+name+"]名称超过长度限制<br/>");
						}
					}else{//二级菜单
						if(nameLength>14){//7个汉字长度
							sb.append("二级菜单["+name+"]名称超过长度限制<br/>");
						}
					}
					//需要检查id是否为负数(新增)、根据响应类型置空消息内容或者url
					Long id = wxMenuModel.getLong(WxMenuModel.id);
					if(id!=null && id<0){
						wxMenuModel.set(WxMenuModel.id, null);
						wxMenuModel.set_tempid(id);//提供给子菜单查询父菜单使用.
					}
					String type = wxMenuModel.getStr(WxMenuModel.type);
					if(type!=null){//做字段逻辑限定
						if(type.equals(MenuType.CLICK)){
							wxMenuModel.set(WxMenuModel.url, null);
							if(StrUtils.isEmpty(wxMenuModel.getStr(WxMenuModel.key))){
								wxMenuModel.set(WxMenuModel.key, UUID.randomUUID().toString());
							}
						}else if(type.equals(MenuType.VIEW)){
							wxMenuModel.set(WxMenuModel.key, null);
							wxMenuModel.set(WxMenuModel.keyRspType, null);
							wxMenuModel.set(WxMenuModel.keyRspContent, null);
						}
					}
				}
    		}catch(Exception e){
    			sb.append(e.getMessage());
    			e.printStackTrace();
    		}
		}
    	return sb.length()==0?null:sb.toString();
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
