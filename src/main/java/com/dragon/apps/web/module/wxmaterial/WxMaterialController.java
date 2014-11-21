package com.dragon.apps.web.module.wxmaterial;

import com.dragon.apps.model.WxMaterial;
import com.dragon.apps.web.module.base.BaseController;

import java.util.List;

public class WxMaterialController extends BaseController {
	public static String controlerKey = "/wx_material";

    public void index() {
        forwardAction("/wx-material/list");
    }

    public void list() {
        List<WxMaterial> list = WxMaterial.dao.find("select * from wx_material");
        setAttr("materialList",list);
        render("list.html");
    }
    public void add() {
        render("add.html");
    }
    public void adddo() {
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
    public void subindex() {
//        forwardAction("/wx-material/list");
    }
    public void subadd() {
//        forwardAction("/wx-material/list");
    }
    
    
}
