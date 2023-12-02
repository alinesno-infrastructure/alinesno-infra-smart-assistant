package com.alinesno.infra.smart.assistant.role;

import com.alinesno.infra.smart.assistant.api.prompt.PromptMessage;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
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

    @Override
    public void run(String... args) throws Exception {
        String chainName = "chain1" ;
        Long chainId = 1L ;
        super.processExpert(null , chainName , chainId);
    }

    @Override
    public void performSpecializedTask(List<PromptMessage> prompts) {

    }

    @LiteflowComponent(value = "a" , name="测试执行节点A")
    public class ACmp extends NodeComponent {

        @Override
        public void process() {
            System.out.println("ACmp executed!");
        }
    }

    @LiteflowComponent(value = "b" , name="测试执行节点B")
    public class BCmp extends NodeComponent {

        @Override
        public void process() {
            System.out.println("BCmp executed!");
        }

    }

    @LiteflowComponent(value = "c" , name="测试执行节点C")
    public class CCmp extends NodeComponent {

        @Override
        public void process() {
            System.out.println("CCmp executed!");
        }

    }
}
