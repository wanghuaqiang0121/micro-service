package org.module.team.permission.service;

import java.util.List;
import java.util.Map;

import org.module.team.permission.dao.OrganizationTeamOperationMapper;
import org.module.team.permission.domain.OrganizationTeamOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamOperationService {

	@Autowired
	private OrganizationTeamOperationMapper organizationTeamOperationMapper;

	public int update(OrganizationTeamOperation organizationTeamOperation) {
		return organizationTeamOperationMapper.update(organizationTeamOperation);
	}

	public Map<String, Object> getOne(OrganizationTeamOperation organizationTeamOperation) {
		return organizationTeamOperationMapper.getOne(organizationTeamOperation);
	}

	public List<Map<String, Object>> getList(OrganizationTeamOperation organizationTeamOperation) {
		return organizationTeamOperationMapper.getList(organizationTeamOperation);
	}

	public int insert(OrganizationTeamOperation organizationTeamOperation) {
		return organizationTeamOperationMapper.insert(organizationTeamOperation);
	}

	public Map<String, Object> getRepeat(OrganizationTeamOperation organizationTeamOperation) {
		return organizationTeamOperationMapper.getRepeat(organizationTeamOperation);
	}

	public int delete(OrganizationTeamOperation organizationTeamOperation) {
		return organizationTeamOperationMapper.delete(organizationTeamOperation);
	}
}
