package org.module.team.permission.dao;

import java.util.List;
import java.util.Map;

import org.module.team.permission.domain.OrganizationTeamRolePermission;
import org.service.core.dao.IBaseMapper;

public interface OrganizationTeamRolePermissionMapper extends IBaseMapper<OrganizationTeamRolePermission> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTeamRolePermission record);

    OrganizationTeamRolePermission selectByPrimaryKey(Integer id);

    List<OrganizationTeamRolePermission> selectAll();

    int updateByPrimaryKey(OrganizationTeamRolePermission record);

	List<Map<String, Object>> teamRoleHaveAndNotHavePermissions(
			OrganizationTeamRolePermission organizationTeamRolePermission);
}