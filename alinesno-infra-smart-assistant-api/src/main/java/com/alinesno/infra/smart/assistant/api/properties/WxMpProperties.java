package com.alinesno.infra.smart.assistant.api.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信公众号配置属性类
 * 该类用于读取和设置微信公众号的相关配置信息
 */
@ConfigurationProperties(prefix = "alinesno.infra.smart.wechat")
public class WxMpProperties {
    /*
     * 设置微信公众号的appid
     */
    private String appId;
    /*
     * 设置微信公众号的app secret
     */
    private String secret;
    /*
     * 设置微信公众号的token
     */
    private String token;
    /*
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }
}
