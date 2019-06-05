package org.module.team.permission.service;

import java.util.List;
import java.util.Map;

import org.module.team.permission.dao.OrganizationTeamRolePermissionMapper;
import org.module.team.permission.domain.OrganizationTeamRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamRolePermissionService {

	@Autowired
	private OrganizationTeamRolePermissionMapper mapper;

	public Map<String, Object> getRepeat(OrganizationTeamRolePermission organizationTeamRolePermission) {
		return mapper.getRepeat(organizationTeamRolePermission);
	}

	public int insert(OrganizationTeamRolePermission organizationTeamRolePermission) {
		return mapper.insert(organizationTeamRolePermission);
	}

	public int delete(OrganizationTeamRolePermission organizationTeamRolePermission) {
		return mapper.delete(organizationTeamRolePermission);
	}

	public List<Map<String, Object>> teamRoleHaveAndNotHavePermissions(
			OrganizationTeamRolePermission organizationTeamRolePermission) {
		return mapper.teamRoleHaveAndNotHavePermissions(organizationTeamRolePermission);
	}
	
}
