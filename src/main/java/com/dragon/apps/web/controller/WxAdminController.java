package com.dragon.apps.web.controller;

import com.dragon.apps.model.WxAdmin;
import com.google.gson.Gson;
import com.jfinal.core.Controller;
import org.eclipse.jetty.util.ajax.JSON;

import java.util.List;

/**
 * Created by Administrator on 2014/11/2.
 */
public class WxAdminController extends Controller {

    public void index() {
        forwardAction("/wx-admin/list");
    }
    public void list() {
        List<WxAdmin> list = WxAdmin.dao.find("select * from wx_admin");
        setAttr("adminList", new Gson().toJson(list));
        render("list.html");
    }

    public void get() {
        WxAdmin wxAdmin = WxAdmin.dao.findById(getParaToLong());
        setAttr("admin", wxAdmin);
    }

    public void add() {
        WxAdmin wxAdmin = getModel(WxAdmin.class, "wxAdmin");
        wxAdmin.save();
    }

    public void preAdd() {
        render("add.html");
    }

    public void update() {
        WxAdmin wxAdmin = getModel(WxAdmin.class);
        wxAdmin.update();
    }

    public void delete() {
        WxAdmin wxAdmin = WxAdmin.dao.findById(getParaToLong("id"));
        wxAdmin.delete();

    }
}
