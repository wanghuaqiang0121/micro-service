package org.module.organization.permission.service;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.dao.OperationalOperationMapper;
import org.module.organization.permission.domain.OperationalOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationalOperationService {
	@Autowired
	private OperationalOperationMapper operationalOperationMapper;
	
	public	int insert(OperationalOperation operationalOperation) {
		return operationalOperationMapper.insert(operationalOperation);

	}

	public	int update(OperationalOperation operationalOperation) {
		return operationalOperationMapper.update(operationalOperation);
	}

	public	int delete(OperationalOperation operationalOperation) {
		return operationalOperationMapper.delete(operationalOperation);
	}

	public	Map<String, Object> getRepeat(OperationalOperation operationalOperation) {
		return operationalOperationMapper.getRepeat(operationalOperation);
	}

	public	Map<String, Object> getOne(OperationalOperation operationalOperation) {
		return operationalOperationMapper.getOne(operationalOperation);
	}

	public	List<Map<String, Object>> getList(OperationalOperation operationalOperation){
		return operationalOperationMapper.getList(operationalOperation);
	}

}
