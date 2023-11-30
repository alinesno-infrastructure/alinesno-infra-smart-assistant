package com.alinesno.infra.smart.assistant.role;

import com.alinesno.infra.smart.assistant.role.bean.PlatformExpert;
import com.alinesno.infra.smart.assistant.role.bean.PromptMessage;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 示例的Chain配置
 */
@Slf4j
@Component
public class DemoChainExpert extends PlatformExpert implements CommandLineRunner {

    @Resource
    private FlowExecutor flowExecutor;

    @SneakyThrows
    @Override
    public void performSpecializedTask(List<PromptMessage> prompts) {

        while(true){

            LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
            DefaultContext context = response.getFirstContextBean();

            System.out.println("----->>>>>>>>>>> " + JsonUtil.toJsonString(context.getData("student")));
            System.out.println("----->>>>>>>>>>> " + JsonUtil.toJsonString(context.getData("s2")));

            if (response.isSuccess()){
                log.info("执行成功");
            }else{
                log.info("执行失败");
            }

            Thread.sleep(10*1000);
        }

    }

    @Override
    public void run(String... args) throws Exception {
       this.performSpecializedTask(null);
    }


    @LiteflowComponent("a")
    public class ACmp extends NodeComponent {

        @Override
        public void process() {
            System.out.println("ACmp executed!");
        }
    }

    @LiteflowComponent("b")
    public class BCmp extends NodeComponent {

        @Override
        public void process() {
            System.out.println("BCmp executed!");
        }

    }

    @LiteflowComponent("c")
    public class CCmp extends NodeComponent {

        @Override
        public void process() {
            System.out.println("CCmp executed!");
        }

    }
}
