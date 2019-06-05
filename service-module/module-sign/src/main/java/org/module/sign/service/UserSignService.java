package org.module.sign.service;

import java.util.Map;

import org.module.sign.dao.UserSignMapper;
import org.module.sign.domain.UserSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSignService {

	@Autowired
	private UserSignMapper userSignMapper;

	public int insert(UserSign userSign) {
		return userSignMapper.insert(userSign);
	}

	public Map<String, Object> getOne(UserSign userSign) {
		return userSignMapper.getOne(userSign);
	}

	public int update(UserSign userSign) {
		return userSignMapper.update(userSign);
	}
	
}
