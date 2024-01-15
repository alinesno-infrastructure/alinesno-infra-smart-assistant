package com.alinesno.infra.smart.assistant.plugin.controller;

import cn.hutool.core.util.IdUtil;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.smart.assistant.enums.AssistantConstants;
import com.alinesno.infra.smart.assistant.role.context.RoleChainContext;
import com.dtflys.forest.annotation.Get;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/assistant/role/plugin")
@RestController
public class RolePluginController {

    @Resource
    private FlowExecutor flowExecutor;

    /**
     * 测试运行插件
     * @return
     */
    @GetMapping("/testPlugin")
    public AjaxResult testPlugin(){

        LiteFlowChainELBuilder.createChain().setChainId("DemoPluginChain").setEL(
                "THEN(DemoPlugin_a,DemoPlugin_b)"
        ).build();

        flowExecutor.reloadRule();

        RoleChainContext roleContext = new RoleChainContext() ;
        roleContext.setBusinessId(IdUtil.getSnowflakeNextIdStr());

        LiteflowResponse response = flowExecutor.execute2Resp("DemoPluginChain" , null , roleContext);
        log.debug("response = {}" , response);

        return AjaxResult.success() ;
    }

}

