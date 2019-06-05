package org.module.platform.configuration.service.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.organization.ModuleOperationalRoleMapper;
import org.module.platform.configuration.domain.organization.ModuleOperationalRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleOperationalRoleService {
	@Autowired
	private ModuleOperationalRoleMapper moduleOperationalRoleMapper;
	
	public List<Map<String, Object>> getList(ModuleOperationalRole moduleOperationalRole){
		return moduleOperationalRoleMapper.getList(moduleOperationalRole);
	}

}
