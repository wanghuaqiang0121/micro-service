package org.module.user.task;

import org.module.user.domain.Sms;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.springframework.stereotype.Component;
@Component
public class SmsServiceFallback implements ISmsService {

	@Override
	public JsonApi send(Sms sms) {
		return new JsonApi(ApiCode.TIMEOUT);
	}

}
