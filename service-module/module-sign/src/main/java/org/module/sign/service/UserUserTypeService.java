package org.module.sign.service;

import java.util.List;


import org.module.sign.dao.UserUserTypeMapper;
import org.module.sign.domain.UserUserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUserTypeService {
	@Autowired
	private UserUserTypeMapper userTypeMapper;
	
	public int batchInsert(List<UserUserType> userUserType){
		return userTypeMapper.batchInsert(userUserType);
	}
	
	public int delete(UserUserType userUserType){
		return userTypeMapper.delete(userUserType);
	}
}
