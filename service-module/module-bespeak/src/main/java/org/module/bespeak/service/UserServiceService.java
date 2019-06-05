package org.module.bespeak.service;

import java.util.Map;

import javax.annotation.Resource;

import org.module.bespeak.dao.UserServiceMapper;
import org.module.bespeak.domain.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceService {

	@Resource
	private UserServiceMapper userServiceMapper;

	public int lockTime(UserService userService) {
		return userServiceMapper.lockTime(userService);
	}

	public Map<String, Object> getOne(UserService userService) {
		return userServiceMapper.getOne(userService);
	}


}
