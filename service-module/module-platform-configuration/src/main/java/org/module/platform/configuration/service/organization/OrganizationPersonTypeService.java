package org.module.platform.configuration.service.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.organization.OrganizationPersonTypeMapper;
import org.module.platform.configuration.domain.organization.OrganizationPersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationPersonTypeService {
	
	@Autowired
	private OrganizationPersonTypeMapper organizationPersonTypeMapper;
	
	
	public List<Map<String, Object>> getOrganizationPersonTypeList(OrganizationPersonType organizationPersonType){
		return organizationPersonTypeMapper.getList(organizationPersonType);
	}
	
	
	public Map<String, Object> getRepeat(OrganizationPersonType organizationPersonType){
		return organizationPersonTypeMapper.getRepeat(organizationPersonType);
	}
	
	
	public int insertOrganizationPersonType(OrganizationPersonType organizationPersonType){
		return organizationPersonTypeMapper.insert(organizationPersonType);
	}
	
	
	public int updateOrganizationPersonType(OrganizationPersonType organizationPersonType){
		return organizationPersonTypeMapper.update(organizationPersonType);
	}
	
	
	public int deleteOrganizationPersonType(OrganizationPersonType organizationPersonType){
		return organizationPersonTypeMapper.delete(organizationPersonType);
	}


	public Map<String, Object> getOne(OrganizationPersonType organizationPersonType) {
		return organizationPersonTypeMapper.getOne(organizationPersonType);
	}
}
