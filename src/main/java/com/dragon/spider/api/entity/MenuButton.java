package com.dragon.spider.api.entity;

import com.dragon.spider.util.JSONUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单按钮对象
 * @author peiyu
 */
public class MenuButton implements Model {

    private MenuType type;

    private String name;

    private String key;

    private String url;

    private List<MenuButton> sub_button;

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<MenuButton> sub_button) {
        this.sub_button = sub_button;
    }

    public String toJsonString() {
        return JSONUtil.toJson(this);
    }
}
