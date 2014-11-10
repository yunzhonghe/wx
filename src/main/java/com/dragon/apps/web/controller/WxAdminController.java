package com.dragon.apps.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAdmin;
import com.google.gson.Gson;
import com.jfinal.core.Controller;

/**
 * Created by Administrator on 2014/11/2.
 */
public class WxAdminController extends Controller {

    public void index() {
        forwardAction("/wx-admin/list");
    }
    
    public void list() {
        List<WxAdmin> list = WxAdmin.dao.find("select * from wx_admin");
        setAttr("adminList",list);
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
        redirect("/wx-admin/list");
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
        redirect("/wx-admin/list");
    }

    public void delete() {
    	Map<String,Object> map=new HashMap<String,Object>();
        map.put("isOK",false);
        System.out.println(getParaToLong("id"));
    	WxAdmin wxAdmin = WxAdmin.dao.findById(getParaToLong("id"));
        wxAdmin.delete();
        map.put("isOK",true);
        this.renderJson(map);   
    }
}
