package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationPersonTypeMapper;
import org.module.organization.configure.domain.OrganizationPersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationPersonTypeService {

	@Autowired
	private OrganizationPersonTypeMapper organizationPersonTypeMapper;

	public List<Map<String, Object>> getList(OrganizationPersonType organizationPersonType) {
		return organizationPersonTypeMapper.getList(organizationPersonType);
	}
}
