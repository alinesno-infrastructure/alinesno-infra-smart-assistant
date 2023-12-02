package com.alinesno.infra.smart.assistant.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.smart.assistant.entity.WorkflowExecutionEntity;

/**
 * 应用构建Service接口
 * 
 * @version 1.0.0
 * @since 2023-09-30
 */
public interface IWorkflowExecutionService extends IBaseService<WorkflowExecutionEntity> {

    /**
     * 获取名称获取到构建次数
     * @param chainName
     * @return
     */
    int getByChainName(String chainName);

}