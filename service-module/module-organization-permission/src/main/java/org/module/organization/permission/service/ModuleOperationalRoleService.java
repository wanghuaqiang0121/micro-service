package org.module.organization.permission.service;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.dao.ModuleOperationalRoleMapper;
import org.module.organization.permission.domain.ModuleOperationalRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleOperationalRoleService {
	@Autowired
	private ModuleOperationalRoleMapper mapper;
	
	public Map<String, Object> getRepeat(ModuleOperationalRole moduleOperationalRole){
		return mapper.getRepeat(moduleOperationalRole);
	}
	
	public int insert(ModuleOperationalRole moduleOperationalRole){
		return mapper.insert(moduleOperationalRole);
	}
	
	public int delete(ModuleOperationalRole moduleOperationalRole){
		return mapper.delete(moduleOperationalRole);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param moduleOperationalRole
	 * @return
	 * @date 2018年8月15日
	 * @version 1.0
	 * @description 查询该模块拥有和未拥有的角色
	 */
	public List<Map<String, Object>> getModuleRoleIsChoose(ModuleOperationalRole moduleOperationalRole){
		return mapper.getModuleRoleIsChoose(moduleOperationalRole);
	}

}
