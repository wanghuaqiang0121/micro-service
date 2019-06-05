package org.module.team.permission.service;

import java.util.List;
import java.util.Map;

import org.module.team.permission.dao.OrganizationTeamMenuMapper;
import org.module.team.permission.domain.OrganizationTeamMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamMenuService {

	@Autowired
	private OrganizationTeamMenuMapper organizationTeamMenuMapper;

	public int update(OrganizationTeamMenu organizationTeamMenu) {
		return organizationTeamMenuMapper.update(organizationTeamMenu);
	}

	public Map<String, Object> getOne(OrganizationTeamMenu organizationTeamMenu) {
		return organizationTeamMenuMapper.getOne(organizationTeamMenu);
	}

	public List<Map<String, Object>> getList(OrganizationTeamMenu organizationTeamMenu) {
		return organizationTeamMenuMapper.getList(organizationTeamMenu);
	}


	public int insert(OrganizationTeamMenu organizationTeamMenu) {
		return organizationTeamMenuMapper.insert(organizationTeamMenu);
	}

	public int delete(OrganizationTeamMenu organizationTeamMenu) {
		return organizationTeamMenuMapper.delete(organizationTeamMenu);
	}

	public Map<String, Object> getRepeat(OrganizationTeamMenu organizationTeamMenu) {
		return organizationTeamMenuMapper.getRepeat(organizationTeamMenu);
	}
	
}
