package org.module.team.permission.service;

import java.util.List;
import java.util.Map;

import org.module.team.permission.dao.OrganizationTeamPermissionMenuMapper;
import org.module.team.permission.domain.OrganizationTeamPermissionMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamPermissionMenuService {

	@Autowired
	private OrganizationTeamPermissionMenuMapper organizationTeamPermissionMenuMapper;

	public Map<String, Object> getRepeat(OrganizationTeamPermissionMenu organizationTeamPermissionMenu) {
		return organizationTeamPermissionMenuMapper.getRepeat(organizationTeamPermissionMenu);
	}

	public int insert(OrganizationTeamPermissionMenu organizationTeamPermissionMenu) {
		return organizationTeamPermissionMenuMapper.insert(organizationTeamPermissionMenu);
	}

	public int delete(OrganizationTeamPermissionMenu organizationTeamPermissionMenu) {
		return organizationTeamPermissionMenuMapper.delete(organizationTeamPermissionMenu);
	}

	public List<Map<String, Object>> teamPermissionHaveAndNotHaveMenus(
			OrganizationTeamPermissionMenu organizationTeamPermissionMenu) {
		return organizationTeamPermissionMenuMapper.teamPermissionHaveAndNotHaveMenus(organizationTeamPermissionMenu);
	}
}
