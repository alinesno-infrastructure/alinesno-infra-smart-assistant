package com.alinesno.infra.smart.assistant.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.smart.assistant.entity.IndustryRoleEntity;
import com.alinesno.infra.smart.assistant.entity.RoleChainEntity;
import com.alinesno.infra.smart.assistant.mapper.IndustryRoleMapper;
import com.alinesno.infra.smart.assistant.redis.MessageConstants;
import com.alinesno.infra.smart.assistant.redis.PublishService;
import com.alinesno.infra.smart.assistant.service.IIndustryRoleService;
import com.alinesno.infra.smart.assistant.service.IRoleChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 应用构建Service业务层处理
 * 
 * @version 1.0.0
 * @since 2023-09-30
 */
@Slf4j
@Service
public class IndustryRoleServiceImpl extends IBaseServiceImpl<IndustryRoleEntity, IndustryRoleMapper> implements IIndustryRoleService {

    @Autowired
    private IRoleChainService roleChainService ;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private PublishService publishService ;

    @Override
    public void saveRoleChainInfo(RoleChainEntity chain , String roleId) {

        chain.setEnable("1");
        chain.setChainApplicationName(applicationName);
        chain.setCreateTime(new Date());

        roleChainService.save(chain) ;

        IndustryRoleEntity role = this.getById(roleId) ;
        role.setChainId(chain.getId());
        this.update(role) ;

        // 发送消息用于规则的热更新
        publishService.sendMsg(MessageConstants.RELOAD_RULE);
    }
}