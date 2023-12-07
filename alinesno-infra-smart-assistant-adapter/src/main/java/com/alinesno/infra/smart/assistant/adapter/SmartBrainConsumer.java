package com.alinesno.infra.smart.assistant.adapter;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.smart.brain.api.BrainTaskDto;
import com.dtflys.forest.annotation.*;

/**
 * 调用接口
 */
@BaseRequest(baseURL = "#{alinesno.infra.gateway.host}")
public interface SmartBrainConsumer {

    /**
     * 发送离线任务
     * @param dto
     * @return
     */
    @Post(url = "/api/llm/chatTask")
    public AjaxResult chatTask(@JSONBody BrainTaskDto dto) ;

    /**
     * 查询离线任务
     * @param businessId
     * @return
     */
    @Post(url = "/api/llm/chatContent")
    public AjaxResult chatContent(@Query("businessId") String businessId) ;

}
