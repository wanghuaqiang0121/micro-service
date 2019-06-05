package org.module.organization.permission.service;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.dao.OperationalRolePermissionMapper;
import org.module.organization.permission.domain.OperationalRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationalRolePermissionService {
	@Autowired
	private  OperationalRolePermissionMapper mapper;
	
	public Map<String, Object> getRepeat(OperationalRolePermission operationalRolePermission){
		return mapper.getRepeat(operationalRolePermission);
	}
	
	public int insert(OperationalRolePermission operationalRolePermission) {
		return mapper.insert(operationalRolePermission);
	}

	public int delete(OperationalRolePermission operationalRolePermission) {
		return mapper.delete(operationalRolePermission);
	}
	

	public List<Map<String, Object>> getPermissionOperationIsChoose(OperationalRolePermission operationalRolePermission) {
		return mapper.getPermissionOperationIsChoose(operationalRolePermission);
	}
}
