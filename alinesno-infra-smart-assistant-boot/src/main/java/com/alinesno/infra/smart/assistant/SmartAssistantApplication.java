package com.alinesno.infra.smart.assistant;

import com.alinesno.infra.common.core.context.SpringContext;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 集成一个Java开发示例工具
 * @author LuoAnDong
 * @since 2023年8月3日 上午6:23:43
 */
@EnableInfraSsoApi
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SmartAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartAssistantApplication.class, args);
	}

	@Bean
	public SpringContext getSpringContext(){
		return new SpringContext() ;
	}

}