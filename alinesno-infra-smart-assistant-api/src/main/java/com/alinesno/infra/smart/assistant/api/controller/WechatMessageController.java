package com.alinesno.infra.smart.assistant.api.controller;

import com.alinesno.infra.smart.assistant.api.event.MsgDispatcher;
import com.alinesno.infra.smart.assistant.api.utils.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * WechatMessageController 是一个控制器类，用于处理微信消息相关的请求。
 */
@RestController
@RequestMapping(value = "/v1/smart/assistant/wechat")
public class WechatMessageController {

    private static final Logger log = LoggerFactory.getLogger(WechatMessageController.class);

    // 在这里可以添加处理微信消息请求的方法

    /**
     * 接收微信消息(普通消息+事件消息)以及自动回复对应类型消息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/token")
    public String receiveAndResponseMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String>  map = MessageUtil.parseXml(request) ;

//        log.debug("map = {}" , JSONObject.toJSON(map));

        String result = MsgDispatcher.processMessage(map) ;

        log.debug("result = {}" , result);

        return result ;
    }

}
