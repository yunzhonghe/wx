package com.dragon.apps.web.module.wxfans;

import java.io.Serializable;

import com.dragon.apps.utils.StrUtils;

public class WxFansListCon implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String markName;
	private String tagid;
	private long ltagid = -1;
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
	public String getTagid() {
		return tagid;
	}
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	public long getLtagid() {
		if(ltagid==0){
			if(StrUtils.isNotEmpty(tagid)){
				ltagid = Long.parseLong(tagid);
			}
		}
		return ltagid;
	}
}
