package com.dragon.apps.service;

import java.io.Serializable;
/**
 * 微信粉丝用户的业务操作，供wxfanscontroller调用
 * @author LiuJian
 *
 */
public class WxFansService implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String IN_PRARM_ERROR = "传入参数错误";
	
	public static WxFansService getInstance(){
		if(instance==null){
			synchronized (WxFansService.class) {
				if(instance==null)
					instance = new WxFansService();
			}
		}
		return instance;
	}
	private static WxFansService instance = null;
	private WxFansService(){}
}
