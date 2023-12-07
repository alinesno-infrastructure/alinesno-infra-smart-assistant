package com.alinesno.infra.smart.assistant.role;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 技术团队培训专家业务处理流程
 */
@Slf4j
@Service
public class TeamTrainingSpecialist extends PlatformExpert {

    private static final String PROMPT_ID = "" ;

    @LiteflowComponent(value = "team_train" + GEN , name="生成试题请求")
    public class TeamTrainGenerator extends NodeComponent {

        @Override
        public void process() {

            long businessId = generatorId() ;

            // 获取到参数
            Map<String , Object> params = this.getRequestData();
            params.put("label1", "设计出关于centos的考核题目") ;

            brainRemoteService.commitTask(params , businessId , PROMPT_ID);

            log.debug("params = {}" , params);
            System.out.println("TeamTrainGeneratorContent executed!");
        }
    }

    @LiteflowComponent(value = "team_train" + PARSE , name="解析试题内容并发送到试题库")
    public class TeamTrainParse extends NodeComponent {

        @Override
        public void process() {

            String businessId = "" ;  // 获取到业务Id
            String content = brainRemoteService.chatContent(businessId);

            log.debug("content = {}" , content);
            System.out.println("TeamTrainGeneratorContent executed!");
        }
    }

    @LiteflowComponent(value = "team_train" + APPLY , name="发送试题到指定通知服务")
    public class TeamTrainApply extends NodeComponent {

        @Override
        public void process() {
            System.out.println("TeamTrainGeneratorContent executed!");
        }
    }
}
