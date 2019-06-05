package org.module.user.service;

import java.util.Map;

import org.module.user.dao.UserGroupMapper;
import org.module.user.domain.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {

	@Autowired
	private UserGroupMapper userGroupMapper;

	public Map<String, Object> getRepeat(UserGroup userGroup) {
		return userGroupMapper.getRepeat(userGroup);
	}

	public Integer insert(UserGroup userGroup) {
		return userGroupMapper.insert(userGroup);
	}
}
