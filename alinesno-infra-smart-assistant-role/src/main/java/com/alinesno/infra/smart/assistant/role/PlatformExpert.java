package com.alinesno.infra.smart.assistant.role;

import cn.hutool.core.util.IdUtil;
import com.alinesno.infra.smart.assistant.api.prompt.PromptMessage;
import com.alinesno.infra.smart.assistant.chain.IBaseExpertService;
import com.alinesno.infra.smart.assistant.im.dto.NoticeDto;
import com.alinesno.infra.smart.assistant.im.service.IDingtalkNoticeService;
import com.alinesno.infra.smart.assistant.role.service.BrainRemoteService;
import com.alinesno.infra.smart.assistant.service.IMessageQueueService;
import com.alinesno.infra.smart.assistant.service.IRoleChainService;
import com.alinesno.infra.smart.assistant.service.IWorkflowExecutionService;
import com.alinesno.infra.smart.assistant.service.IWorkflowNodeExecutionService;
import com.yomahub.liteflow.core.FlowExecutor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 创建父类 ITExpert 并声明为抽象类
@Slf4j
@Data
public abstract class PlatformExpert implements IBaseExpertService {

    protected static final String GEN = "_gen" ; // 生成内容
    protected static final String PARSE = "_parse" ;  // 解析内容
    protected static final String AGG = "_agg" ;  // 聚合内容
    protected static final String APPLY = "_apply" ;  // 内容审核

    protected int DEFAULT_SLEEP_TIME = 5*1000 ; // 默认生成内容等待时间
    protected int MAX_RETRY_COUNT = 100 ;  // 默认重试次数

    @Value("${alinesno.infra.smart.comment-link}")
    protected String platformUrl ;

    private int defaultThreadPool = 200 ;

    @Autowired
    protected BrainRemoteService brainRemoteService ;

    @Autowired
    protected IDingtalkNoticeService dingtalkNoticeService ;

    @Autowired
    protected IMessageQueueService messageQueueService ;

    @Autowired
    protected FlowExecutor flowExecutor;

    @Autowired
    protected IWorkflowExecutionService workflowExecutionService ;

    @Autowired
    protected IRoleChainService roleChainService ;

    @Autowired
    protected IWorkflowNodeExecutionService workflowNodeExecutionService ;

    private static ExecutorService executor = null ;

    protected ExecutorService getExecutor(){
        if(executor == null){
            // 生成一个固定线程池
            executor = Executors.newFixedThreadPool(defaultThreadPool);
        }
        return executor ;
    }

    // 添加一个公共的方法
    public void displayExpertInformation() {
        System.out.println("This is an IT expert.");
    }

    public void performSpecializedTask(List<PromptMessage> prompts){

    }

    @Override
    public void processExpert(Map<String, Object> params, String chainName , Long chainId, NoticeDto noticeDto){

    }

    /**
     * 保存生成的内容到持久化中
     * @param businessId
     * @param resultMap
     */
    protected void saveToBusinessResult(String businessId , String resultMap){
        messageQueueService.updateAssistantContent(businessId , resultMap) ;
    }

    /**
     * 生成Id
     * @return
     */
    protected String generatorId(){
       return IdUtil.getSnowflakeNextId()+"" ;
    }

}
