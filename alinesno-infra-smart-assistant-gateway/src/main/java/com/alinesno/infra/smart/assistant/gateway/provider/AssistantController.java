package com.alinesno.infra.smart.assistant.gateway.provider;

import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.SuperController;
import com.alinesno.infra.smart.assistant.entity.IndustryRoleEntity;
import com.alinesno.infra.smart.assistant.service.IIndustryRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 这是一个助手控制器类
 * 该类用于提供助手功能的接口
 * 作者：luoxiaodong
 * 版本：1.0.0
 */
@Slf4j
@RestController
@Scope("prototype")
@RequestMapping("/api/infra/smart/assistant/agent")
public class AssistantController extends SuperController {

    @Autowired
    private IIndustryRoleService roleService ;

    /**
     * 获取到所有的agent
     * @return
     */
    @GetMapping("/list")
    public AjaxResult agentList(){
        List<IndustryRoleEntity> roleEntityList = roleService.list() ;
        return AjaxResult.success(roleEntityList) ;
    }

    /**
     * 查询Agent详情
     * @return
     */
    @GetMapping("/getById")
    public IndustryRoleEntity getById(String roleId){
        return roleService.getById(roleId);
    }

}
