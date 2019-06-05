package org.module.organization.configure.dao;

import java.util.List;
import org.module.organization.configure.domain.OrganizationPackageUserType;
import org.service.core.dao.IBaseMapper;

public interface OrganizationPackageUserTypeMapper extends IBaseMapper<OrganizationPackageUserType> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationPackageUserType record);

    OrganizationPackageUserType selectByPrimaryKey(Integer id);

    List<OrganizationPackageUserType> selectAll();

    int updateByPrimaryKey(OrganizationPackageUserType record);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param oPackageUserType
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除服务包所有使用人群关联
	*/
	int deleteByOrganizationPackageId(OrganizationPackageUserType oPackageUserType);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageUserType
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 批量添加服务包使用人群关联
	*/
	int batchInsert(List<OrganizationPackageUserType> organizationPackageUserType);
}