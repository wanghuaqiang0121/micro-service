package org.module.inquiry.task;

import org.module.inquiry.domain.ServiceTimeout;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.springframework.stereotype.Component;

@Component
public class InquiryServiceFallback implements IInquiryService{

	@Override
	public JsonApi sendTimeout(ServiceTimeout serviceTimeout) {
		return new JsonApi(ApiCode.TIMEOUT);
	}
}
