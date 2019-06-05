package org.module.sign.dao;

import java.util.List;

import org.module.sign.domain.UserUserType;
import org.service.core.dao.IBaseMapper;

public interface UserUserTypeMapper extends IBaseMapper<UserUserType>{
	
	public int batchInsert(List<UserUserType> userUserType);

}
