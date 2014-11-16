package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.Objects;

/**
 * Created by Administrator on 2014/11/2.
 */
public class WxMaterial extends Model<WxMaterial> {
    private static final long serialVersionUID = 1L;
    public static WxMaterial dao = new WxMaterial();

    public static String getTableName() {
        return "wx_material";
    }

    public static final String ID = "id";
    public static final String DESCRIBE = "describe";
    public static final String FILE_PATH = "file_path";

    public long getId() {
        return getLong(ID);
    }

    public String getDescribe() {
        return getStr(DESCRIBE);
    }

    public String getFilePath() {
        return getStr(FILE_PATH);
    }

    public WxMaterial setDescribe(String describe) {
        return set(DESCRIBE, describe);
    }

    public WxMaterial setFilePath(String filePath) {
        return set(FILE_PATH, filePath);
    }


}
