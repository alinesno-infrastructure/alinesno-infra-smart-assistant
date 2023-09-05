package com.alinesno.infra.smart.assistant.api.config;

import com.alinesno.infra.smart.assistant.api.properties.WxMpProperties;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * WxMpServiceConfiguration 是一个配置类，用于配置微信公众号服务的相关属性和实例化 WxMpService。
 */
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpServiceConfiguration {

    @Autowired
    private WxMpProperties properties;

    /**
     * 实例化 WxMpService，用于操作微信公众号的相关功能。
     *
     * @return WxMpService 实例
     */
    @Bean
    public WxMpService wxMpService() {

        WxMpService service = new WxMpServiceImpl();

        Map<String, WxMpConfigStorage> collect = new HashMap<>();

        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(properties.getAppId());
        configStorage.setSecret(properties.getSecret());
        configStorage.setToken(properties.getToken());
        configStorage.setAesKey(properties.getAesKey());

        collect.put(properties.getAppId(), configStorage);

        service.setMultiConfigStorages(collect);

        return service;
    }
}
