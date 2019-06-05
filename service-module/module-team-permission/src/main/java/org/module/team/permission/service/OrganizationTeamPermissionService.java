package org.module.team.permission.service;

import java.util.List;
import java.util.Map;

import org.module.team.permission.dao.OrganizationTeamPermissionMapper;
import org.module.team.permission.domain.OrganizationTeamPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamPermissionService {

	@Autowired
	private OrganizationTeamPermissionMapper organizationTeamPermissionMapper;

	public Map<String, Object> getRepeat(OrganizationTeamPermission organizationTeamPermission) {
		return organizationTeamPermissionMapper.getRepeat(organizationTeamPermission);
	}

	public int insert(OrganizationTeamPermission organizationTeamPermission) {
		return organizationTeamPermissionMapper.insert(organizationTeamPermission);
	}

	public Map<String, Object> getOne(OrganizationTeamPermission organizationTeamPermission) {
		return organizationTeamPermissionMapper.getOne(organizationTeamPermission);
	}

	public int delete(OrganizationTeamPermission organizationTeamPermission) {
		return organizationTeamPermissionMapper.delete(organizationTeamPermission);
	}

	public List<Map<String, Object>> getList(OrganizationTeamPermission organizationTeamPermission) {
		return organizationTeamPermissionMapper.getList(organizationTeamPermission);
	}

	public int update(OrganizationTeamPermission organizationTeamPermission) {
		return organizationTeamPermissionMapper.update(organizationTeamPermission);
	}
	
}
