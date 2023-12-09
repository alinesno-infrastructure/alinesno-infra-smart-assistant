import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";


// 接口配置项
var prefix = '/v1/smart/assistant/dingtalk/robot/' ;
var managerUrl = {
  chatMessage : prefix +"chatMessage" ,
}

// 运行角色流程
export function chatMessage(businessId) {
  return request({
    url: managerUrl.chatMessage + '?businessId=' + parseStrEmpty(businessId),
    method: 'get'
  })
}
