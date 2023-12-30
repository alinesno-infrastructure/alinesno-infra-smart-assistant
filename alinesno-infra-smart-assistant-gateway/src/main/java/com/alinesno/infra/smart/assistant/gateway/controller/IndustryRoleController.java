package com.alinesno.infra.smart.assistant.gateway.controller;

import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.smart.assistant.entity.IndustryRoleEntity;
import com.alinesno.infra.smart.assistant.entity.RoleChainEntity;
import com.alinesno.infra.smart.assistant.service.IIndustryRoleService;
import com.alinesno.infra.smart.assistant.service.IRoleChainService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用构建Controller
 * 处理与ApplicationEntity相关的请求
 * 继承自BaseController类并实现IApplicationService接口
 * 
 * @version 1.0.0
 * @since 2023-09-30
 */
@Slf4j
@Api(tags = "IndustryRole")
@RestController
@Scope("prototype")
@RequestMapping("/api/infra/smart/assistant/role")
public class IndustryRoleController extends BaseController<IndustryRoleEntity, IIndustryRoleService> {

    @Autowired
    private IIndustryRoleService service;

    @Autowired
    private IRoleChainService roleChainService ;

    /**
     * 获取ApplicationEntity的DataTables数据
     * 
     * @param request HttpServletRequest对象
     * @param model Model对象
     * @param page DatatablesPageBean对象
     * @return 包含DataTables数据的TableDataInfo对象
     */
    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign(), page);
    }

    /**
     * 运行角色流程
     * @return
     */
    @GetMapping("/listAllRole")
    public AjaxResult listAllRole(){

        LambdaQueryWrapper<IndustryRoleEntity> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.orderByDesc(IndustryRoleEntity::getAddTime) ;

        List<IndustryRoleEntity> roleEntityList = service.list(wrapper) ;

        return AjaxResult.success(roleEntityList) ;
    }

    /**
     * 运行角色流程
     * @return
     */
    @GetMapping("/runRoleChainByRoleId")
    public AjaxResult runRoleChainByRoleId(long roleId , @RequestParam("text") String executeOrder){

        Assert.hasLength(executeOrder , "执行命令为空.");

        Map<String , Object> params = new HashMap<>() ;
        params.put("label1" , executeOrder) ;

        service.runRoleChainByRoleId(params , roleId , null) ;

        return ok() ;
    }

    /**
     * 获取到角色的ID
     * @param roleId
     * @return
     */
    @GetMapping("/getRoleChainByChainId")
    public AjaxResult getRoleChainByChainId(String roleId){

        IndustryRoleEntity role = service.getById(roleId) ;
        RoleChainEntity roleChain = roleChainService.getById(role.getChainId()) ;

        return AjaxResult.success(roleChain==null?new RoleChainEntity():roleChain) ;
    }

    /**
     * 保存角色工作流
     * @param entity
     * @return
     */
    @PostMapping("/saveRoleChainInfo")
    public AjaxResult saveRoleChainInfo(@RequestBody RoleChainEntity entity , String roleId){
        service.saveRoleChainInfo(entity , roleId) ;
        return ok() ;
    }

    @Override
    public IIndustryRoleService getFeign() {
        return this.service;
    }
}