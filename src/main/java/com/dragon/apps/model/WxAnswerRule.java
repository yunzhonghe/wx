package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by Administrator on 2014/11/2.
 */
public class WxAnswerRule extends Model<WxAnswerRule> {
    private static final long serialVersionUID = 1L;
    public static WxAnswerRule dao = new WxAnswerRule();

    public static String getTableName() {
        return "wx_answer_rule";
    }

    public static final String ID = "id";
    public static final String KEYWORD = "keyword";
    public static final String ANSWER = "answer";
    public static final String ANSWER_TYPE = "answer_type";
    public static final String MATERIAL_ID = "material_id";
    public static final String MATERIAL_PATH = "material_path";

    public long getId() {
        return getLong(ID);
    }

    public String getKeyword() {
        return getStr(KEYWORD);
    }

    public String getAnswer() {
        return getStr(ANSWER);
    }

    public int getAnswerType() {
        return getInt(ANSWER_TYPE);
    }

    public long getMaterialId() {
        return getLong(MATERIAL_ID);
    }

    public Object getMaterialPath() {
        return get(MATERIAL_PATH);
    }

    public WxAnswerRule setKeyword(String keyword) {
        return set(KEYWORD, keyword);
    }

    public WxAnswerRule setAnwser(String answer) {
        return set(ANSWER, answer);
    }

    public WxAnswerRule setAnswerType(int answerType) {
        return set(ANSWER_TYPE, answerType);
    }

    public WxAnswerRule setMaterialId(long materialId) {
        return set(MATERIAL_ID, materialId);
    }

    public WxAnswerRule setMaterialPath(Object materialPath) {
        return set(MATERIAL_PATH, materialPath);
    }


}
