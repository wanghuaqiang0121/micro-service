package org.module.team.permission.dao;

import java.util.List;
import java.util.Map;

import org.module.team.permission.domain.OrganizationTeamPermissionOperation;
import org.service.core.dao.IBaseMapper;

public interface OrganizationTeamPermissionOperationMapper extends IBaseMapper<OrganizationTeamPermissionOperation> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTeamPermissionOperation record);

    OrganizationTeamPermissionOperation selectByPrimaryKey(Integer id);

    List<OrganizationTeamPermissionOperation> selectAll();

    int updateByPrimaryKey(OrganizationTeamPermissionOperation record);

	List<Map<String, Object>> teamPermissionHaveAndNotHaveOperations(
			OrganizationTeamPermissionOperation organizationTeamPermissionOperation);
}