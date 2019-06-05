package org.module.organization.user.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.user.domain.DoctorOrganizationDepartmentDuty;
import org.service.core.dao.IBaseMapper;

public interface DoctorOrganizationDepartmentDutyMapper extends IBaseMapper<DoctorOrganizationDepartmentDuty> {
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 机构的成员列表
	 */
	public	List<Map<String, Object>> getOrganizationMember(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty);

	public List<Map<String, Object>> getOrganizationMemberIsNullList(
			DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty);

}
