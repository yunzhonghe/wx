package com.dragon.apps.model.bean;

import java.io.Serializable;

public class WxFansListCon implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String markName;
	public WxFansListCon(String nickName,String markName){
		this.nickName = nickName;
		this.markName = markName;
	}
	public WxFansListCon(){}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMarkName() {
		return markName;
	}
	public void setMarkName(String markName) {
		this.markName = markName;
	}
}
