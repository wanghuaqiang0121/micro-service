package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationServicePackage;
import org.service.core.dao.IBaseMapper;

public interface OrganizationServicePackageMapper extends IBaseMapper<OrganizationServicePackage> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationServicePackage record);

    OrganizationServicePackage selectByPrimaryKey(Integer id);

    List<OrganizationServicePackage> selectAll();

    int updateByPrimaryKey(OrganizationServicePackage record);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationServicePackage
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 查询父机构服务包列表
	*/
	List<Map<String, Object>> getParentOrganizationPackages(OrganizationServicePackage organizationServicePackage);

	List<Map<String, Object>> getOrganizationServiceAuthorizationTeamPackages(
			OrganizationServicePackage organizationServicePackage);

}