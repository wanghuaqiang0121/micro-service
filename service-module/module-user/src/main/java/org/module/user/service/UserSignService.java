package org.module.user.service;

import java.util.List;
import java.util.Map;

import org.module.user.dao.UserSignMapper;
import org.module.user.domain.UserSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSignService {

	@Autowired
	private UserSignMapper userSignMapper;

	public List<Map<String, Object>> getList(UserSign userSign) {
		return userSignMapper.getList(userSign);
	}
}
