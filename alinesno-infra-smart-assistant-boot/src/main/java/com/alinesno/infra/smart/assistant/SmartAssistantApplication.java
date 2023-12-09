package com.alinesno.infra.smart.assistant;

import com.alinesno.infra.smart.assistant.role.service.BrainRemoteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 集成一个Java开发示例工具
 * @author LuoAnDong
 * @since 2023年8月3日 上午6:23:43
 */
@SpringBootApplication
public class SmartAssistantApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SmartAssistantApplication.class, args);

		BrainRemoteService brainRemoteService = ctx.getBean(BrainRemoteService.class) ;
		System.out.println("BrainRemoteService = " + brainRemoteService);

	}

}