package com.alinesno.infra.smart.assistant.im.dingtalk.controller;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.SuperController;
import com.alinesno.infra.smart.assistant.im.dingtalk.dto.DingtalkRobotMessageDto;
import com.alinesno.infra.smart.assistant.im.utils.DingtalkSignUtils;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 参考相关资料：https://open.dingtalk.com/document/orgapp/robot-reply-and-send-messages
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/smart/assistant/dingtalk/robot/")
public class RobotController extends SuperController {

    @Value("${alinesno.infra.smart.dingtalk.robot.webhook:}")
    private String robotWebhook;

    @Value("${alinesno.infra.smart.dingtalk.robot.secret:}")
    private String robotSecret;

    /**
     * 发送消息给钉钉
     * @return
     */
    @SneakyThrows
    @GetMapping("/send")
    public AjaxResult robotSend() {

        DingtalkRobotMessageDto dto = new DingtalkRobotMessageDto() ;
        dto.setWebhook(robotWebhook) ;
        dto.setSecret(robotSecret) ;
        dto.setAtAll(false);
        dto.setAtUser("");
        dto.setMessageType("markdown");
        dto.setMessageContent("你要求生成的Java计算机考核题目已经生成，请查看连接.");

        sendMessageWebhook(dto) ;

        return ok() ;
    }

    public void sendMessageWebhook(DingtalkRobotMessageDto dto) throws ApiException {

        String signWebhook = DingtalkSignUtils.signWebHook(dto.getSecret() , dto.getWebhook()) ;
        DingTalkClient client = new DefaultDingTalkClient(signWebhook);

        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(dto.getMessageType());

        if(dto.getMessageType().equals("text")){
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(dto.getMessageContent());
            request.setText(text);
        }else if(dto.getMessageType().equals("link")){
            OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
            link.setMessageUrl("https://www.dingtalk.com/");
            link.setPicUrl("https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png");
            link.setTitle("时代的火车向前开");
            link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
            request.setLink(link);
        }else if(dto.getMessageType().equals("markdown")){
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle("## 任务执行结果通知");

            String markdownText = "#### 你的任务已经完成:\n" +
                    "- <font color=\"#3b5998\">请生成关于CentOS的相关题目,生成内容包含选择题目和填空题</font>\n" +
                    "---\n" +
                    "- 持续时间: 1分23秒\n" +
                    "- 环境: [测试环境](#)\n" +
                    "- 内容: [查看生成结果](http://www.thinkpage.cn/)\n" +
                    "- 状态: 成功\n" +
                    "- 完成时间: 2023-12-21 12:33:23\n" +
                    "- 执行人：培训题设计Agent" ;
                    ;

            markdown.setText(markdownText) ;
            request.setMarkdown(markdown);
        }

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtUserIds(List.of(dto.getAtUser()));
        at.setIsAtAll(dto.isAtAll());
        request.setAt(at);

        OapiRobotSendResponse response = client.execute(request);
        System.out.println(response.getBody());
    }

}