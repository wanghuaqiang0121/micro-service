package org.module.sign.dao;

import java.util.List;

import org.module.sign.domain.UserService;
import org.service.core.dao.IBaseMapper;

public interface UserServiceMapper extends IBaseMapper<UserService> {
    int deleteByPrimaryKey(Integer id);

    int insert(UserService record);

    UserService selectByPrimaryKey(Integer id);

    List<UserService> selectAll();

    int updateByPrimaryKey(UserService record);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userService
	* @return 
	* @date 2018年3月29日
	* @version 1.0
	* @description 修改次数
	*/
	int updateLockTime(UserService userService);
}