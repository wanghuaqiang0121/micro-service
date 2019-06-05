package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.OrganizationOrganizationTeamRoleMapper;
import org.module.platform.configuration.domain.OrganizationOrganizationTeamRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationOrganizationTeamRoleService {

	@Autowired
	private OrganizationOrganizationTeamRoleMapper organizationOrganizationTeamRoleMapper;

	public Map<String, Object> getRepeat(OrganizationOrganizationTeamRole organizationOrganizationTeamRole) {
		return organizationOrganizationTeamRoleMapper.getRepeat(organizationOrganizationTeamRole);
	}

	public int insert(OrganizationOrganizationTeamRole organizationOrganizationTeamRole) {
		return organizationOrganizationTeamRoleMapper.insert(organizationOrganizationTeamRole);
	}

	public List<Map<String, Object>> getList(OrganizationOrganizationTeamRole organizationOrganizationTeamRole) {
		return organizationOrganizationTeamRoleMapper.getList(organizationOrganizationTeamRole);
	}

	public Map<String, Object> getOne(OrganizationOrganizationTeamRole organizationOrganizationTeamRole) {
		return organizationOrganizationTeamRoleMapper.getOne(organizationOrganizationTeamRole);
	}

	public int delete(OrganizationOrganizationTeamRole organizationOrganizationTeamRole) {
		return organizationOrganizationTeamRoleMapper.delete(organizationOrganizationTeamRole);
	}

	public int deleteOrganizationUserTeamRole(OrganizationOrganizationTeamRole organizationOrganizationTeamRole) {
		return organizationOrganizationTeamRoleMapper.deleteOrganizationUserTeamRole(organizationOrganizationTeamRole);
	}

	public List<Map<String, Object>> getOrganizationUserTeamRoleOne(
			OrganizationOrganizationTeamRole organizationOrganizationTeamRole) {
		return organizationOrganizationTeamRoleMapper.getOrganizationUserTeamRoleOne(organizationOrganizationTeamRole);
	}
}
