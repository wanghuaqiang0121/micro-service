package org.module.organization.team.dao;

import org.apache.ibatis.annotations.Mapper;
import org.module.organization.team.domain.Organization;
import org.service.core.dao.IBaseMapper;
@Mapper
public interface OrganizationMapper extends IBaseMapper<Organization> {
    int deleteByPrimaryKey(Integer id);
}