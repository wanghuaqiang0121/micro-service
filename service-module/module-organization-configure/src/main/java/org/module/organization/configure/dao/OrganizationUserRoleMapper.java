package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationUserRole;
import org.service.core.dao.IBaseMapper;

public interface OrganizationUserRoleMapper extends IBaseMapper<OrganizationUserRole> {

	List<Map<String, Object>> getHaveAndNotHave(OrganizationUserRole organizationUserRole);
   
}