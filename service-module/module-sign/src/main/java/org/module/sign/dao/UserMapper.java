package org.module.sign.dao;

import java.util.Map;

import org.module.sign.domain.User;
import org.service.core.dao.IBaseMapper;

public interface UserMapper extends IBaseMapper<User> {
	Map<String, Object> getWechatRepeat(User user);
	int bindWechat(User user);
	Map<String, Object> getUserByWechat(User user);
	int updateUserWechat(User user);
}
