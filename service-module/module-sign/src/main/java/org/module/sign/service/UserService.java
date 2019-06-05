package org.module.sign.service;

import java.util.Map;

import org.module.sign.dao.UserMapper;
import org.module.sign.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public Map<String, Object> getOne(User user) {
		return userMapper.getOne(user);
	}

	
}
