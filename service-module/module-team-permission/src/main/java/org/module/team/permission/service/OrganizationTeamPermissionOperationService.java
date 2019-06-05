package org.module.team.permission.service;

import java.util.List;
import java.util.Map;

import org.module.team.permission.dao.OrganizationTeamPermissionOperationMapper;
import org.module.team.permission.domain.OrganizationTeamPermissionOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamPermissionOperationService {

	@Autowired
	private OrganizationTeamPermissionOperationMapper organizationTeamPermissionOperationMapper;

	public Map<String, Object> getRepeat(OrganizationTeamPermissionOperation organizationTeamPermissionOperation) {
		return organizationTeamPermissionOperationMapper.getRepeat(organizationTeamPermissionOperation);
	}

	public int insert(OrganizationTeamPermissionOperation organizationTeamPermissionOperation) {
		return organizationTeamPermissionOperationMapper.insert(organizationTeamPermissionOperation);
	}

	public int delete(OrganizationTeamPermissionOperation organizationTeamPermissionOperation) {
		return organizationTeamPermissionOperationMapper.delete(organizationTeamPermissionOperation);
	}

	public List<Map<String, Object>> teamPermissionHaveAndNotHaveOperations(
			OrganizationTeamPermissionOperation organizationTeamPermissionOperation) {
		return organizationTeamPermissionOperationMapper.teamPermissionHaveAndNotHaveOperations(organizationTeamPermissionOperation);
	}
}
