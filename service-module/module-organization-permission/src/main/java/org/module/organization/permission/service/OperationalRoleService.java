package org.module.organization.permission.service;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.dao.OperationalRoleMapper;
import org.module.organization.permission.domain.OperationalRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationalRoleService {
	
	@Autowired
	private OperationalRoleMapper operationalRoleMapper;

	public Map<String, Object> getRepeat(OperationalRole operationalRole) {
		return operationalRoleMapper.getRepeat(operationalRole);
	}

	public int insert(OperationalRole operationalRole) {
		return operationalRoleMapper.insert(operationalRole);
	}

	public Map<String, Object> getOne(OperationalRole operationalRole) {
		return operationalRoleMapper.getOne(operationalRole);
	}

	public int update(OperationalRole operationalRole) {
		return operationalRoleMapper.update(operationalRole);
	}

	public List<Map<String, Object>> getList(OperationalRole operationalRole) {
		return operationalRoleMapper.getList(operationalRole);
	}

	
	
}
