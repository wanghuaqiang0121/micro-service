package org.module.organization.team.service;

import java.util.List;
import java.util.Map;

import org.module.organization.team.dao.OrganizationMapper;
import org.module.organization.team.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月16日
 * @Version 
 * @Description 机构
 */
@Service
public class OrganizationService {
	
	@Autowired
	private OrganizationMapper organizationMapper;



	public List<Map<String, Object>> getList(Organization organization) {
		return organizationMapper.getList(organization);
	}
	
}
