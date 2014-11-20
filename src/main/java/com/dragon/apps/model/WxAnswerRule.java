package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class WxAnswerRule extends Model<WxAnswerRule> {
	public static final String RULE_TYPE_KEYWORD = "1";//关键字回复规则，可以有多条
	public static final String RULE_TYPE_SUBSCIBE = "2";//关注回复规则
	public static final String RULE_TYPE_DEFAULT = "3";//默认回复规则
    private static final long serialVersionUID = 1L;
    public static WxAnswerRule dao = new WxAnswerRule();

    public static String getTableName() {
        return "wx_answer_rule";
    }

    public static final String ID = "id";
    public static final String RULE_TYPE = "rule_type";//规则类型,char(1)
    public static final String KEYWORD = "keyword";//关键字类型时的关键字,VARCHAR(256)
    public static final String ANSWER_TYPE = "answer_type";//char,回复内容的类型,refers to ReqType.
    public static final String ANSWER = "answer";//varchar,回复内容，文本即为文本值，其它可能为参照(素材)id
    public static final String ACCOUNT_ID = "account_id";//bigint,创建该规则的微信id，如果为null则表示是超级管理员的.

}
