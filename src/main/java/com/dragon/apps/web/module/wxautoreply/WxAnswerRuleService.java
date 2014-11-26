package com.dragon.apps.web.module.wxautoreply;

import java.util.ArrayList;
import java.util.List;

import com.dragon.apps.model.WxAnswerRule;
import com.dragon.apps.utils.ModelUtils;
import com.dragon.apps.utils.RoleUtils;
import com.dragon.apps.utils.StrUtils;
import com.dragon.apps.web.module.base.BaseService;
import com.dragon.apps.web.module.base.IdValueBean;
import com.dragon.spider.message.req.ReqType;

public class WxAnswerRuleService extends BaseService{
    private static final long serialVersionUID = 1L;
    
    public List<WxAnswerRule> getKerWordRuleList(){
    	Long accontId = null;
    	if(!RoleUtils.isCurrentSuperAdmin()){
    		accontId = RoleUtils.gerCurAccountId();
    	}
    	return setAnswerTypeNameToList(getKerWordRuleList(accontId));
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
    /**
     * 维护应答规则
     * @param bean	前端填充后的规则对象
     * @param ruleType 规则的类型，参照WxAnswerRule.RULE_TYPE_*
     * @return
     */
    public String modifyWxAnswerRule(WxAnswerRule bean,String ruleType){
		String result = null;
		if(bean!=null){
			Long ruleId = bean.get(WxAnswerRule.ID);
			if(ruleId==null){//自动回复和关注回复的初始特殊处理(比如第一次调用时，数据库没有自动回复数据，则新增)
				if(ruleType!=null && (ruleType.equals(WxAnswerRule.RULE_TYPE_DEFAULTA) || ruleType.equals(WxAnswerRule.RULE_TYPE_SUBSCRIBE))){
					Long accountId = RoleUtils.gerCurAccountId();
					WxAnswerRule exists = null;
					if(ruleType.equals(WxAnswerRule.RULE_TYPE_DEFAULTA)){
						exists = WxAnswerRule.dao.getDefaultaWxAnswerRule(accountId);
					}else{
						exists = WxAnswerRule.dao.getSubscribeWxAnswerRule(accountId);
					}
					if(exists!=null){
						ModelUtils.setModelProperty(bean, exists, WxAnswerRule.ANSWER_TYPE);
						ModelUtils.setModelProperty(bean, exists, WxAnswerRule.ANSWER);
						if(exists.update()){
							result = "修改成功";
						}else{
							result = "修改失败";
						}
					}else{
						bean.set(WxAnswerRule.RULE_TYPE, ruleType);
						bean.set(WxAnswerRule.ACCOUNT_ID, accountId);
						if(bean.save()){
							return "添加成功";
						}else{
							return "添加失败";
						}
					}
				}
			}else{//关键字修改，ruleid不能为null
				WxAnswerRule exists = WxAnswerRule.dao.findById(ruleId);
				if(exists!=null){
					if(ruleType!=null && (ruleType.equals(WxAnswerRule.RULE_TYPE_DEFAULTA) || ruleType.equals(WxAnswerRule.RULE_TYPE_SUBSCRIBE))){
						ModelUtils.setModelProperty(bean, exists, WxAnswerRule.ANSWER_TYPE);
						ModelUtils.setModelProperty(bean, exists, WxAnswerRule.ANSWER);
					}else{//keyword
						ModelUtils.setModelProperty(bean, exists, WxAnswerRule.KEYWORD);
						ModelUtils.setModelProperty(bean, exists, WxAnswerRule.ANSWER_TYPE);
						ModelUtils.setModelProperty(bean, exists, WxAnswerRule.ANSWER);
					}
					if(exists.update()){
						result = "修改成功";
					}else{
						result = "修改失败";
					}
				}
			}
		}else{
			result = IN_PRARM_ERROR;
		}
		return result;
	}
    public String deleteWxAnswerRule(String sid){
		return deleteBean(sid, WxAnswerRule.dao);
	}
    /**
     * 获取当前人员的对应id的回复规则设置
     * @param id 规则id,当规则类别为默认规则或关注规则，该id可为null(因为这两种规则只有一个)
     * @param ruleType 规则类别
     * @return
     */
    public WxAnswerRule getRuleById(String id,String ruleType){
    	if(StrUtils.isEmpty(id)){
    		return getRuleById(id, ruleType, RoleUtils.gerCurAccountId());
    	}else{
    		return getRuleById(id, ruleType, null);
    	}
    }
    /**
     * 获取对应人员id的对应规则id的回复规则设置
     * @param id 规则id,当规则类别为默认规则或关注规则，该id可为null(因为这两种规则只有一个)
     * @param ruleType规则类别
     * @param curAccountId 微信账号id
     * @return
     */
    public WxAnswerRule getRuleById(String id,String ruleType, Long curAccountId){
    	if(StrUtils.isEmpty(id)){
    		if(ruleType!=null){
    			if(ruleType.equals(WxAnswerRule.RULE_TYPE_DEFAULTA)){//默认回复和关注回复修改页面载入特殊处理.
    				WxAnswerRule war = WxAnswerRule.dao.getDefaultaWxAnswerRule(curAccountId);
    				return war;
    			}else if(ruleType.equals(WxAnswerRule.RULE_TYPE_SUBSCRIBE)){
    				WxAnswerRule war = WxAnswerRule.dao.getSubscribeWxAnswerRule(curAccountId);
    				return war;
    			}
    		}
    		return null;
    	}else{
    		return WxAnswerRule.dao.findById(id);
    	}
    }
    /**
     * 获取对应帐号的关键字回复规则列表
     * @param accontId 帐号信息，如果为null则表示超级管理员的规则，不为null则为子管理员的规则.
     * @return
     */
    public List<WxAnswerRule> getKerWordRuleList(Long accontId){
		return getKerWordRuleList(WxAnswerRule.RULE_TYPE_KEYWORD, accontId);
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
    
    public static WxAnswerRuleService getInstance(){
		if(instance==null){
			synchronized (WxAnswerRuleService.class) {
				if(instance==null)
					instance = new WxAnswerRuleService();
			}
		}
		return instance;
	}
	private static WxAnswerRuleService instance = null;
	private WxAnswerRuleService(){}
}
