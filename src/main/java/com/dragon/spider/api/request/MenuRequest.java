package com.dragon.spider.api.request;

import com.dragon.spider.api.entity.MenuButton;

import java.util.List;

/**
 * @author peiyu
 */
public class MenuRequest extends BaseRequest {

    private List<MenuButton> button;

    public List<MenuButton> getButton() {
        return button;
    }

    public void setButton(List<MenuButton> button) {
        this.button = button;
    }
}
