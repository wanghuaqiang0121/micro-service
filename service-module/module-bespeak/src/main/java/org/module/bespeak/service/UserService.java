package org.module.bespeak.service;

import java.util.Map;

import javax.annotation.Resource;

import org.module.bespeak.dao.UserMapper;
import org.module.bespeak.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Resource
	private UserMapper userMapper;

	public Map<String, Object> getWechat(User user) {
		return userMapper.getWechat(user);
	}
	
}
