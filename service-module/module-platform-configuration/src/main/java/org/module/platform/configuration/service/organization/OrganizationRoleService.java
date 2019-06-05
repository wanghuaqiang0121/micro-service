package org.module.platform.configuration.service.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.organization.OrganizationRoleMapper;
import org.module.platform.configuration.domain.organization.ModuleOperationalRole;
import org.module.platform.configuration.domain.organization.OrganizationRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationRoleService {
	
	@Autowired
	private OrganizationRoleMapper organizationRoleMapper ;

	public int insert(OrganizationRole organizationRole) {
		return organizationRoleMapper.insert(organizationRole);
	}

	public List<Map<String, Object>> moduleOperationalRoleList(ModuleOperationalRole moduleOperationalRole) {
		return organizationRoleMapper.moduleOperationalRoleList(moduleOperationalRole);
	}
	
	public List<Map<String, Object>> getList(OrganizationRole organizationRole){
		return organizationRoleMapper.getList(organizationRole);
	}
	
	public int update(OrganizationRole organizationRole){
		return organizationRoleMapper.update(organizationRole);
	}

	public Map<String, Object> getRepeat(OrganizationRole organizationRole) {
		return organizationRoleMapper.getRepeat(organizationRole);
	}

	public int batchDelete(OrganizationRole organizationRole) {
		return organizationRoleMapper.batchDelete(organizationRole);
	}

	public int batchInsert(List<OrganizationRole> organizationRoles) {
		return organizationRoleMapper.batchInsert(organizationRoles);
	}
}
