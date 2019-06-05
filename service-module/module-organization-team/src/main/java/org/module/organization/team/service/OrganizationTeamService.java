package org.module.organization.team.service;

import java.util.List;
import java.util.Map;

import org.module.organization.team.dao.OrganizationTeamMapper;
import org.module.organization.team.domain.OrganizationTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamService {

	@Autowired
	private OrganizationTeamMapper organizationTeamMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeam
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description
	*/
	public Map<String, Object> getOne(OrganizationTeam organizationTeam) {
		return organizationTeamMapper.getOne(organizationTeam);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeam
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 查询机构团队列表(内嵌服务包列表)
	*/
	public List<Map<String, Object>> getList(OrganizationTeam organizationTeam) {
		return organizationTeamMapper.getList(organizationTeam);
	}
	
}
