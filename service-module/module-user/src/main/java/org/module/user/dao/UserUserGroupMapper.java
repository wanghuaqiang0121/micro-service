package org.module.user.dao;

import java.util.List;
import java.util.Map;

import org.module.user.domain.UserGroup;
import org.module.user.domain.UserUserGroup;
import org.service.core.dao.IBaseMapper;

public interface UserUserGroupMapper extends IBaseMapper<UserUserGroup> {
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param userUserGroup
	 * @return
	 * @date 2018年12月26日
	 * @version 1.0
	 * @description 查询用户成员
	 */
	public List<Map<String, Object>> getUserMember(UserUserGroup userUserGroup);
	
	public Map<String, Object> getUsers(UserUserGroup userUserGroup);

	public Map<String, Object> getUserBelongToGroup(UserGroup userGroup);
}