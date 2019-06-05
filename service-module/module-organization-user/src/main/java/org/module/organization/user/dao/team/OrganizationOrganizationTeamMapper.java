package org.module.organization.user.dao.team;

import java.util.List;
import java.util.Map;

import org.module.organization.user.domain.team.OrganizationOrganizationTeam;
import org.service.core.dao.IBaseMapper;

public interface OrganizationOrganizationTeamMapper extends IBaseMapper<OrganizationOrganizationTeam> {

	List<Map<String, Object>> getOrganizationOrganizationTeamRoles(
			OrganizationOrganizationTeam organizationOrganizationTeam);

	List<Map<String, Object>> getOrganizationOrganizationTeamPermissions(
			OrganizationOrganizationTeam organizationOrganizationTeam);

	List<Map<String, Object>> getOrganizationOrganizationTeamMenus(
			OrganizationOrganizationTeam organizationOrganizationTeam);

	List<Map<String, Object>> getOrganizationOrganizationTeamOperation(
			OrganizationOrganizationTeam organizationOrganizationTeam);

	List<Map<String, Object>> getOrganizationOrganizationTeamManagerRoles(
			OrganizationOrganizationTeam organizationOrganizationTeam);

	List<Map<String, Object>> getOrganizationOrganizationTeamManagerPermissions(
			OrganizationOrganizationTeam organizationOrganizationTeam);

	List<Map<String, Object>> getOrganizationOrganizationTeamManagerMenus(
			OrganizationOrganizationTeam organizationOrganizationTeam);

	List<Map<String, Object>> getOrganizationOrganizationTeamManagerOperation(
			OrganizationOrganizationTeam organizationOrganizationTeam);
}