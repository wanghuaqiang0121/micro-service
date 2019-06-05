package org.module.organization.permission.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.ModuleOperationalRole;
import org.service.core.dao.IBaseMapper;

public interface ModuleOperationalRoleMapper extends IBaseMapper<ModuleOperationalRole> {
	
	public List<Map<String, Object>> getModuleRoleIsChoose(ModuleOperationalRole moduleOperationalRole);

}
