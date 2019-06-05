package org.module.organization.user.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.user.domain.OrganizationUserRole;
import org.service.core.dao.IBaseMapper;

public interface OrganizationUserRoleMapper extends IBaseMapper<OrganizationUserRole> {

	List<Map<String, Object>> getOrganizationUserRoleRolesList(OrganizationUserRole organizationUserRole);

	List<Map<String, Object>> getOrganizationUserRolepermissionsList(OrganizationUserRole organizationUserRole);

	List<Map<String, Object>> getOrganizationUserRoleOperationsList(OrganizationUserRole organizationUserRole);

	List<Map<String, Object>> getOrganizationUserRoleMenusList(OrganizationUserRole organizationUserRole);
}