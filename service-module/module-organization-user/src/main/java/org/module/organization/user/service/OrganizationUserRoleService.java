package org.module.organization.user.service;

import java.util.List;
import java.util.Map;

import org.module.organization.user.dao.OrganizationUserRoleMapper;
import org.module.organization.user.domain.OrganizationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationUserRoleService {

	@Autowired
	private OrganizationUserRoleMapper organizationUserRoleMapper;

	public List<Map<String, Object>> getList(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.getList(organizationUserRole);
	}

	public List<Map<String, Object>> getOrganizationUserRoleRolesList(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.getOrganizationUserRoleRolesList(organizationUserRole);
	}

	public List<Map<String, Object>> getOrganizationUserRolepermissionsList(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.getOrganizationUserRolepermissionsList(organizationUserRole);
	}

	public List<Map<String, Object>> getOrganizationUserRoleOperationsList(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.getOrganizationUserRoleOperationsList(organizationUserRole);
	}

	public List<Map<String, Object>> getOrganizationUserRoleMenusList(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.getOrganizationUserRoleMenusList(organizationUserRole);
	}
}
