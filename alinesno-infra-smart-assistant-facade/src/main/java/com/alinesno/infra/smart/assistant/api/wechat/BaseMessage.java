package com.alinesno.infra.smart.assistant.api.wechat;

public class BaseMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;

    // 消息类型（链接-link /地理位置-location /小视频-shortvideo/视频-video /语音-voice /图片-image /文本-text）
    private String MsgType;
    private Long MsgId;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public Long getMsgId() {
        return MsgId;
    }

    public void setMsgId(Long msgId) {
        MsgId = msgId;
    }
}
 