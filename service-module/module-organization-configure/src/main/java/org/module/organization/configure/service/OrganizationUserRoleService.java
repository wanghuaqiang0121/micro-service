package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationUserRoleMapper;
import org.module.organization.configure.domain.OrganizationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationUserRoleService {
	
	@Autowired
	private OrganizationUserRoleMapper organizationUserRoleMapper;

	public int update(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.update(organizationUserRole);
	}

	public int insert(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.insert(organizationUserRole);
	}

	public Map<String, Object> getRepeat(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.getRepeat(organizationUserRole);
	}

	public List<Map<String, Object>> getList(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.getList(organizationUserRole);
	}

	public List<Map<String, Object>> getHaveAndNotHave(OrganizationUserRole organizationUserRole) {
		return organizationUserRoleMapper.getHaveAndNotHave(organizationUserRole);
	}

	

}
