package com.dragon.apps.web.controller;

import com.dragon.apps.model.WxMaterial;
import com.dragon.apps.service.WxMaterialService;
import com.jfinal.core.Controller;

import java.util.List;

/**
 * Created by Administrator on 2014/11/2.
 */
public class WxMaterialController extends Controller {

    public void index() {
        forwardAction("/wx-material/list");
    }

    public void list() {
        List<WxMaterial> list = WxMaterial.dao.find("select * from wx_material");
        setAttr("materialList",list);
        render("list.html");
    }

    public void preAdd() {
        render("add.html");
    }

    public void add() {
        WxMaterialService wxMaterialService = new WxMaterialService();
        WxMaterial wxMaterial = wxMaterialService.uploadMaterialFile(this.getRequest());
        if(wxMaterial != null) {
            wxMaterial.save();
        }
        redirect("/wx-material/list");
    }

    public void delete() {
        boolean isDelete = WxMaterial.dao.deleteById(getParaToLong("id"));
        this.redirect("/wx-material/list");
    }
}
