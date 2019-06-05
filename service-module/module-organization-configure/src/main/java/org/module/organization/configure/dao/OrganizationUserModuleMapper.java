package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationUserModule;
import org.service.core.dao.IBaseMapper;

public interface OrganizationUserModuleMapper extends IBaseMapper<OrganizationUserModule> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationUserModule record);

    OrganizationUserModule selectByPrimaryKey(Integer id);

    List<OrganizationUserModule> selectAll();

    int updateByPrimaryKey(OrganizationUserModule record);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserModule
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 机构用户在该机构下拥有和未拥有的模块列表
	*/
	List<Map<String, Object>> haveAndNotHaveModules(OrganizationUserModule organizationUserModule);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserModule
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 机构用户在该机构下拥有的模块列表
	*/
	List<Map<String, Object>> haveModules(OrganizationUserModule organizationUserModule);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserModule
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 机构拥有的模块列表
	*/
	List<Map<String, Object>> organizationHaveModules(OrganizationUserModule organizationUserModule);
}