package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.OrganizationTeamRoleMapper;
import org.module.platform.configuration.domain.OrganizationTeamRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamRoleService {

	@Autowired
	private OrganizationTeamRoleMapper organizationTeamRoleMapper;

	public Map<String, Object> getRepeat(OrganizationTeamRole organizationTeamRole) {
		return organizationTeamRoleMapper.getRepeat(organizationTeamRole);
	}

	public int insert(OrganizationTeamRole organizationTeamRole) {
		return organizationTeamRoleMapper.insert(organizationTeamRole);
	}

	public Map<String, Object> getOne(OrganizationTeamRole organizationTeamRole) {
		return organizationTeamRoleMapper.getOne(organizationTeamRole);
	}

	public int delete(OrganizationTeamRole organizationTeamRole) {
		return organizationTeamRoleMapper.delete(organizationTeamRole);
	}

	public List<Map<String, Object>> getList(OrganizationTeamRole organizationTeamRole) {
		return organizationTeamRoleMapper.getList(organizationTeamRole);
	}

	public int update(OrganizationTeamRole organizationTeamRole) {
		return organizationTeamRoleMapper.update(organizationTeamRole);
	}
	
}
