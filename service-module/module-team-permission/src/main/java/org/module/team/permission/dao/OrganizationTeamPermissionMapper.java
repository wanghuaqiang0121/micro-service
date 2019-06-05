package org.module.team.permission.dao;

import java.util.List;
import org.module.team.permission.domain.OrganizationTeamPermission;
import org.service.core.dao.IBaseMapper;

public interface OrganizationTeamPermissionMapper extends IBaseMapper<OrganizationTeamPermission> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTeamPermission record);

    OrganizationTeamPermission selectByPrimaryKey(Integer id);

    List<OrganizationTeamPermission> selectAll();

    int updateByPrimaryKey(OrganizationTeamPermission record);
}