package org.module.common.service;

import java.util.Map;

import org.module.common.dao.WechatUrlMapper;
import org.module.common.domain.WechatUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatUrlService {

	
	@Autowired
	private WechatUrlMapper wechatUrlMapper;

	public Map<String, Object> getOne(WechatUrl wechatUrl) {
		return wechatUrlMapper.getOne(wechatUrl);
	}
}
