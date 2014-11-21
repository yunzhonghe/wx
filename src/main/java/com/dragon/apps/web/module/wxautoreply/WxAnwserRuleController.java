package com.dragon.apps.web.module.wxautoreply;

import com.dragon.apps.model.WxAnswerRule;
import com.dragon.apps.web.module.base.BaseController;

public class WxAnwserRuleController extends BaseController {
	public static String controlerKey = "/wx_answer_rule";

    public void index() {
    	setAttr("list", getService().getKerWordRuleList());
    	render("keyword_list.html");
    }
    public void keywordadd(){
    	setAttr("answerTypes", getService().getAnswerTypeList());
    	render("keyword_add.html");
    }
    public void keywordadddo(){
    	WxAnswerRule war = getModel(WxAnswerRule.class);
    	setAttr(OPERATION_RESULT, getService().saveWxAnswerRule(war));
    	render("keyword_add.html");
    }
	public void keywordmodify() {// 修改
		String id = getPara();
		setAttr("wxAnswerRule", getService().getRuleById(id));
		setAttr("answerTypes", getService().getAnswerTypeList());
    	render("keyword_modify.html");
	}
	public void keywordmodifydo() {// 保存修改
		WxAnswerRule war = getModel(WxAnswerRule.class);
		setAttr(OPERATION_RESULT, getService().saveWxAnswerRule(war));
		render("keyword_modify.html");
	}
	public void keywordremovedo() {// 执行删除
		
	}
    
    public void defaulta(){
    	renderNull();
    }
    public void subscribe(){
    	renderNull();
    }
    
    private WxAnwserRuleService getService(){
    	return WxAnwserRuleService.getInstance();
    }
}
