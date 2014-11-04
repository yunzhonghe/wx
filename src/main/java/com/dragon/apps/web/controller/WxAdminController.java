package com.dragon.apps.web.controller;

import com.dragon.apps.model.WxAdmin;
import com.jfinal.core.Controller;

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
        setAttr("adminList", list);
        render("list.html");
    }

    public void get() {
        WxAdmin wxAdmin = WxAdmin.dao.findById(getParaToLong());
        setAttr("admin", wxAdmin);
        render("/wx-admin/update");
    }

    public void add() {
        WxAdmin wxAdmin = getModel(WxAdmin.class);
        wxAdmin.save();
        render("/wx-admin/list");
    }

    public void update() {
        WxAdmin wxAdmin = getModel(WxAdmin.class);
        wxAdmin.update();
        render("/wx-admin/list");
    }

    public void delete() {
        WxAdmin wxAdmin = WxAdmin.dao.findById(getParaToLong());
        wxAdmin.delete();
        render("/wx-admin/list");
    }
}
