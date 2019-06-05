package org.module.organization.permission.service;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.dao.OperationalPermissionOperationMapper;
import org.module.organization.permission.domain.OperationalPermissionOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationalPermissionOperationService {
	@Autowired
	private  OperationalPermissionOperationMapper mapper;
	
	public Map<String, Object> getRepeat(OperationalPermissionOperation operationalPermissionOperation){
		return mapper.getRepeat(operationalPermissionOperation);
	}
	
	public int insert(OperationalPermissionOperation operationalPermissionOperation) {
		return mapper.insert(operationalPermissionOperation);
	}

	public int delete(OperationalPermissionOperation operationalPermissionOperation) {
		return mapper.delete(operationalPermissionOperation);
	}

	public List<Map<String, Object>> getPermissionOperationIsChoose(OperationalPermissionOperation operationalPermissionOperation) {
		return mapper.getPermissionOperationIsChoose(operationalPermissionOperation);
	}
}
