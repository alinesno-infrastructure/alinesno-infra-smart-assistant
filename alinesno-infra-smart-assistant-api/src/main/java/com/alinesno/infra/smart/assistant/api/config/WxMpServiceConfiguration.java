package com.alinesno.infra.smart.assistant.api.config;

import com.alinesno.infra.smart.assistant.api.properties.WxMpProperties;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpServiceConfiguration {

    @Autowired
    private WxMpProperties properties;

    @Bean
    public WxMpService wxMpService() {

        WxMpService service = new WxMpServiceImpl();

        Map<String, WxMpConfigStorage> collect = new HashMap<>() ;

        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(properties.getAppId());
        configStorage.setSecret(properties.getSecret());
        configStorage.setToken(properties.getToken());
        configStorage.setAesKey(properties.getAesKey());

        collect.put(properties.getAppId() , configStorage);

//        Map<String, WxMpConfigStorage> collect = configs.stream().map(a -> {
//            return configStorage;
//        }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o));

        service.setMultiConfigStorages(collect);

        return service;
    }
}
