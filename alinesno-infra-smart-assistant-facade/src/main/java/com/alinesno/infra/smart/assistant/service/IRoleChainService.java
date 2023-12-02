package com.alinesno.infra.smart.assistant.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.smart.assistant.entity.RoleChainEntity;

/**
 * 应用构建Service接口
 * 
 * @version 1.0.0
 * @since 2023-09-30
 */
public interface IRoleChainService extends IBaseService<RoleChainEntity> {

    /**
     * 运行工作流
     * @param chainId
     */
    void runById(Long chainId);

}