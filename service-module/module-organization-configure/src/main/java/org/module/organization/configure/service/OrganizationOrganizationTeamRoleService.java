package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationOrganizationTeamRoleMapper;
import org.module.organization.configure.domain.OrganizationOrganizationTeamRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationOrganizationTeamRoleService {

	@Autowired
	private OrganizationOrganizationTeamRoleMapper organizationOrganizationTeamRoleMapper;

	public List<Map<String, Object>> getList(OrganizationOrganizationTeamRole organizationOrganizationTeamRole) {
		return organizationOrganizationTeamRoleMapper.getList(organizationOrganizationTeamRole);
	}

}
