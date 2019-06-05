package org.module.organization.permission.service;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.dao.OperationalMenuMapper;
import org.module.organization.permission.domain.OperationalMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationalMenuService {
	@Autowired
	private OperationalMenuMapper operationalMenuMapper;
	
	public	int insert(OperationalMenu operationalMenu) {
		return operationalMenuMapper.insert(operationalMenu);

	}

	public	int update(OperationalMenu operationalMenu) {
		return operationalMenuMapper.update(operationalMenu);
	}

	public	int delete(OperationalMenu operationalMenu) {
		return operationalMenuMapper.delete(operationalMenu);
	}

	public	Map<String, Object> getRepeat(OperationalMenu operationalMenu) {
		return operationalMenuMapper.getRepeat(operationalMenu);
	}

	public	Map<String, Object> getOne(OperationalMenu operationalMenu) {
		return operationalMenuMapper.getOne(operationalMenu);
	}

	public	List<Map<String, Object>> getList(OperationalMenu operationalMenu){
		return operationalMenuMapper.getList(operationalMenu);
	}

}
