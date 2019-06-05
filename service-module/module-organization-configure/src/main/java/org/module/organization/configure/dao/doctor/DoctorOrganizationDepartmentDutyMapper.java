package org.module.organization.configure.dao.doctor;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.doctor.DoctorOrganizationDepartmentDuty;
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

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @return
	 * @date 2018年8月14日
	 * @version 1.0
	 * @description 团队没有的机构成员列表
	 */
	public List<Map<String, Object>> getOrganizationMemberIsNullList(
			DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty);

	public Map<String, Object> getDoctorOrganizationDepartmentDutyOne(
			DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty);

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @return
	 * @date 2018年11月14日
	 * @version 1.0
	 * @description 不在该机构下的成员
	 */
	public  List<Map<String, Object>> getOrganizationNotMember(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty);
	
	
	public  List<Map<String, Object>> getOrganizationUserNotTeamMember(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty);
}
