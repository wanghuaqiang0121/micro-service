package org.module.organization.team.service;

import java.util.List;
import java.util.Map;

import org.module.organization.team.dao.OrganizationServicePackageMapper;
import org.module.organization.team.domain.OrganizationServicePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServicePackageService {

	@Autowired
	private OrganizationServicePackageMapper organizationServicePackageMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationServicePackage
	* @return 
	* @date 2018年4月2日
	* @version 1.0
	* @description 查询机构的服务包详情
	*/
	public Map<String, Object> getOne(OrganizationServicePackage organizationServicePackage) {
		return organizationServicePackageMapper.getOne(organizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationServicePackage
	* @return 
	* @date 2018年4月2日
	* @version 1.0
	* @description 查询包列表
	*/
	public List<Map<String, Object>> getList(OrganizationServicePackage organizationServicePackage) {
		return organizationServicePackageMapper.getList(organizationServicePackage);
	}
	
	
}
