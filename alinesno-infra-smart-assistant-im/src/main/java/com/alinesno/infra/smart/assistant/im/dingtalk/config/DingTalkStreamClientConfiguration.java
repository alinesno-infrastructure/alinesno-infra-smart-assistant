package com.alinesno.infra.smart.assistant.im.dingtalk.config;//package com.alinesno.infra.smart.assistant.im.dingtalk.config;
//
//import com.alinesno.infra.smart.assistant.im.dingtalk.callback.AIGraphPluginCallbackListener;
//import com.alinesno.infra.smart.assistant.im.dingtalk.callback.ChatBotCallbackListener;
//import com.dingtalk.open.app.api.OpenDingTalkClient;
//import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
//import com.dingtalk.open.app.api.callback.DingTalkStreamTopics;
//import com.dingtalk.open.app.api.security.AuthClientCredential;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author zeymo
// */
//@Configuration
//public class DingTalkStreamClientConfiguration {
//
//    @Value("${alinesno.infra.smart.dingtalk.app-key:}")
//    private String clientId;
//
//    @Value("${alinesno.infra.smart.dingtalk.app-secret:}")
//    private String clientSecret;
//
//    /**
//     * 配置OpenDingTalkClient客户端并配置初始化方法(start)
//     *
//     * @param chatBotCallbackListener
//     * @param aiGraphPluginCallbackListener
//     * @return
//     * @throws Exception
//     */
//    @Bean(initMethod = "start")
//    public OpenDingTalkClient configureStreamClient(@Autowired ChatBotCallbackListener chatBotCallbackListener,
//                                                    @Autowired AIGraphPluginCallbackListener aiGraphPluginCallbackListener) throws Exception {
//        // init stream client
//        return OpenDingTalkStreamClientBuilder.custom()
//                //配置应用的身份信息, 企业内部应用分别为appKey和appSecret, 三方应用为suiteKey和suiteSecret
//                .credential(new AuthClientCredential(clientId, clientSecret))
//                //注册机器人回调
//                .registerCallbackListener(DingTalkStreamTopics.BOT_MESSAGE_TOPIC, chatBotCallbackListener)
//                //注册graph api回调
//                .registerCallbackListener(DingTalkStreamTopics.GRAPH_API_TOPIC, aiGraphPluginCallbackListener).build();
//    }
//}