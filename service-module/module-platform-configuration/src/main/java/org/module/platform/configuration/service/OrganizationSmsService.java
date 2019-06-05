package org.module.platform.configuration.service;

import org.module.platform.configuration.dao.OrganizationSmsMapper;
import org.module.platform.configuration.domain.OrganizationSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationSmsService {

	@Autowired
	private OrganizationSmsMapper mapper;

	public int insert(OrganizationSms organizationSms) {
		return mapper.insert(organizationSms);
	}
	
}
