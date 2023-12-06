package com.alinesno.infra.smart.assistant.im.dingtalk.controller;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.SuperController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RobotController extends SuperController {

    /**
     * 发送消息给钉钉
     * @return
     */
    @GetMapping("/send")
    public AjaxResult send() {

        return ok() ;
    }
}