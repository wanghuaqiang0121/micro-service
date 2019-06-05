package org.module.platform.configuration.dao.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.organization.OrganizationModule;
import org.service.core.dao.IBaseMapper;

public interface OrganizationModuleMapper extends IBaseMapper<OrganizationModule>{
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationModule
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 查询机构关联和未关联的模块列表
	 */
	public List<Map<String, Object>> queryOrganizationModuleIsChoose(OrganizationModule organizationModule);

}
