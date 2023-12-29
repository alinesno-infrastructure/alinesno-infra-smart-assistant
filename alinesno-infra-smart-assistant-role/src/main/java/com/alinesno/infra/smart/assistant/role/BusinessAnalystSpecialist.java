package com.alinesno.infra.smart.assistant.role;

import com.alinesno.infra.smart.assistant.role.context.RoleChainContext;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 需求分析专家
 */
@Slf4j
@Component
public class BusinessAnalystSpecialist extends PlatformExpert {

    private static final String STEP_01 = "mOO5Fq5s" ;
    private static final String STEP_02 = "V5yZ1l3H" ;
    private static final String STEP_03 = "RgPSt3oS" ;
    private static final String STEP_04 = "3hmyCqhJ" ;
    private static final String STEP_05 = "wbqZCMp8" ;


    @LiteflowComponent(value = "BA_STEP_01" , name="需求分析_需求文档分析")
    public class BA_STEP_01 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            String businessId = generatorId() ;

            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , STEP_01);

            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    @LiteflowComponent(value = "BA_STEP_02" , name="需求分析_项目介绍分析")
    public class BA_STEP_02 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            String businessId = generatorId() ;

            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , STEP_02);

            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    @LiteflowComponent(value = "BA_STEP_03" , name="需求分析_项目功能分析")
    public class BA_STEP_03 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            String businessId = generatorId() ;

            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , STEP_03);

            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    @LiteflowComponent(value = "BA_STEP_04" , name="需求分析_项目功能细化分析")
    public class BA_STEP_04 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            String businessId = generatorId() ;

            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , STEP_04);

            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    @LiteflowComponent(value = "BA_STEP_05" , name="需求分析_项目非功能性")
    public class BA_STEP_05 extends NodeComponent {

        @Override
        public void process() throws Exception {
            RoleChainContext roleContext = this.getContextBean(RoleChainContext.class) ;
            String businessId = generatorId() ;

            Map<String , Object> params = this.getRequestData();
            brainRemoteService.chatTask(params , businessId , STEP_05);

            roleContext.setBusinessId(businessId);
            roleContext.setUserContent(params.get("label1").toString());
        }
    }

    @LiteflowComponent(value = "BA_STEP_AGG" , name="需求分析_聚合成文档")
    public class BA_STEP_AGG extends NodeComponent {

        @Override
        public void process() throws Exception {

        }
    }


}
