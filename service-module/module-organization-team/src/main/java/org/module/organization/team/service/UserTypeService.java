package org.module.organization.team.service;

import java.util.List;
import java.util.Map;

import org.module.organization.team.dao.UserTypeMapper;
import org.module.organization.team.domain.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Li.ZhiHao
 * @Date 2018/3/19
 * @Version
 * @Description
 */
@Service
public class UserTypeService {

    @Autowired
    private UserTypeMapper userTypeMapper;


	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userType
	* @return 
	* @date 2018年4月2日
	* @version 1.0
	* @description 人群类型列表
	*/
	public List<Map<String, Object>> getList(UserType userType) {
		return userTypeMapper.getList(userType);
	}

}
