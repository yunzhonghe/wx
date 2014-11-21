package com.dragon.apps.web.module.wxautoreply;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dragon.apps.model.WxAnswerRule;
import com.dragon.apps.utils.ModelUtils;
import com.dragon.apps.utils.RoleUtils;
import com.dragon.apps.utils.StrUtils;
import com.dragon.apps.web.module.base.IdValueBean;
import com.dragon.spider.message.req.ReqType;

public class WxAnwserRuleService implements Serializable {
	public static final String IN_PRARM_ERROR = "传入参数错误";
    private static final long serialVersionUID = 1L;
    
    public List<WxAnswerRule> getKerWordRuleList(){
    	Long accontId = null;
    	if(!RoleUtils.isCurrentSuperAdmin()){
    		accontId = RoleUtils.gerCurAccountId();
    	}
    	return setAnswerTypeNameToList(getKerWordRuleList(WxAnswerRule.RULE_TYPE_KEYWORD, accontId));
    }
    public List<IdValueBean> getAnswerTypeList(){
    	List<IdValueBean> ls = new ArrayList<IdValueBean>();
    	ls.add(new IdValueBean(ReqType.TEXTID, ReqType.TEXT_NAME));
    	//FIXME add others
    	return ls;
    }
    public String saveWxAnswerRule(WxAnswerRule bean){
		String result = null;
		if(bean!=null){
			Long accontId = null;
	    	if(!RoleUtils.isCurrentSuperAdmin()){
	    		accontId = RoleUtils.gerCurAccountId();
	    	}
	    	bean.set(WxAnswerRule.RULE_TYPE, WxAnswerRule.RULE_TYPE_KEYWORD);
	    	bean.set(WxAnswerRule.ACCOUNT_ID, accontId);
			if(bean.save()){
				return "添加成功";
			}else{
				return "添加失败";
			}
		}else{
			result = IN_PRARM_ERROR;
		}
		return result;
	}
    public String modifyWxAnswerRule(WxAnswerRule bean){
		String result = null;
		if(bean!=null && bean.get(WxAnswerRule.ID)!=null){
			WxAnswerRule exists = WxAnswerRule.dao.findById(bean.get(WxAnswerRule.ID));
			if(exists!=null){
				ModelUtils.setModelProperty(bean, exists, WxAnswerRule.KEYWORD);
				ModelUtils.setModelProperty(bean, exists, WxAnswerRule.ANSWER_TYPE);
				ModelUtils.setModelProperty(bean, exists, WxAnswerRule.ANSWER);
				if(exists.update()){
					result = "修改成功";
				}else{
					result = "修改失败";
				}
			}
		}else{
			result = IN_PRARM_ERROR;
		}
		return result;
	}
    public WxAnswerRule getRuleById(String id){
    	if(StrUtils.isEmpty(id)){
    		return null;
    	}
    	return WxAnswerRule.dao.findById(id);
    }
	private List<WxAnswerRule> getKerWordRuleList(String ruleType, Long accontId){
		String sql = "select * from "+WxAnswerRule.getTableName()+" where "+WxAnswerRule.RULE_TYPE+"="+ruleType;
		if(accontId==null){
			sql += " and "+WxAnswerRule.ACCOUNT_ID+" is null";
		}else{
			sql += " and "+WxAnswerRule.ACCOUNT_ID+"="+accontId;
		}
		return WxAnswerRule.dao.find(sql);
    }
	private List<WxAnswerRule> setAnswerTypeNameToList(List<WxAnswerRule> ls){
		if(ls!=null && ls.size()>0){
			for(WxAnswerRule war : ls){
				setAnswerTypeName(war);
			}
		}
		return ls;
	}
	private void setAnswerTypeName(WxAnswerRule war){
		war.put("answerTypeName", ReqType.getTypeNameByTypeId(war.getInt(WxAnswerRule.ANSWER_TYPE)));
	}
    
    public static WxAnwserRuleService getInstance(){
		if(instance==null){
			synchronized (WxAnwserRuleService.class) {
				if(instance==null)
					instance = new WxAnwserRuleService();
			}
		}
		return instance;
	}
	private static WxAnwserRuleService instance = null;
	private WxAnwserRuleService(){}
}
