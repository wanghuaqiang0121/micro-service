package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationSiteMapper;
import org.module.organization.configure.domain.OrganizationSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationSiteService {

	@Autowired
	private OrganizationSiteMapper organizationSiteMapper;

	public Map<String, Object> getRepeat(OrganizationSite organizationSite) {
		return organizationSiteMapper.getRepeat(organizationSite);
	}

	public int insert(OrganizationSite organizationSite) {
		return organizationSiteMapper.insert(organizationSite);
	}

	public Map<String, Object> getOne(OrganizationSite organizationSite) {
		return organizationSiteMapper.getOne(organizationSite);
	}

	public List<Map<String, Object>> getList(OrganizationSite organizationSite) {
		return organizationSiteMapper.getList(organizationSite);
	}

	public int update(OrganizationSite organizationSite) {
		return organizationSiteMapper.update(organizationSite);
	}
}
