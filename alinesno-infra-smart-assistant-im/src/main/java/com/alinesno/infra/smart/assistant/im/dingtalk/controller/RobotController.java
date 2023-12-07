package com.alinesno.infra.smart.assistant.im.dingtalk.controller;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.SuperController;
import com.alinesno.infra.smart.assistant.im.dingtalk.dto.DingtalkRobotMessageDto;
import com.alinesno.infra.smart.assistant.im.dingtalk.event.DingtalkMsgDispatcher;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参考相关资料：https://open.dingtalk.com/document/orgapp/robot-reply-and-send-messages
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/smart/assistant/dingtalk/robot/")
public class RobotController extends SuperController {

    @Autowired
    private DingtalkMsgDispatcher dingtalkMsgDispatcher ;

    /**
     * 发送消息给钉钉
     * @return
     */
    @SneakyThrows
    @GetMapping("/send")
    public AjaxResult robotSend() {

        DingtalkRobotMessageDto dto = new DingtalkRobotMessageDto() ;

        dto.setAtAll(false);
        dto.setMessageType("markdown");
        dto.setMessageContent("你要求生成的Java计算机考核题目已经生成，请查看连接.");

        dingtalkMsgDispatcher.sendMessageWebhook(dto, null) ;

        return ok() ;
    }

}