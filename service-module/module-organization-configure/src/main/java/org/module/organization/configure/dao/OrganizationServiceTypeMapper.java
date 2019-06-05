package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationServiceType;
import org.service.core.dao.IBaseMapper;

public interface OrganizationServiceTypeMapper extends IBaseMapper<OrganizationServiceType> {
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationServiceType
	 * @return
	 * @date 2018年6月6日
	 * @version 1.0
	 * @description 机构没有的服务类型
	 */
	List<Map<String, Object>> getOrganizationIsNullServiceTypeList(OrganizationServiceType organizationServiceType);

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationServiceType
	 * @return
	 * @date 2018年6月6日
	 * @version 1.0
	 * @description 查询机构拥有和未拥有的服务类型
	 */
	List<Map<String, Object>> getOrganizationServiceTypeIsChoose(OrganizationServiceType organizationServiceType);
    
}