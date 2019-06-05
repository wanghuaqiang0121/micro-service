package org.module.organization.configure.service;

import java.util.Map;

import org.module.organization.configure.dao.OrganizationMapper;
import org.module.organization.configure.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationMapper organizationMapper;

	public Map<String, Object> getOne(Organization organization) {
		return organizationMapper.getOne(organization);
	}
	
}
