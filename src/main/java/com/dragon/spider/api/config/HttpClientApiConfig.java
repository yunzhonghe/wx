package com.dragon.spider.api.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * API配置类，项目中请保证其为单例
 * @author peiyu
 */
public final class HttpClientApiConfig {

    private String name;

    private String password;

    private String token;

    private HttpClient client;
    
    public volatile boolean refreshing = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return  token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HttpClient getClient() {
		return client;
	}

	public void setClient(HttpClient client) {
		this.client = client;
	}

	public static class Builder {
        private String name;

        private String password;

        public Builder setAppid(String name) {
            this.name = name;
            return this;
        }

        public Builder setSecret(String password) {
            this.password = password;
            return this;
        }

        public HttpClientApiConfig build() {
            HttpClientApiConfig config = new HttpClientApiConfig();
            config.name = this.name;
            config.password = this.password;
            return config;
        }
    }

	@Override
	public String toString() {
		return "HttpClientApiConfig [name=" + name + ", password=" + password + ", token=" + token + ", client=" + client + ", refreshing="
				+ refreshing + "]";
	}
	
}
