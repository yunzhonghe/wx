package com.dragon.apps.web.module.wxautoreply;


import com.dragon.apps.model.WxAnswerRule;
import com.dragon.apps.utils.OperationUtil;
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
		setAttr("wxAnswerRule", getService().getRuleById(id,WxAnswerRule.RULE_TYPE_KEYWORD));
		setAttr("answerTypes", getService().getAnswerTypeList());
    	render("keyword_modify.html");
	}
	public void keywordmodifydo() {// 保存修改
		WxAnswerRule war = getModel(WxAnswerRule.class);
		setAttr(OPERATION_RESULT, getService().modifyWxAnswerRule(war,WxAnswerRule.RULE_TYPE_KEYWORD));
		render("keyword_modify.html");
	}
	public void keywordremovedo() {// 执行删除
		String sid = getPara();
		setAttr(OPERATION_RESULT, getService().deleteWxAnswerRule(sid));
		render("keyword_list.html");
	}
    
    public void defaulta(){
		defaultaOrSubscripe(WxAnswerRule.RULE_TYPE_DEFAULTA);
    }
    public void subscribe(){
    	defaultaOrSubscripe(WxAnswerRule.RULE_TYPE_SUBSCRIBE);
    }
    private void defaultaOrSubscripe(String ruleType){
    	String identify = getPara();//modify,modifydo
		OperationUtil op = OperationUtil.getOperationUtilByIdentify(identify,OperationUtil.MODIFY);
		
		WxAnswerRule bean = null;
		String nextAction = op.getIdentify();
		String operationMsg = null;
		switch(op){
		case MODIFY:
			bean = getService().getRuleById(getPara("id"), ruleType);
			nextAction = OperationUtil.MODIFYDO.getIdentify();
			setAttr("answerTypes", getService().getAnswerTypeList());
			break;
		case MODIFYDO:
			bean = getModel(WxAnswerRule.class);
			operationMsg = getService().modifyWxAnswerRule(bean,ruleType);
			bean = null;
			break;
		default:
			break;
		}
		setAttr(BEAN_ENTITY, bean);
		setAttr("nextAction", nextAction);
		setAttr(OPERATION_RESULT, operationMsg);
		if(ruleType.equals(WxAnswerRule.RULE_TYPE_DEFAULTA)){
			render("defaulta_modify.html");
		}else if(ruleType.equals(WxAnswerRule.RULE_TYPE_SUBSCRIBE)){
			render("subscribe_modify.html");
		}else{
			System.out.println("内部类型错误！");
			renderText("内部类型错误！");
		}
    }
    
    private WxAnwserRuleService getService(){
    	return WxAnwserRuleService.getInstance();
    }
}
