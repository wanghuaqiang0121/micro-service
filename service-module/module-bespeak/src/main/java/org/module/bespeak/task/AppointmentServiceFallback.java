package org.module.bespeak.task;

import org.module.bespeak.domain.ServiceTimeout;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.springframework.stereotype.Component;

@Component
public class AppointmentServiceFallback implements IAppointmentService{

	@Override
	public JsonApi sendTimeout(ServiceTimeout serviceTimeout) {
		return new JsonApi(ApiCode.TIMEOUT);
	}
}
