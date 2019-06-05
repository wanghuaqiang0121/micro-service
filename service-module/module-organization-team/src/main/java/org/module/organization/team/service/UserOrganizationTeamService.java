package org.module.organization.team.service;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.organization.team.dao.UserOrganizationTeamMapper;
import org.module.organization.team.domain.UserOrganizationTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserOrganizationTeamService {

	@Autowired
	private UserOrganizationTeamMapper userOrganizationTeamMapper;
	
	
	@Transactional
	public boolean insert(UserOrganizationTeam userOrganizationTeam){
		Map<String, Object> map = userOrganizationTeamMapper.getRepeat(userOrganizationTeam);
		int num = 0;
		if(MapUtils.isEmpty(map)){
			if(userOrganizationTeam.getIsSign() == null){
				userOrganizationTeam.setIsSign(false);
			}
			if(userOrganizationTeam.getIsIncrementService() == null){
				userOrganizationTeam.setIsIncrementService(false);
			}
			if(userOrganizationTeam.getIsSingleService() == null){
				userOrganizationTeam.setIsSingleService(false);
			}
			num = userOrganizationTeamMapper.insert(userOrganizationTeam);
		}else{
			userOrganizationTeam.setId(Integer.valueOf(map.get("id").toString()));
			num = userOrganizationTeamMapper.update(userOrganizationTeam);
		}
		if(num > 0){
			return true;
		}
		return false;
	}
}
