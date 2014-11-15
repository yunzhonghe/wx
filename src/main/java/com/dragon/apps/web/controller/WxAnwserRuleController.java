package com.dragon.apps.web.controller;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.model.WxAnswerRule;
import com.dragon.apps.model.WxMaterial;
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

    public void delete() {
    	boolean isDelete = WxAnswerRule.dao.deleteById(getParaToLong("id"));
        this.redirect("/wx-answer-rule/list");
    }
}
