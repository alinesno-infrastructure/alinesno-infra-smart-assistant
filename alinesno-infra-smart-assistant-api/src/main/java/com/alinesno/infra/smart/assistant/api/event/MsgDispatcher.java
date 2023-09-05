package com.alinesno.infra.smart.assistant.api.event;

import com.alibaba.fastjson.JSONObject;
import com.alinesno.infra.smart.assistant.api.request.*;
import com.alinesno.infra.smart.assistant.api.response.TextMessageRes;
import com.alinesno.infra.smart.assistant.api.utils.MessageUtil;

import java.util.Date;
import java.util.Map;

/**
 * 微信消息业务处理分发器
 * 该类用于处理不同类型的微信消息，并进行相应的处理逻辑
 */
public class MsgDispatcher {

    /**
     * 处理微信消息
     *
     * @param map 包含微信消息的键值对
     * @return 返回处理后的响应消息
     */
    public static String processMessage(Map<String, String> map) {

        String openid = map.get("FromUserName"); //用户openid
        String mpid = map.get("ToUserName");   //公众号原始ID

        TextMessageRes textMessageRes = new TextMessageRes();
        textMessageRes.setToUserName(openid);
        textMessageRes.setFromUserName(mpid);
        textMessageRes.setCreateTime(new Date().getTime());
        textMessageRes.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
            String jsonString = JSONObject.toJSONString(map);
            TextMessageReq textMessage = JSONObject.parseObject(jsonString, TextMessageReq.class);

            textMessageRes.setContent("你好，这里是测试回复");

            return MessageUtil.textMessageToXml(textMessageRes);
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
            String jsonString = JSONObject.toJSONString(map);
            ImageMessageReq imageMessage = JSONObject.parseObject(jsonString, ImageMessageReq.class);

        }


        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
            String jsonString = JSONObject.toJSONString(map);
            LinkMessageReq linkMessage = JSONObject.parseObject(jsonString, LinkMessageReq.class);

        }


        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
            String jsonString = JSONObject.toJSONString(map);
            LocationMessageReq locationMessage = JSONObject.parseObject(jsonString, LocationMessageReq.class);

        }


        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频/小视频消息
            String jsonString = JSONObject.toJSONString(map);
            VideoMessageReq videoMessage = JSONObject.parseObject(jsonString, VideoMessageReq.class);

        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
            String jsonString = JSONObject.toJSONString(map);
            VoiceMessageReq voiceMessage = JSONObject.parseObject(jsonString, VoiceMessageReq.class);

        }
        return "";
    }
}
