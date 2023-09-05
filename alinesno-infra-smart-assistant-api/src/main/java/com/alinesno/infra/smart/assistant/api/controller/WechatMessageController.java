package com.alinesno.infra.smart.assistant.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WechatMessageController 是一个控制器类，用于处理微信消息相关的请求。
 */
@RestController
@RequestMapping(value = "/v1/smart/assistant/wechat")
public class WechatMessageController {

    private static final Logger log = LoggerFactory.getLogger(WechatMessageController.class);

    // 在这里可以添加处理微信消息请求的方法

}
