package org.module.bespeak.dao;

import org.module.bespeak.domain.UserService;
import org.service.core.dao.IBaseMapper;

public interface UserServiceMapper extends IBaseMapper<UserService> {

	int lockTime(UserService userService);

	
}