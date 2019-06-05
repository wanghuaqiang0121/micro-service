package org.module.platform.configuration.service;

import org.module.platform.configuration.dao.OrganizationUserRoleMapper;
import org.module.platform.configuration.domain.OrganizationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationUserRoleService {
	
	@Autowired
	private OrganizationUserRoleMapper organizationUserRoleMapper;

	public int insert(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.insert(organizationUserRole);
	}

}
