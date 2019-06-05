package org.module.user.task;

import org.module.user.domain.WechatApi;
import org.service.core.api.JsonApi;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${service.task.delay}",fallback = WeChatServiceFallback.class)
public interface IWeChatService {

	@RequestMapping(value = { "/wechat/send" }, method = RequestMethod.POST)
	public JsonApi insertWechatNews(@RequestBody WechatApi wechatApi);
}
