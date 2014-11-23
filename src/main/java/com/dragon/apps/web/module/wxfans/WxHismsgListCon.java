package com.dragon.apps.web.module.wxfans;

import java.io.Serializable;

import com.dragon.apps.utils.StrUtils;

public class WxHismsgListCon implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String TIMELIMIT_UNLIMIT = "不限";
	public static final String TIMELIMIT_DEFAULT = "1";
	public static final String[] timeLimits = {
		"1","3","7","15","30",TIMELIMIT_UNLIMIT
	};
	
	private String timeLimit;//最近时间的限制，如最近1天、3天、7天、15天、30天、不限之内等
	private String msg;//消息内容模糊匹配
	
	private String accountId;
	private long laccountId = -1;
	
	public WxHismsgListCon(String timeLimit,String msg){
		this.timeLimit = timeLimit;
		this.msg = msg;
	}
	public WxHismsgListCon(String msg){
		this.msg = msg;
	}
	public WxHismsgListCon(){}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
