package com.alinesno.infra.smart.assistant.im.dingtalk.callback;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.smart.assistant.im.dingtalk.service.RobotGroupMessagesService;
import com.dingtalk.open.app.api.callback.OpenDingTalkCallbackListener;
import com.dingtalk.open.app.api.models.bot.ChatbotMessage;
import com.dingtalk.open.app.api.models.bot.MessageContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 机器人消息回调
 *
 * @author zeymo
 */
@Slf4j
@Component
public class ChatBotCallbackListener implements OpenDingTalkCallbackListener<ChatbotMessage, JSONObject> {
    private RobotGroupMessagesService robotGroupMessagesService;

    @Autowired
    public ChatBotCallbackListener(RobotGroupMessagesService robotGroupMessagesService) {
        this.robotGroupMessagesService = robotGroupMessagesService;
    }

    /**
     * https://open.dingtalk.com/document/orgapp/the-application-robot-in-the-enterprise-sends-group-chat-messages
     *
     * @param message
     * @return
     */
    @Override
    public JSONObject execute(ChatbotMessage message) {
        try {
            MessageContent text = message.getText();
            if (text != null) {

                String senderId = message.getSenderId() ;
                String senderNick = message.getSenderNick() ;
                String chatbotUserId = message.getChatbotUserId() ;

                log.debug("发送者ID senderId = {}" , senderId);
                log.debug("发送者名称 senderNick = {}" , senderNick);
                log.debug("机器人Id chatbotUserId = {}" , chatbotUserId);

                String msg = text.getContent();
                log.info("receive bot message from user={}, msg={}", message.getSenderId(), msg);
                String openConversationId = message.getConversationId();
                try {
                    // 发送培训相关的要求到任务中

                    //发送机器人消息
                    String result = robotGroupMessagesService.send(openConversationId, "收到，你的任务我已经在处理，请耐心等待 :-)");
                    log.debug("result = {}" , result);
                } catch (Exception e) {
                    log.error("send group message by robot error:" + e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.error("receive group message by robot error:" + e.getMessage(), e);
        }
        return new JSONObject();
    }
}