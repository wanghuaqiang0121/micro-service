package org.module.user.task;

import org.module.user.domain.WechatApi;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.springframework.stereotype.Component;
@Component
public class WeChatServiceFallback implements IWeChatService {


	@Override
	public JsonApi insertWechatNews(WechatApi wechatApi) {
		return new JsonApi(ApiCode.TIMEOUT);
	}

}
