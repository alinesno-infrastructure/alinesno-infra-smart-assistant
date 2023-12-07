package com.alinesno.infra.smart.assistant.role.context;

import com.alinesno.infra.smart.brain.api.reponse.TaskContentDto;
import com.yomahub.liteflow.slot.DefaultContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 自定义上下文
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleChainContext extends DefaultContext {

    private String businessId ;  // 业务ID
    private String promptId ;  // 指令标识
    private String userContent ; // 用户要求
    private Map<String , Object> params ; // 请求参数

    private TaskContentDto assistantContent ; // 机器人回复

}
