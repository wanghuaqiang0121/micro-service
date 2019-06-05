package org.module.user.dao.itv;

import java.util.List;
import java.util.Map;

import org.module.user.domain.itv.DoctorOrganizationDepartmentDuty;
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
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorOrganizationDepartmentDuty
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 机构的医生列表
	*/
	public List<Map<String, Object>> getOrganizationMemberList(
			DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty);

}
