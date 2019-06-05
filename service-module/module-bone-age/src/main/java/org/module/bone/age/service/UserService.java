package org.module.bone.age.service;

import java.util.List;
import java.util.Map;

import org.module.bone.age.dao.UserMapper;
import org.module.bone.age.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public List<Map<String, Object>> getSearchMember(User user) {
		return userMapper.getSearchMember(user);
	}

	public Map<String, Object> getUserByPhone(User user) {
		return userMapper.getUserByPhone(user);
	}

	public int insert(User user) {
		return userMapper.insert(user);
	}

	public Map<String, Object> getOne(User user) {
		return userMapper.getOne(user);
	}

	public int update(User user) {
		return userMapper.update(user);
	}
}
