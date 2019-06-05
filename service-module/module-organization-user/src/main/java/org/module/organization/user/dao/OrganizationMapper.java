package org.module.organization.user.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.module.organization.user.domain.Organization;
import org.service.core.dao.IBaseMapper;
@Mapper
public interface OrganizationMapper extends IBaseMapper<Organization> {
    int deleteByPrimaryKey(Integer id);

	Map<String, Object> getSmsInfo(Organization organization);
}