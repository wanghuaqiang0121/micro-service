package org.module.user.task;

import org.module.user.domain.Sms;
import org.service.core.api.JsonApi;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${service.task.delay}",fallback = SmsServiceFallback.class)
public interface ISmsService {

	@RequestMapping(value = { "/sms/send" }, method = RequestMethod.POST)
	JsonApi send(@RequestBody Sms sms);
}
