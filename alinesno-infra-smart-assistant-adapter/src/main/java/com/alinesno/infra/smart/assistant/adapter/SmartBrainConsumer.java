package com.alinesno.infra.smart.assistant.adapter;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.smart.brain.api.BrainTaskDto;
import com.alinesno.infra.smart.brain.api.reponse.TaskContentDto;
import com.dtflys.forest.annotation.*;

/**
 * 调用接口
 */
@BaseRequest(
    baseURL = "#{alinesno.infra.gateway.host}" ,
    connectTimeout = 30*1000
)
public interface SmartBrainConsumer {

    /**
     * 发送离线任务
     * @param dto
     * @return
     */
    @Post(url="/api/llm/chatTask")
    AjaxResult chatTask(@JSONBody BrainTaskDto dto) ;

    /**
     * 查询离线任务
     * @param businessId
     * @return
     */
    @Post(url="/api/llm/chatContent")
    AjaxResult chatContent(@Query("businessId") String businessId) ;

    /**
     * 更新任务生成内容
     * @param dto
     * @return
     */
    @Post(url="/api/llm/modifyContent")
    AjaxResult modifyContent(@JSONBody TaskContentDto dto);
}
