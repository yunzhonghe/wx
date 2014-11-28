package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxAnswerRule extends Model<WxAnswerRule> {
	public static final String RULE_TYPE_KEYWORD = "1";//关键字回复规则，可以有多条
	public static final String RULE_TYPE_SUBSCRIBE = "2";//关注回复规则
	public static final String RULE_TYPE_DEFAULTA = "3";//默认回复规则
	
    private static final long serialVersionUID = 1L;
    public static WxAnswerRule dao = new WxAnswerRule();

    public static String getTableName() {
        return "wx_answer_rule";
    }
    public WxAnswerRule getDefaultaWxAnswerRule(Long accountId){
    	return getDefaultaOrSubscribeWxAnswerRule(accountId, RULE_TYPE_DEFAULTA);
    }
    public WxAnswerRule getSubscribeWxAnswerRule(Long accountId){
    	return getDefaultaOrSubscribeWxAnswerRule(accountId, RULE_TYPE_SUBSCRIBE);
    }
    private WxAnswerRule getDefaultaOrSubscribeWxAnswerRule(Long accountId, String ruleType){
    	if(accountId!=null){
    		String sql = "select * from "+getTableName()+" where "+"ACCOUNT_ID="+accountId+" and "+RULE_TYPE+"="+ruleType;
    		return findFirst(sql);
    	}
    	return null;
    }
    @Override
    public String toString() {
    	return "WxAnswerRule[id="+get(ID)+",rule_type="+get(RULE_TYPE)+",keyword="+get(KEYWORD)
    			+",answer_type="+get(ANSWER_TYPE)+",answer="+get(ANSWER)+",account_id="+get(ACCOUNT_ID)+"]";
    }

    public static final String ID = "id";//long
    public static final String RULE_TYPE = "rule_type";//规则类型,char(1)
    public static final String KEYWORD = "keyword";//关键字类型时的关键字,VARCHAR(256)，用|隔开
    public static final String ANSWER_TYPE = "answer_type";//int,回复内容的类型,refers to ReqType.
    public static final String ANSWER = "answer";//varchar,回复内容，文本即为文本值，其它可能为参照(素材)id
    public static final String ACCOUNT_ID = "account_id";//bigint,创建该规则的微信id，如果为null则表示是超级管理员的.

}
