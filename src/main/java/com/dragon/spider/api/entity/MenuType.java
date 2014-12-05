package com.dragon.spider.api.entity;

/**
 * 微信菜单类型
 */
public class MenuType {
    /**
     * 点击推事件
     */
//    CLICK("click"),
    public static final String CLICK = "click";

    /**
     * 跳转URL
     */
//    VIEW("view"),
    public static final String VIEW = "view";

    /*-------------------------以下仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户------------------------*/

    /**
     * 扫码推事件
     */
//    SCANCODE_PUSH("scancode_push"),
    public static final String SCANCODE_PUSH = "scancode_push";

    /**
     * 扫码推事件且弹出“消息接收中”提示框
     */
//    SCANCODE_WAITMSG("scancode_waitmsg"),
    public static final String SCANCODE_WAITMSG = "scancode_waitmsg";

    /**
     * 弹出系统拍照发图
     */
//    PIC_SYSPHOTO("pic_sysphoto"),
    public static final String PIC_SYSPHOTO = "pic_sysphoto";

    /**
     * 弹出拍照或者相册发图
     */
//    PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),
    public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";

    /**
     * 弹出微信相册发图器
     */
//    PIC_WEIXIN("pic_weixin"),
    public static final String PIC_WEIXIN = "pic_weixin";

    /**
     * 弹出地理位置选择器
     */
//    LOCATION_SELECT("location_select");
    public static final String LOCATION_SELECT = "location_select";

}



