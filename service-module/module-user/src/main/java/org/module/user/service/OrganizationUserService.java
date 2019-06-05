package org.module.user.service;

import java.util.List;
import java.util.Map;

import org.module.user.dao.itv.OrganizationUserMapper;
import org.module.user.domain.itv.OrganizationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrganizationUserService {
	@Autowired
	private OrganizationUserMapper organizationUserMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUser
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description 根据电话号码查询医生
	*/
	public Map<String, Object> getDoctorByPhone(OrganizationUser organizationUser) {
		return organizationUserMapper.getDoctorByPhone(organizationUser);
	}

	public Map<String, Object> getLoginMsg(OrganizationUser organizationUser) {
		return organizationUserMapper.getLoginMsg(organizationUser);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUser
	* @return 
	* @date 2018年5月14日
	* @version 1.0
	* @description 查询医生所在机构对应的团队
	*/
	public List<Map<String, Object>> getOrganizationAndTeams(OrganizationUser organizationUser) {
		return organizationUserMapper.getOrganizationAndTeams(organizationUser);
	}

	
}
