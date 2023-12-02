package com.alinesno.infra.smart.assistant.service;

import com.alinesno.infra.common.facade.services.IBaseService;
import com.alinesno.infra.smart.assistant.entity.IndustryRoleEntity;
import com.alinesno.infra.smart.assistant.entity.RoleChainEntity;

/**
 * 应用构建Service接口
 * 
 * @version 1.0.0
 * @since 2023-09-30
 */
public interface IIndustryRoleService extends IBaseService<IndustryRoleEntity> {

    /**
     * 保存角色工作流信息
     *
     * @param entity
     * @param roleId
     */
    void saveRoleChainInfo(RoleChainEntity entity, String roleId);

}