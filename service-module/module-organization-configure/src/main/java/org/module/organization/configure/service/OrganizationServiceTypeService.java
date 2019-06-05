package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationServiceTypeMapper;
import org.module.organization.configure.domain.OrganizationServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceTypeService {

	@Autowired
	private OrganizationServiceTypeMapper organizationServiceTypeMapper;
	
	public List<Map<String, Object>> getList(OrganizationServiceType organizationServiceType) {
		return organizationServiceTypeMapper.getList(organizationServiceType);
	}

	public Map<String, Object> getRepeat(OrganizationServiceType organizationServiceType) {
		return organizationServiceTypeMapper.getRepeat(organizationServiceType);
	}

	public int insert(OrganizationServiceType organizationServiceType) {
		return organizationServiceTypeMapper.insert(organizationServiceType);
	}

	public int delete(OrganizationServiceType organizationServiceType) {
		return organizationServiceTypeMapper.delete(organizationServiceType);
	}

	public List<Map<String, Object>> getOrganizationIsNullServiceTypeList(
			OrganizationServiceType organizationServiceType) {
		return organizationServiceTypeMapper.getOrganizationIsNullServiceTypeList(organizationServiceType);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationServiceType
	 * @return
	 * @date 2018年6月6日
	 * @version 1.0
	 * @description 查询机构拥有和未拥有的服务类型
	 */
	public List<Map<String, Object>> getOrganizationServiceTypeIsChoose(OrganizationServiceType organizationServiceType){
		return organizationServiceTypeMapper.getOrganizationServiceTypeIsChoose(organizationServiceType);
	}

}
