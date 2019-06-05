package org.module.user.service;

import java.util.List;
import java.util.Map;

import org.module.user.dao.itv.DoctorOrganizationDepartmentDutyMapper;
import org.module.user.domain.itv.DoctorOrganizationDepartmentDuty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorOrganizationDepartmentDutyService {

	@Autowired
	private DoctorOrganizationDepartmentDutyMapper mapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorOrganizationDepartmentDuty
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description  机构的医生列表
	*/
	public List<Map<String, Object>> getOrganizationMemberList(
			DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty) {
		return mapper.getOrganizationMemberList(doctorOrganizationDepartmentDuty);
	}
	
	
}
