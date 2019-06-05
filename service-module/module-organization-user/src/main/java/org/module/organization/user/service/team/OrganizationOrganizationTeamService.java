package org.module.organization.user.service.team;

import java.util.List;
import java.util.Map;

import org.module.organization.user.dao.team.OrganizationOrganizationTeamMapper;
import org.module.organization.user.domain.team.OrganizationOrganizationTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrganizationOrganizationTeamService {
	
	@Autowired
	private OrganizationOrganizationTeamMapper organizationOrganizationTeamMapper;

	public List<Map<String, Object>> getList(OrganizationOrganizationTeam organizationOrganizationTeam) {
		return organizationOrganizationTeamMapper.getList(organizationOrganizationTeam);
	}

	public List<Map<String, Object>> getOrganizationOrganizationTeamRoles(
			OrganizationOrganizationTeam organizationOrganizationTeam) {
		return organizationOrganizationTeamMapper.getOrganizationOrganizationTeamRoles(organizationOrganizationTeam);
	}

	public List<Map<String, Object>> getOrganizationOrganizationTeamPermissions(
			OrganizationOrganizationTeam organizationOrganizationTeam) {
		return organizationOrganizationTeamMapper.getOrganizationOrganizationTeamPermissions(organizationOrganizationTeam);
	}

	public List<Map<String, Object>> getOrganizationOrganizationTeamMenus(
			OrganizationOrganizationTeam organizationOrganizationTeam) {
		return organizationOrganizationTeamMapper.getOrganizationOrganizationTeamMenus(organizationOrganizationTeam);
	}

	public List<Map<String, Object>> getOrganizationOrganizationTeamOperation(
			OrganizationOrganizationTeam organizationOrganizationTeam) {
		return organizationOrganizationTeamMapper.getOrganizationOrganizationTeamOperation(organizationOrganizationTeam);
	}

	public List<Map<String, Object>> getOrganizationOrganizationTeamManagerRoles(
			OrganizationOrganizationTeam organizationOrganizationTeam) {
		return organizationOrganizationTeamMapper.getOrganizationOrganizationTeamManagerRoles(organizationOrganizationTeam);
	}

	public List<Map<String, Object>> getOrganizationOrganizationTeamManagerPermissions(
			OrganizationOrganizationTeam organizationOrganizationTeam) {
		return organizationOrganizationTeamMapper.getOrganizationOrganizationTeamManagerPermissions(organizationOrganizationTeam);
	}

	public List<Map<String, Object>> getOrganizationOrganizationTeamManagerMenus(
			OrganizationOrganizationTeam organizationOrganizationTeam) {
		return organizationOrganizationTeamMapper.getOrganizationOrganizationTeamManagerMenus(organizationOrganizationTeam);
	}

	public List<Map<String, Object>> getOrganizationOrganizationTeamManagerOperation(
			OrganizationOrganizationTeam organizationOrganizationTeam) {
		return organizationOrganizationTeamMapper.getOrganizationOrganizationTeamManagerOperation(organizationOrganizationTeam);
	}

}
