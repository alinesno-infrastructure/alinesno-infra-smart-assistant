package com.alinesno.infra.smart.assistant.role.bean;

import java.util.List;

// 创建 Expert 接口
public interface BaseExpert {

    // 声明抽象方法，用于后期扩展,mapReduce处理
    void performSpecializedTask(List<PromptMessage> prompts);

}