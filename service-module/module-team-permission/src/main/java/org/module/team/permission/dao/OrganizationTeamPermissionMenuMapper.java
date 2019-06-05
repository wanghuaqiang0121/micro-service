package org.module.team.permission.dao;

import java.util.List;
import java.util.Map;

import org.module.team.permission.domain.OrganizationTeamPermissionMenu;
import org.service.core.dao.IBaseMapper;

public interface OrganizationTeamPermissionMenuMapper extends IBaseMapper<OrganizationTeamPermissionMenu> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTeamPermissionMenu record);

    OrganizationTeamPermissionMenu selectByPrimaryKey(Integer id);

    List<OrganizationTeamPermissionMenu> selectAll();

    int updateByPrimaryKey(OrganizationTeamPermissionMenu record);

	List<Map<String, Object>> teamPermissionHaveAndNotHaveMenus(
			OrganizationTeamPermissionMenu organizationTeamPermissionMenu);
}