package org.module.user.service;

import java.util.List;
import java.util.Map;

import org.module.user.dao.UserMapper;
import org.module.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public int insert(User user) {
		return userMapper.insert(user);
	}

	public Map<String, Object> getRepeat(User user) {
		return userMapper.getRepeat(user);
	}

	public Map<String, Object> getWechatRepeat(User user) {
		return userMapper.getWechatRepeat(user);
	}

	public int bindWechat(User user) {
		return userMapper.bindWechat(user);
	}

	public Map<String, Object> getUserByWechat(User user) {
		return userMapper.getUserByWechat(user);
	}

	public int updateUserWechat(User user) {
		return userMapper.updateUserWechat(user);
	}

	public int update(User user) {
		return userMapper.update(user);
	}

	public Map<String, Object> getOne(User user) {
		return userMapper.getOne(user);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param user
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description 根据电话号码查询用户
	*/
	public Map<String, Object> getUserByPhone(User user) {
		return userMapper.getUserByPhone(user);
	}

	public Map<String, Object> getLoginMsg(User user) {
		return userMapper.getLoginMsg(user);
	}

	public Map<String, Object> getWechat(User user) {
		return userMapper.getWechat(user);
	}

	public List<Map<String, Object>> getGroupList(User user) {
		return userMapper.getGroupList(user);
	}
}
