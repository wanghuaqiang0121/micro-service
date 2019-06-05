package org.module.sign.service;

import org.module.sign.dao.UserSignLogMapper;
import org.module.sign.domain.UserSignLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSignLogService {

	@Autowired
	private UserSignLogMapper signLogMapper;

	public int insert(UserSignLog userSignLog) {
		return signLogMapper.insert(userSignLog);
	}
	
}
