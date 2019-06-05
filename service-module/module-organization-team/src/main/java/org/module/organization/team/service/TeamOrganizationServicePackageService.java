package org.module.organization.team.service;

import java.util.List;
import java.util.Map;

import org.module.organization.team.dao.TeamOrganizationServicePackageMapper;
import org.module.organization.team.domain.TeamOrganizationServicePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamOrganizationServicePackageService {

	@Autowired
	private TeamOrganizationServicePackageMapper teamOrganizationServicePackageMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description  团队服务包详情
	*/
	public Map<String, Object> getOne(TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.getOne(teamOrganizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 团队机构服务包列表
	*/
	public List<Map<String, Object>> getList(TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.getList(teamOrganizationServicePackage);
	}
	
}
