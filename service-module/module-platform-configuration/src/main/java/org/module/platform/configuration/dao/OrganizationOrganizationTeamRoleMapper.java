package org.module.platform.configuration.dao;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.OrganizationOrganizationTeamRole;
import org.service.core.dao.IBaseMapper;

public interface OrganizationOrganizationTeamRoleMapper extends IBaseMapper<OrganizationOrganizationTeamRole> {

	int deleteOrganizationUserTeamRole(OrganizationOrganizationTeamRole organizationOrganizationTeamRole);

	List<Map<String, Object>> getOrganizationUserTeamRoleOne(
			OrganizationOrganizationTeamRole organizationOrganizationTeamRole);
}