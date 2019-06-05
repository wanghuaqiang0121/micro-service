package org.module.platform.configuration.dao.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.organization.ModuleOperationalRole;
import org.module.platform.configuration.domain.organization.OrganizationRole;
import org.service.core.dao.IBaseMapper;

public interface OrganizationRoleMapper extends IBaseMapper<OrganizationRole> {
  
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param moduleOperationalRole
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 查询机构在该模块下拥有和未拥有的权限列表
	 */
	public List<Map<String, Object>> moduleOperationalRoleList(ModuleOperationalRole moduleOperationalRole);

	public int batchDelete(OrganizationRole organizationRole);

	public int batchInsert(List<OrganizationRole> organizationRoles);
}