package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationServicePackageMapper;
import org.module.organization.configure.domain.OrganizationServicePackage;
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
	* @date 2018年3月21日
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
	* @date 2018年3月21日
	* @version 1.0
	* @description 修改机构的服务包详情
	*/
	public int update(OrganizationServicePackage organizationServicePackage) {
		return organizationServicePackageMapper.update(organizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationServicePackage
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 查询是否存在重复
	*/
	public Map<String, Object> getRepeat(OrganizationServicePackage organizationServicePackage) {
		return organizationServicePackageMapper.getRepeat(organizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationServicePackage
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 删除机构的服务包
	*/
	public int delete(OrganizationServicePackage organizationServicePackage) {
		return organizationServicePackageMapper.delete(organizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationServicePackage
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 查询父机构服务包列表
	*/
	public List<Map<String, Object>> getParentOrganizationPackages(
			OrganizationServicePackage organizationServicePackage) {
		return organizationServicePackageMapper.getParentOrganizationPackages(organizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param newServicePackage
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 添加机构的服务包
	*/
	public int insert(OrganizationServicePackage newServicePackage) {
		return organizationServicePackageMapper.insert(newServicePackage);
	}


	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationServicePackage
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 机构列表
	*/
	public List<Map<String, Object>> getList(OrganizationServicePackage organizationServicePackage) {
		return organizationServicePackageMapper.getList(organizationServicePackage);
	}

	public List<Map<String, Object>> getOrganizationServiceAuthorizationTeamPackages(
			OrganizationServicePackage organizationServicePackage) {
		return organizationServicePackageMapper.getOrganizationServiceAuthorizationTeamPackages(organizationServicePackage);
	}

	
	
}
