package org.module.organization.configure.service.doctor;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.doctor.OrganizationTeamRoleMapper;
import org.module.organization.configure.domain.doctor.OrganizationTeamRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamRoleService {

	@Autowired
	private OrganizationTeamRoleMapper organizationTeamRoleMapper;

	public List<Map<String, Object>> getList(OrganizationTeamRole organizationTeamRole) {
		return organizationTeamRoleMapper.getList(organizationTeamRole);
	}
}
