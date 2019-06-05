package org.module.platform.configuration.dao;

import org.apache.ibatis.annotations.Mapper;
import org.module.platform.configuration.domain.UserType;
import org.service.core.dao.IBaseMapper;
@Mapper
public interface UserTypeMapper extends IBaseMapper<UserType> {

}