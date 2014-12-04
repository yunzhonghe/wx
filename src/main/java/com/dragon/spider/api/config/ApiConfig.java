package com.dragon.spider.api.config;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.utils.StrUtils;

/**
 * API配置类，项目中请保证其为单例
 * @author peiyu
 */
public final class ApiConfig {
	//extends info with originalId,wx_type(2014-12-03,ljsnake)
	private String originalId;//微信原始id
	private Long accountId;//数据库的为新帐号id主键
	private Integer wxType;//微信类型：订阅号、服务号之类，参照WxAccType.java
	
	public void fillApiConfigByOriginalId(ApiConfig config,String originalId){
		if(config!=null && StrUtils.isNotEmpty(originalId)){
			WxAccount account = WxAccount.dao.getWxAccountByOriginalId(originalId);
			if(account!=null){
				config.setOriginalId(originalId);
				config.setAccountId(account.getId());
				config.setWxType(account.getTypeid());
				config.setAppid(account.getStr("app_id"));
				config.setSecret(account.getStr("app_secret"));
				config.setAccess_token(account.getStr("access_token"));
			}
		}
	}
	public static ApiConfig getApiConfigByOriginalId(String originalId){
		if(StrUtils.isNotEmpty(originalId)){
			ApiConfig config = new ApiConfig();
			WxAccount account = WxAccount.dao.getWxAccountByOriginalId(originalId);
			if(account!=null){
				config.setOriginalId(originalId);
				config.setAccountId(account.getId());
				config.setWxType(account.getTypeid());
				config.setAppid(account.getStr("app_id"));
				config.setSecret(account.getStr("app_secret"));
				config.setAccess_token(account.getStr("access_token"));
			}
			return config;
		}
		return null;
	}
	
    private String appid;
    private String secret;
    private String access_token;

    public volatile boolean refreshing = false;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getOriginalId() {
		return originalId;
	}
	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}
	public Integer getWxType() {
		return wxType;
	}
	public void setWxType(Integer wxType) {
		this.wxType = wxType;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public static class Builder {
        private String appid;

        private String secret;

        public Builder setAppid(String appid) {
            this.appid = appid;
            return this;
        }

        public Builder setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        public ApiConfig build() {
            ApiConfig config = new ApiConfig();
            config.appid = this.appid;
            config.secret = this.secret;
            return config;
        }
    }
}
