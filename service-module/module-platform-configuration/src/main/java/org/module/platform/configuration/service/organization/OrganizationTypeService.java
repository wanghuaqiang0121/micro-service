package org.module.platform.configuration.service.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.OrganizationTypeMapper;
import org.module.platform.configuration.domain.OrganizationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月16日
 * @Version 
 * @Description 机构类型
 */
@Service
public class OrganizationTypeService {

	@Autowired
	private OrganizationTypeMapper organizationTypeMapper;

	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationType
	* @return 
	* @date 2018年3月16日
	* @version 1.0
	* @description 查询详情
	*/
	public Map<String, Object> getOne(OrganizationType organizationType) {
		return organizationTypeMapper.getOne(organizationType);
	}


	public Map<String, Object> getRepeat(OrganizationType organizationType) {
		return organizationTypeMapper.getRepeat(organizationType);
	}


	public int insert(OrganizationType organizationType) {
		return organizationTypeMapper.insert(organizationType);
	}


	public int update(OrganizationType organizationType) {
		return organizationTypeMapper.update(organizationType);
	}


	public int delete(OrganizationType organizationType) {
		return organizationTypeMapper.delete(organizationType);
	}


	public List<Map<String, Object>> getList(OrganizationType organizationType) {
		return organizationTypeMapper.getList(organizationType);
	}
	
}
