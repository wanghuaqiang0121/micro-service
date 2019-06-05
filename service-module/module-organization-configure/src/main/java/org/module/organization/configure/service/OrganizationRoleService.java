package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationRoleMapper;
import org.module.organization.configure.domain.OrganizationRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationRoleService {
	
	@Autowired
	private OrganizationRoleMapper organizationRoleMapper ;

	public int insert(OrganizationRole organizationRole) {
		return organizationRoleMapper.insert(organizationRole);
	}

	public List<Map<String, Object>> getOrganizationRoleAuthorizationUserList(OrganizationRole organizationRole) {
		return organizationRoleMapper.getOrganizationRoleAuthorizationUserList(organizationRole);
	}

}
