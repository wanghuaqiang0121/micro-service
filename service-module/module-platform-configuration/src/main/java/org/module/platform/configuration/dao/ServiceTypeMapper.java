package org.module.platform.configuration.dao;

import org.apache.ibatis.annotations.Mapper;
import org.module.platform.configuration.domain.ServiceType;
import org.service.core.dao.IBaseMapper;
@Mapper
public interface ServiceTypeMapper extends IBaseMapper<ServiceType> {

}