package org.module.platform.configuration.service.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.organization.OrganizationTypeUserCategoryMapper;
import org.module.platform.configuration.domain.organization.OrganizationTypeUserCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTypeUserCategoryService {
	
	@Autowired
	private OrganizationTypeUserCategoryMapper organizationTypeUserCategoryMapper;
	
	
	public List<Map<String, Object>> getOrganizationTypeUserCategoryList(OrganizationTypeUserCategory organizationTypeUserCategory){
		return organizationTypeUserCategoryMapper.getList(organizationTypeUserCategory);
	}
	
	public Map<String, Object> getRepeat(OrganizationTypeUserCategory organizationTypeUserCategory){
		return organizationTypeUserCategoryMapper.getRepeat(organizationTypeUserCategory);
	}
	
	public int insertOrganizationTypeUserCategory(OrganizationTypeUserCategory organizationTypeUserCategory){
		return organizationTypeUserCategoryMapper.insert(organizationTypeUserCategory);
	}
	
	public int deleteOrganizationTypeUserCategory(OrganizationTypeUserCategory organizationTypeUserCategory){
		return organizationTypeUserCategoryMapper.delete(organizationTypeUserCategory);
	}
}
