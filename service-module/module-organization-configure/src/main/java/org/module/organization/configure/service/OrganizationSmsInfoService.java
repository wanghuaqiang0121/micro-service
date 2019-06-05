package org.module.organization.configure.service;

import java.util.Map;

import org.module.organization.configure.dao.OrganizationSmsInfoMapper;
import org.module.organization.configure.domain.OrganizationSmsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationSmsInfoService {
	@Autowired
	private OrganizationSmsInfoMapper mapper;

	public Map<String, Object> getOne(OrganizationSmsInfo organizationSmsInfo) {
		return mapper.getOne(organizationSmsInfo);
	}

	public int update(OrganizationSmsInfo organizationSmsInfo) {
		return mapper.update(organizationSmsInfo);
	}

	public int insert(OrganizationSmsInfo organizationSmsInfo) {
		return mapper.insert(organizationSmsInfo);
	}

	
}
