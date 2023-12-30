package com.alinesno.infra.smart.assistant.queue.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.smart.assistant.entity.MessageQueueConfigEntity;
import com.alinesno.infra.smart.assistant.entity.MessageQueueEntity;
import com.alinesno.infra.smart.assistant.enums.MessageStatus;
import com.alinesno.infra.smart.assistant.queue.mapper.MessageQueueMapper;
import com.alinesno.infra.smart.assistant.service.IIndustryRoleService;
import com.alinesno.infra.smart.assistant.service.IMessageQueueConfigService;
import com.alinesno.infra.smart.assistant.service.IMessageQueueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MessageQueueServiceImpl extends IBaseServiceImpl<MessageQueueEntity, MessageQueueMapper> implements IMessageQueueService {

    @Autowired
    private IMessageQueueConfigService queueConfigService ;

    @Autowired
    private IIndustryRoleService industryRoleService ;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 添加消息任务先判断当前队列是否已经满，没有则执行
     * @param messageQueue
     */
    @SneakyThrows
    @Override
    public void addMessage(MessageQueueEntity messageQueue) {

        if(queryQueueStatus(messageQueue.getBusinessId())){

            messageQueue.setStatus(MessageStatus.SENT.getValue());
            this.save(messageQueue) ;

            // 进行任务构建
            long agentId = messageQueue.getAgentId() ;
            String content = messageQueue.getContent() ;

            Map<String , Object> params = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {});
            log.debug("agentId = {} , params = {}" , agentId , params);

            industryRoleService.runRoleChainByRoleId(params , agentId+"" , null) ;

        }else{

            messageQueue.setStatus(MessageStatus.PENDING.getValue());
            this.save(messageQueue) ;

        }
    }

    @SneakyThrows
    @Override
    public void runMessage(MessageQueueEntity messageQueue) {

        // 进行任务构建
        long agentId = messageQueue.getAgentId() ;
        String content = messageQueue.getContent() ;

        Map<String , Object> params = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {});
        log.debug("agentId = {} , params = {}" , agentId , params);

        industryRoleService.runRoleChainByRoleId(params , agentId+"" , null) ;
    }

    @Override
    public MessageQueueEntity queryMessage(String businessId) {
        return null;
    }

    @Override
    public boolean queryQueueStatus(String businessId) {

        MessageQueueConfigEntity config = queueConfigService.queryConfigByBusinessId(null) ;
        int maxQueueSize = config.getMaxQueueSize() ;  // 最大任务队列

        LambdaQueryWrapper<MessageQueueEntity> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(MessageQueueEntity::getStatus , MessageStatus.PENDING.getValue()) ;
        long pendingCount = count(wrapper) ;

        return pendingCount < maxQueueSize ;
    }

    @Override
    public void updateAssistantContent(String businessId, String resultMap) {

        LambdaQueryWrapper<MessageQueueEntity> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(MessageQueueEntity::getBusinessId , businessId) ;
        MessageQueueEntity entity = this.getOne(wrapper) ;

        entity.setStatus(MessageStatus.SUCCESS.getValue());
        entity.setAssistantContent(resultMap);
        update(entity) ;
    }
}
