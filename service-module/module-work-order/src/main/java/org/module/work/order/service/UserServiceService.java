package org.module.work.order.service;

import java.util.Map;

import org.module.work.order.dao.UserServiceMapper;
import org.module.work.order.domain.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceService {

	@Autowired
	private UserServiceMapper userServiceMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userService
	* @return 
	* @date 2018年3月27日
	* @version 1.0
	* @description 根据服务类型id查询用户服务详情 
	*/
	public Map<String, Object> getOne(UserService userService) {
		return userServiceMapper.getOne(userService);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userService
	* @return 
	* @date 2018年3月29日
	* @version 1.0
	* @description  修改用户服务
	*/
	public int updateLockTime(UserService userService) {
		return userServiceMapper.updateLockTime(userService);
	}
	
}
