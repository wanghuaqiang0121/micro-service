package org.module.user.dao.itv;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.module.user.domain.itv.OrganizationUser;
import org.service.core.dao.IBaseMapper;

@Mapper
public interface OrganizationUserMapper extends IBaseMapper<OrganizationUser> {

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUser
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description 根据电话号码查询医生
	*/
	Map<String, Object> getDoctorByPhone(OrganizationUser organizationUser);

	Map<String, Object> getLoginMsg(OrganizationUser organizationUser);

	List<Map<String, Object>> getOrganizationAndTeams(OrganizationUser organizationUser);

}
