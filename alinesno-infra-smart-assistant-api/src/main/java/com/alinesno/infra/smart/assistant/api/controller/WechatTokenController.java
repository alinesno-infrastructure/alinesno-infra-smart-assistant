package com.alinesno.infra.smart.assistant.api.controller;

import com.alinesno.infra.common.web.adapter.utils.StringUtils;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 这是一个微信Token控制器类，用于处理微信对接接口和验证微信消息的合法性。
 作者：luoxiaodong
 版本：1.0.0
 */
@RestController
@RequestMapping(value = "/v1/smart/assistant/wechat")
public class WechatTokenController {

    private static final Logger log = LoggerFactory.getLogger(WechatTokenController.class);

    @Autowired
    private WxMpService wxService;

    /**
     * 微信对接接口，用于验证微信消息的合法性。
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return 如果验证通过，返回随机字符串echostr；否则返回"非法请求"
     */
    @CrossOrigin
    @GetMapping("/token")
    public String token(@RequestParam(name = "signature", required = false) String signature,
                        @RequestParam(name = "timestamp", required = false) String timestamp,
                        @RequestParam(name = "nonce", required = false) String nonce,
                        @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("wechat message：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }
}
