package org.module.organization.team.service;

import java.util.List;
import java.util.Map;

import org.module.organization.team.dao.OrganizationSiteMapper;
import org.module.organization.team.domain.OrganizationSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationSiteService {

	@Autowired
	private OrganizationSiteMapper organizationSiteMapper;


	public List<Map<String, Object>> getList(OrganizationSite organizationSite) {
		return organizationSiteMapper.getList(organizationSite);
	}

}
