package com.alinesno.infra.smart.assistant.role;

import com.alinesno.infra.smart.assistant.im.dto.NoticeDto;
import com.alinesno.infra.smart.assistant.role.context.RoleChainContext;
import com.alinesno.infra.smart.brain.api.reponse.TaskContentDto;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 技术团队培训专家业务处理流程 <br/>
 * (集成串行的示例)
 */
@Slf4j
@Service
public class TeamTrainingSpecialist extends PlatformExpert {

    @LiteflowComponent(value = "team_train" + GEN , name="生成试题请求")
    public class TeamTrainGenerator extends NodeComponent {

        @Override
        public void process() {

            // 获取上下文
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;

            // 通过上下文传入
            String promptId = "0GSheQ31" ;
            String businessId = generatorId() ;

            // 获取到参数
            Map<String , Object> params = this.getRequestData();
            brainRemoteService.commitTask(params , businessId , promptId);

            log.debug("params = {}" , params);
            System.out.println("TeamTrainGeneratorContent executed!");

            // 设置上下文
            roleContext.setBusinessId(businessId);
            roleContext.setPromptId(promptId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    // 根据情况，进行并行处理
    @LiteflowComponent(value = "team_train" + PARSE , name="解析试题内容并发送到试题库")
    public class TeamTrainParse extends NodeComponent {

        @Override
        public void process() {

            // 获取上下文
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;

            String businessId = roleContext.getBusinessId() ;  // 获取到业务Id
            String promptId = roleContext.getPromptId() ;

            TaskContentDto content = brainRemoteService.chatContent(businessId);

            log.debug("promptId = {} , content = {}" , promptId , content);
            System.out.println("TeamTrainGeneratorContent executed!");

            // 设置上下文
            roleContext.setAssistantContent(content);
        }
    }

    @LiteflowComponent(value = "team_train" + APPLY , name="发送试题到指定通知服务")
    public class TeamTrainApply extends NodeComponent {

        @Override
        public void process() {
            // 获取上下文
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;

            log.debug("roleContext = {}" , roleContext);

            NoticeDto noticeDto = new NoticeDto() ;

            noticeDto.setBusinessId(roleContext.getBusinessId());
            noticeDto.setTaskName(roleContext.getUserContent());
            noticeDto.setApplyLink("http://portal.infra.linesno.com");
            noticeDto.setEnv("测试环境");
            noticeDto.setUsageTime("1分23秒231");
            noticeDto.setFinishTime("2023-12-2 01:12:32");
            noticeDto.setTaskStatus("完成");

            dingtalkNoticeService.noticeAgent(noticeDto);

            System.out.println("TeamTrainGeneratorContent executed!");
        }
    }
}
