package com.dragon.apps.web.controller;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.model.WxAnswerRule;
import com.dragon.apps.model.WxMaterial;
import com.dragon.apps.utils.ConstantsUtils;
import com.google.gson.Gson;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/2.
 */
public class WxAnwserRuleController extends Controller {

    public void index() {
        forwardAction("/wx-answer-rule/list");
    }
    
    public void list() {
        List<WxAnswerRule> list = WxAnswerRule.dao.find("select * from wx_answer_rule");
        setAttr("ruleList",list);
        render("list.html");
    }

    public void preAdd() {
        List<WxMaterial> list = WxMaterial.dao.find("select * from wx_material");
        setAttr("materialList", list);
        render("add.html");
    }

    public void add() {
        WxAnswerRule wxAnswerRule = getModel(WxAnswerRule.class);
        if(wxAnswerRule.getAnswerType() == ConstantsUtils.ANSWER_TYPE_TEXT) {
            wxAnswerRule.setMaterialId(null);
            wxAnswerRule.setMaterialPath(null);
        } else {
            wxAnswerRule.setAnwser(null);
            WxMaterial wxMaterial = WxMaterial.dao.findById(wxAnswerRule.getMaterialId());
            wxAnswerRule.setMaterialPath(wxMaterial.getFilePath());
        }
        wxAnswerRule.save();
        redirect("/wx-answer-rule/list");
    }

    public void preUpdate() {
        WxAnswerRule wxAnswerRule = WxAnswerRule.dao.findById(getParaToLong("id"));
        if(wxAnswerRule.getAnswerType() == ConstantsUtils.ANSWER_TYPE_TEXT) {
            wxAnswerRule.setMaterialId(-1L);
        }
        List<WxMaterial> list = WxMaterial.dao.find("select * from wx_material");
        setAttr("wxAnswerRule",wxAnswerRule);
        setAttr("materialList", list);
        render("update.html");
    }

    public void update() {
        WxAnswerRule wxAnswerRule = getModel(WxAnswerRule.class);
        if(wxAnswerRule.getAnswerType() == ConstantsUtils.ANSWER_TYPE_TEXT) {
            wxAnswerRule.setMaterialId(null);
            wxAnswerRule.setMaterialPath(null);
        } else {
            wxAnswerRule.setAnwser(null);
            WxMaterial wxMaterial = WxMaterial.dao.findById(wxAnswerRule.getMaterialId());
            wxAnswerRule.setMaterialPath(wxMaterial.getFilePath());
        }
        wxAnswerRule.update();
        redirect("/wx-answer-rule/list");
    }

    public void delete() {
    	boolean isDelete = WxAnswerRule.dao.deleteById(getParaToLong("id"));
        this.redirect("/wx-answer-rule/list");
    }
}
