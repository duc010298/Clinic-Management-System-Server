package com.github.duc010298.cms.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
public class ApiConfig {
    private String tokenSecretKey;
    private String tokenHeaderString;
    private String tokenPrefix;

    public String getTokenSecretKey() {
        return tokenSecretKey;
    }

    public void setTokenSecretKey(String tokenSecretKey) {
        this.tokenSecretKey = tokenSecretKey;
    }

    public String getTokenHeaderString() {
        return tokenHeaderString;
    }

    public void setTokenHeaderString(String tokenHeaderString) {
        this.tokenHeaderString = tokenHeaderString;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }
}