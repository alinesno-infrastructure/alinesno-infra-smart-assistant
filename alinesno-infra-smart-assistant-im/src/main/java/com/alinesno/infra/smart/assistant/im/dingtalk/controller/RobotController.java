package com.alinesno.infra.smart.assistant.im.dingtalk.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.SuperController;
import com.alinesno.infra.smart.assistant.adapter.SmartBrainConsumer;
import com.alinesno.infra.smart.assistant.im.dingtalk.dto.ChatMessageDto;
import com.alinesno.infra.smart.assistant.im.dingtalk.dto.DingtalkRobotMessageDto;
import com.alinesno.infra.smart.assistant.im.dingtalk.event.DingtalkMsgDispatcher;
import com.alinesno.infra.smart.brain.api.reponse.TaskContentDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 参考相关资料：https://open.dingtalk.com/document/orgapp/robot-reply-and-send-messages
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/smart/assistant/dingtalk/robot/")
public class RobotController extends SuperController {

    @Autowired
    private DingtalkMsgDispatcher dingtalkMsgDispatcher ;

    @Autowired
    private SmartBrainConsumer smartBrainConsumer ;

    /**
     * 获取到消息信息
     * @return
     */
    @GetMapping("/chatMessage")
    public AjaxResult chatMessage(String businessId){

        AjaxResult result = smartBrainConsumer.chatContent(businessId) ;

        log.debug("chatContent result = {}" , result);

        String resultData = result.get("data").toString() ;
        TaskContentDto ta = null ;
        if(resultData != null){
            ta =  JSONObject.parseObject(resultData, TaskContentDto.class) ;
            List<ChatMessageDto> messageList = new ArrayList<>() ;

            ChatMessageDto dto = new ChatMessageDto() ;
            dto.setChatText(ta.getGenContent());
            dto.setName("高级数据库工程师");
            dto.setRoleType("agent");
            dto.setDateTime(DateUtil.formatDateTime(new Date()));
            messageList.add(dto) ;

            ChatMessageDto personDto = new ChatMessageDto() ;
            personDto.setChatText("收到，罗小东的任务我已经在处理，请稍等1-2分钟 :-)");
            personDto.setName("考核题目生成Agent");
            personDto.setRoleType("person");
            personDto.setDateTime(DateUtil.formatDateTime(new Date()));
            messageList.add(personDto) ;

            return AjaxResult.success(messageList) ;
        }

        return AjaxResult.error() ;
    }

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