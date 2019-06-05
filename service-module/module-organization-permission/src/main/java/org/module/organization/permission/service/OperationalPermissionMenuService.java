package org.module.organization.permission.service;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.dao.OperationalPermissionMenuMapper;
import org.module.organization.permission.domain.OperationalPermissionMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationalPermissionMenuService {
	@Autowired
	private OperationalPermissionMenuMapper mapper;
	
	public Map<String, Object> getRepeat(OperationalPermissionMenu operationalPermissionMenu){
		return mapper.getRepeat(operationalPermissionMenu);
	}

	public int insert(OperationalPermissionMenu operationalPermissionMenu) {
		return mapper.insert(operationalPermissionMenu);
	}

	public int delete(OperationalPermissionMenu operationalPermissionMenu) {
		return mapper.delete(operationalPermissionMenu);
	}

	public List<Map<String, Object>> getPermissionMenuIsChoose(OperationalPermissionMenu operationalPermissionMenu) {
		return mapper.getPermissionMenuIsChoose(operationalPermissionMenu);
	}
}
