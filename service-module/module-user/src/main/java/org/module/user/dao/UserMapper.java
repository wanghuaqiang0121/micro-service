package org.module.user.dao;

import java.util.List;
import java.util.Map;

import org.module.user.domain.User;
import org.service.core.dao.IBaseMapper;

public interface UserMapper extends IBaseMapper<User> {
	Map<String, Object> getWechatRepeat(User user);

	int bindWechat(User user);

	Map<String, Object> getUserByWechat(User user);

	int updateUserWechat(User user);

	Map<String, Object> getUserByPhone(User user);

	Map<String, Object> getLoginMsg(User user);

	Map<String, Object> getWechat(User user);

	List<Map<String, Object>> getGroupList(User user);
}
