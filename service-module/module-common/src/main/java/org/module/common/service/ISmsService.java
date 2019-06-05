package org.module.common.service;

import org.module.common.domain.Sms;
import org.service.core.api.JsonApi;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年5月3日
 * @Version
 * @Description 短信推送
 */
@FeignClient(value = "${feign.client.task.delay}", fallback = SmsServiceFallback.class)
public interface ISmsService {

	@RequestMapping(value = { "/sms/send" }, method = RequestMethod.POST)
	JsonApi send(@RequestBody Sms sms);

}
