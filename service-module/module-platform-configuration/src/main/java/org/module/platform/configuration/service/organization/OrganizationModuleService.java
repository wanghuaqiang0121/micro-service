package org.module.platform.configuration.service.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.organization.OrganizationModuleMapper;
import org.module.platform.configuration.domain.organization.OrganizationModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationModuleService {
	@Autowired
	private OrganizationModuleMapper organizationModuleMapper;
	
	public int insert(OrganizationModule organizationModule){
		return organizationModuleMapper.insert(organizationModule);
	}
	
	public int update(OrganizationModule organizationModule){
		return organizationModuleMapper.update(organizationModule);
	}
	
	public List<Map<String, Object>> getList(OrganizationModule organizationModule){
		return organizationModuleMapper.getList(organizationModule);
	}

	public List<Map<String, Object>> getOrganizationModuleIsChoose(OrganizationModule organizationModule){
		return organizationModuleMapper.queryOrganizationModuleIsChoose(organizationModule);
	}

	public Map<String, Object> getRepeat(OrganizationModule organizationModule) {
		return organizationModuleMapper.getRepeat(organizationModule);
	}

	public Map<String, Object> getOne(OrganizationModule organizationModule) {
		return organizationModuleMapper.getOne(organizationModule);
	}
}
