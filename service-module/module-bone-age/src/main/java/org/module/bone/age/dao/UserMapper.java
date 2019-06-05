package org.module.bone.age.dao;

import java.util.List;
import java.util.Map;

import org.module.bone.age.domain.User;
import org.service.core.dao.IBaseMapper;

public interface UserMapper extends IBaseMapper<User> {

	List<Map<String, Object>> getSearchMember(User user);

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param user
	 * @return
	 * @date 2018年8月13日
	 * @version 1.0
	 * @description 判断电话号码是否重复
	 */
	Map<String, Object> getUserByPhone(User user);
}