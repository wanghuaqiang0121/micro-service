package org.module.user.service;

import java.util.List;
import java.util.Map;

import org.module.user.dao.UserOrganizationTeamMapper;
import org.module.user.domain.UserOrganizationTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOrganizationTeamService {
	@Autowired
	private UserOrganizationTeamMapper userOrganizationTeamMapper;

	public List<Map<String, Object>> getList(UserOrganizationTeam userOrganizationTeam) {
		return userOrganizationTeamMapper.getList(userOrganizationTeam);
	}
	
}
