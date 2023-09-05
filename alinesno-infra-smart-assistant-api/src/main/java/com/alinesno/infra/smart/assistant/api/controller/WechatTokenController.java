package com.alinesno.infra.smart.assistant.api.controller;

import com.alinesno.infra.common.web.adapter.utils.StringUtils;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/smart/assistant/wechat")
public class WechatTokenController {

    private static final Logger log = LoggerFactory.getLogger(WechatTokenController.class);

    //微信对接
    @Autowired
    private WxMpService wxService;

    @CrossOrigin
    @GetMapping("/token")
    public String token(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("wechat message：[{}, {}, {}, {}]", signature,timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }
}
