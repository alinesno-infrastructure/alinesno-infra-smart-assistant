package com.alinesno.infra.smart.assistant.role;

import cn.hutool.core.date.DateUtil;
import com.alinesno.infra.smart.assistant.im.dto.NoticeDto;
import com.alinesno.infra.smart.assistant.role.context.RoleChainContext;
import com.alinesno.infra.smart.brain.api.reponse.TaskContentDto;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 技术团队培训专家业务处理流程 <br/>
 * (集成串行的示例)
 */
@Slf4j
@Component
public class TeamTrainingSpecialist extends PlatformExpert {

    private static final String promptId = "0GSheQ31" ;

    @LiteflowComponent(value = "team_train" + GEN , name="生成试题请求")
    public class TeamTrainGenerator extends NodeComponent {

        @Override
        public void process() {

            // 获取上下文
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            roleContext.setStartTime(System.currentTimeMillis());

            // 通过上下文传入
            String businessId = generatorId() ;

            // 获取到参数
            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , promptId);

            log.debug("params = {}" , params);
            System.out.println("TeamTrainGeneratorContent executed!");

            // 设置上下文
            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    // 根据情况，进行并行处理
    @LiteflowComponent(value = "team_train" + PARSE , name="解析试题内容并发送到试题库")
    public class TeamTrainParse extends NodeComponent {

        @SneakyThrows
        @Override
        public void process() {

            // 默认等待生成时间
            Thread.sleep(defaultSleepTime);

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
            TaskContentDto taskContentDto = roleContext.getAssistantContent() ;

            long startTime = roleContext.getStartTime() ;
            long endTime = System.currentTimeMillis() ;

            log.debug("roleContext = {}" , roleContext);

            NoticeDto noticeDto = roleContext.getNoticeDto() ;

            noticeDto.setBusinessId(roleContext.getBusinessId());
            noticeDto.setTaskName(roleContext.getUserContent());
            noticeDto.setApplyLink("http://portal.infra.linesno.com");
            noticeDto.setEnv("测试环境");
            noticeDto.setUsageTime(formatTime(startTime , endTime));
            noticeDto.setFinishTime(DateUtil.formatDateTime(new Date()));
            noticeDto.setTaskStatus(taskContentDto.getTaskStatus() == 2 ?"完成":"失败");

            dingtalkNoticeService.noticeAgent(noticeDto);
            System.out.println("TeamTrainGeneratorContent executed!");
        }
    }

    public static String formatTime(long startTime, long endTime) {
        long elapsedTime = endTime - startTime;

        long hours = elapsedTime / 3600000;
        long remainingMillis = elapsedTime % 3600000;
        long minutes = remainingMillis / 60000;
        remainingMillis %= 60000;
        long seconds = remainingMillis / 1000;
        long milliseconds = remainingMillis % 1000;

        StringBuilder formattedTime = new StringBuilder();

        if (hours > 0) {
            formattedTime.append(hours).append("时");
        }
        if (minutes > 0 || hours > 0) {
            formattedTime.append(minutes).append("分");
        }
        if (seconds > 0 || minutes > 0 || hours > 0) {
            formattedTime.append(seconds).append("秒");
        }
        formattedTime.append(milliseconds);

        return formattedTime.toString();
    }
}
