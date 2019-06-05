package org.module.organization.configure.service;

import org.module.organization.configure.dao.OrganizationServicePackageLogMapper;
import org.module.organization.configure.domain.OrganizationServicePackageLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServicePackageLogService {

	@Autowired
	private OrganizationServicePackageLogMapper mapper;

	public int insert(OrganizationServicePackageLog packagelog) {
		return mapper.insert(packagelog);
	}
	
}
