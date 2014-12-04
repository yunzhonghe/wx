package com.dragon.apps.web.module.wxadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.web.module.base.BaseController;
import com.google.gson.Gson;

public class WxAdminController extends BaseController {
	public static String controlerKey = "/wx_admin";
    public void index() {
        forwardAction("/wx_admin/list");
    }
    
    public void list() {
        List<WxAdmin> list = WxAdmin.dao.find("select * from wx_admin");
        setAttr("adminList",list);
//        String sql = "select a.id, a.name from wx_account a left join wx_admin d on d.wx_account_id=a.id where d.wx_account_id is null";
//        List<WxAccount> accountList = WxAccount.dao.find(sql);
        List<WxAccount> accountList = WxAccount.dao.find("select id, name from wx_account");
        setAttr("accountList", new Gson().toJson(accountList));
        render("list.html");
    }

    public void preAdd() {
        List<WxAccount> list = WxAccount.dao.find("select id, name from wx_account");
        setAttr("accountList",list);
        render("add.html");
    }

    public void add() {
        WxAdmin wxAdmin = getModel(WxAdmin.class);
        wxAdmin.save();
        redirect("/wx_admin/list");
    }

    public void preUpdate() {
        WxAdmin wxAdmin = WxAdmin.dao.findById(getParaToLong("id"));
        List<WxAccount> list = WxAccount.dao.find("select id, name from wx_account");
        setAttr("accountList",list);
        setAttr("admin", wxAdmin);
        render("update.html");
    }

    public void update() {
        WxAdmin wxAdmin = getModel(WxAdmin.class);
        wxAdmin.update();
        redirect("/wx_admin/list");
    }

    public void delete() {
    	Map<String,Object> map=new HashMap<String,Object>();
        map.put("isOK",false);
    	WxAdmin wxAdmin = WxAdmin.dao.findById(getParaToLong("id"));
        wxAdmin.delete();
        map.put("isOK",true);
        this.renderJson(map);
    }
}
