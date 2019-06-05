package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationPackageService;
import org.service.core.dao.IBaseMapper;

public interface OrganizationPackageServiceMapper extends IBaseMapper<OrganizationPackageService> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationPackageService record);

    OrganizationPackageService selectByPrimaryKey(Integer id);

    List<OrganizationPackageService> selectAll();

    int updateByPrimaryKey(OrganizationPackageService record);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param oPackageService
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除服务包所有服务关联
	*/
	int deleteByOrganizationPackageId(OrganizationPackageService oPackageService);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageService
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 批量添加机构服务包和服务关联
	*/
	int batchInsert(List<OrganizationPackageService> organizationPackageService);

	List<Map<String, Object>> getOrganizationPackageIsNullService(
			OrganizationPackageService organizationPackageService);

	
}