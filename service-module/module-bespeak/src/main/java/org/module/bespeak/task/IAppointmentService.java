package org.module.bespeak.task;

import org.module.bespeak.domain.ServiceTimeout;
import org.service.core.api.JsonApi;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${service.task.delay}",fallback = AppointmentServiceFallback.class)
public interface IAppointmentService {

	@RequestMapping(value = { "/service/timeout/send" }, method = RequestMethod.POST)
	JsonApi sendTimeout(@RequestBody ServiceTimeout serviceTimeout);
}
