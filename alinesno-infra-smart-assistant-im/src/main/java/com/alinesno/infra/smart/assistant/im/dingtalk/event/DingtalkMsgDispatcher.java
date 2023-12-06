package com.alinesno.infra.smart.assistant.im.dingtalk.event;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DingtalkMsgDispatcher {

    /**
     * 处理请求业务
     * @param bizData
     */
    public static void process(JSONObject bizData) {
        log.info("bizData = {}" , bizData.toJSONString());
    }
}
