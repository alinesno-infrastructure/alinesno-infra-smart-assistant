package com.alinesno.infra.smart.assistant.config;

import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import com.dtflys.forest.springboot.annotation.ForestScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 统一配置中心
 */
@EnableScheduling
@EnableRetry
@EnableInfraSsoApi
@EnableActable
@Configuration
@ForestScan(basePackages = "com.alinesno.infra.smart.assistant.adapter")
@MapperScan("com.alinesno.infra.smart.assistant.mapper")
public class AppConfiguration {

}
