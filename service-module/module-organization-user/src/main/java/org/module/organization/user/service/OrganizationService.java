package org.module.organization.user.service;

import java.util.Map;

import org.module.organization.user.dao.OrganizationMapper;
import org.module.organization.user.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
	@Autowired
	private OrganizationMapper mapper;

	public Map<String, Object> getSmsInfo(Organization organization) {
		return mapper.getSmsInfo(organization);
	}
	
}
