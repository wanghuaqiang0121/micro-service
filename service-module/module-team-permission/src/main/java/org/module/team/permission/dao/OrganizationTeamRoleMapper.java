package org.module.team.permission.dao;

import java.util.List;
import org.module.team.permission.domain.OrganizationTeamRole;
import org.service.core.dao.IBaseMapper;

public interface OrganizationTeamRoleMapper extends IBaseMapper<OrganizationTeamRole> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTeamRole record);

    OrganizationTeamRole selectByPrimaryKey(Integer id);

    List<OrganizationTeamRole> selectAll();

    int updateByPrimaryKey(OrganizationTeamRole record);
}