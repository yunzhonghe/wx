package com.dragon.spider.api.config;

/**
 * API配置类，项目中请保证其为单例
 * @author peiyu
 */
public final class ApiConfig {

    private String name;

    private String password;

    private String access_token;

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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
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

        public ApiConfig build() {
            ApiConfig config = new ApiConfig();
            config.name = this.name;
            config.password = this.password;
            return config;
        }
    }
}
