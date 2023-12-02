package com.alinesno.infra.smart.assistant.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.smart.assistant.entity.RoleChainEntity;
import com.alinesno.infra.smart.assistant.mapper.RoleChainMapper;
import com.alinesno.infra.smart.assistant.service.IRoleChainService;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 应用构建Service业务层处理
 * 
 * @version 1.0.0
 * @since 2023-09-30
 */
@Slf4j
@Service
public class RoleChainServiceImpl extends IBaseServiceImpl<RoleChainEntity, RoleChainMapper> implements IRoleChainService {

    @Resource
    private FlowExecutor flowExecutor;

    @Override
    public void runById(Long chainId) {

        RoleChainEntity roleChain = getById(chainId) ;

        LiteflowResponse response = flowExecutor.execute2Resp(roleChain.getChainName() , "arg");
        log.debug("response = {}" , response);
    }
}