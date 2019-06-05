package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationRole;
import org.service.core.dao.IBaseMapper;

public interface OrganizationRoleMapper extends IBaseMapper<OrganizationRole> {

	List<Map<String, Object>> getOrganizationRoleAuthorizationUserList(OrganizationRole organizationRole);
  
}