package org.module.platform.configuration.dao;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.Organization;
import org.service.core.dao.IBaseMapper;

public interface OrganizationMapper extends IBaseMapper<Organization> {
	
	public List<Map<String, Object>>  getTeams(Organization organization);
}