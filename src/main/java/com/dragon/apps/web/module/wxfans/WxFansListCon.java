package com.dragon.apps.web.module.wxfans;

import java.io.Serializable;

import com.dragon.apps.utils.StrUtils;

public class WxFansListCon implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String markName;
	private String tagid;
	private String accountId;
	private long ltagid = -1;
	private long laccountId = -1;
	public WxFansListCon(String nickName,String markName){
		this.nickName = nickName;
		this.markName = markName;
	}
	public WxFansListCon(String nickName){
		this.nickName = nickName;
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
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public long getLaccountId() {
		if(laccountId==0){
			if(StrUtils.isNotEmpty(accountId)){
				laccountId = Long.parseLong(accountId);
			}
		}
		return laccountId;
	}
}
