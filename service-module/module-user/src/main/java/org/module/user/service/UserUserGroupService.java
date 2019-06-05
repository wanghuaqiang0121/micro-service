package org.module.user.service;

import java.util.List;
import java.util.Map;

import org.module.user.dao.UserUserGroupMapper;
import org.module.user.domain.UserGroup;
import org.module.user.domain.UserUserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUserGroupService {

	@Autowired
	private UserUserGroupMapper userUserGroupMapper;

	public Map<String, Object> getRepeat(UserUserGroup userUserGroup) {
		return userUserGroupMapper.getRepeat(userUserGroup);
	}

	public List<Map<String, Object>> getList(UserUserGroup baseUserUserGroup) {
		return userUserGroupMapper.getList(baseUserUserGroup);
	}

	public int insert(UserUserGroup userUserGroup) {
		return userUserGroupMapper.insert(userUserGroup);
	}

	public int delete(UserUserGroup userUserGroup) {
		return userUserGroupMapper.delete(userUserGroup);
	}
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param userUserGroup
	 * @return
	 * @date 2018年12月26日
	 * @version 1.0
	 * @description 查询用户成员
	 */
	public List<Map<String, Object>> getUserMember(UserUserGroup userUserGroup){
		return userUserGroupMapper.getUserMember(userUserGroup);
	}
	
	public Map<String, Object> getUsers(UserUserGroup userUserGroup){
		return userUserGroupMapper.getUsers(userUserGroup);
		
	}

	public Map<String, Object> getUserBelongToGroup(UserGroup userGroup) {
		return userUserGroupMapper.getUserBelongToGroup(userGroup);
	}
}
