package org.module.organization.permission.service;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.dao.OperationalPermissionMapper;
import org.module.organization.permission.domain.OperationalPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationalPermissionService {

	@Autowired
	private OperationalPermissionMapper operationalPermissionMapper;

	public Map<String, Object> getRepeat(OperationalPermission operationalPermission) {
		return operationalPermissionMapper.getRepeat(operationalPermission);
	}

	public int insert(OperationalPermission operationalPermission) {
		return operationalPermissionMapper.insert(operationalPermission);
	}

	public Map<String, Object> getOne(OperationalPermission operationalPermission) {
		return operationalPermissionMapper.getOne(operationalPermission);
	}

	public int update(OperationalPermission operationalPermission) {
		return operationalPermissionMapper.update(operationalPermission);
	}

	public List<Map<String, Object>> getList(OperationalPermission operationalPermission) {
		return operationalPermissionMapper.getList(operationalPermission);
	}
}
