package org.module.bone.age.service;

import org.module.bone.age.dao.UserGroupMapper;
import org.module.bone.age.domain.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {

	@Autowired
	private UserGroupMapper userGroupMapper;

	public int insert(UserGroup userGroup) {
		return userGroupMapper.insert(userGroup);
	}
}
