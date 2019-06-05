package org.module.bone.age.service;

import java.util.Map;

import org.module.bone.age.dao.UserUserGroupMapper;
import org.module.bone.age.domain.UserUserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUserGroupService {

	@Autowired
	private UserUserGroupMapper userUserGroupMapper;

	public int insert(UserUserGroup userUserGroup) {
		return userUserGroupMapper.insert(userUserGroup);
	}

	public Map<String, Object> getRepeat(UserUserGroup userUserGroup) {
		return userUserGroupMapper.getRepeat(userUserGroup);
	}
}
