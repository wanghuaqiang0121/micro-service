package org.module.platform.configuration.service.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.organization.DoctorOrganizationDepartmentDutyMapper;
import org.module.platform.configuration.domain.organization.DoctorOrganizationDepartmentDuty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorOrganizationDepartmentDutyService {
	
	@Autowired
	private DoctorOrganizationDepartmentDutyMapper mapper;
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @return
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 新增
	 */
	public int insert(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty){
		return mapper.insert(doctorOrganizationDepartmentDuty);
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @return
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 删除(三个字段)
	 */
	public int delete(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty){
		return mapper.delete(doctorOrganizationDepartmentDuty);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @return
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 数据是否重复
	 */
	public	Map<String, Object> getRepeat(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty) {
		return mapper.getRepeat(doctorOrganizationDepartmentDuty);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @return
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 数据列表
	 */
	public	List<Map<String, Object>> getList(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty){
		return mapper.getList(doctorOrganizationDepartmentDuty);
	}


	
	public Map<String, Object> getOne(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty) {
		return mapper.getOne(doctorOrganizationDepartmentDuty);
	}


	public int update(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty) {
		return mapper.update(doctorOrganizationDepartmentDuty);
	}


}
