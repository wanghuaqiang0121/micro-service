package org.module.inquiry.task;

import org.module.inquiry.domain.ServiceTimeout;
import org.service.core.api.JsonApi;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${service.task.delay}",fallback = InquiryServiceFallback.class)
public interface IInquiryService {
	
	@PostMapping(value = { "/service/timeout/send" })
	public JsonApi sendTimeout(@RequestBody ServiceTimeout serviceTimeout);
}