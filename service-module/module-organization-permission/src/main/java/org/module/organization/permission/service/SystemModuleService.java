package org.module.organization.permission.service;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.dao.SystemModuleMapper;
import org.module.organization.permission.domain.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemModuleService {

	@Autowired
	private SystemModuleMapper systemModuleMapper;

	public Map<String, Object> getRepeat(SystemModule systemModule) {
		return systemModuleMapper.getRepeat(systemModule);
	}

	public int insert(SystemModule systemModule) {
		return systemModuleMapper.insert(systemModule);
	}

	public Map<String, Object> getOne(SystemModule systemModule) {
		return systemModuleMapper.getOne(systemModule);
	}

	public int update(SystemModule systemModule) {
		return systemModuleMapper.update(systemModule);
	}

	public List<Map<String, Object>> getList(SystemModule systemModule) {
		return systemModuleMapper.getList(systemModule);
	}
}
