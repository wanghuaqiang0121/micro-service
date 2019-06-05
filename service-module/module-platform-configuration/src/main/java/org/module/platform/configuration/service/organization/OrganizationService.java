package org.module.platform.configuration.service.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.OrganizationMapper;
import org.module.platform.configuration.domain.Organization;
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

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organization
	* @return 
	* @date 2018年3月16日
	* @version 1.0
	* @description 添加机构
	*/
	public int insert(Organization organization) {
		return organizationMapper.insert(organization);
	}

	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organization
	* @return 
	* @date 2018年3月16日
	* @version 1.0
	* @description 查询是否存在重复
	*/
	public Map<String, Object> getRepeat(Organization organization) {
		return organizationMapper.getRepeat(organization);
	}


	public Map<String, Object> getOne(Organization organization) {
		return organizationMapper.getOne(organization);
	}


	public int update(Organization organization) {
		return organizationMapper.update(organization);
	}


	public List<Map<String, Object>> getList(Organization organization) {
		return organizationMapper.getList(organization);
	}
	
	
	public List<Map<String, Object>>  getTeams(Organization organization){
		return organizationMapper.getTeams(organization);
	}
	
}
