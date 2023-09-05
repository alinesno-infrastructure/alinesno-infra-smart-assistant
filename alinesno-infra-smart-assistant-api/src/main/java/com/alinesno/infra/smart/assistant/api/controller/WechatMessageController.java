package com.alinesno.infra.smart.assistant.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/smart/assistant/wechat")
public class WechatMessageController {

    private static final Logger log = LoggerFactory.getLogger(WechatMessageController.class) ;

}
