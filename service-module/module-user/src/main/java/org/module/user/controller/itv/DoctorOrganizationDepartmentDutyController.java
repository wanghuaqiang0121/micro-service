package org.module.user.controller.itv;

import java.util.List;
import java.util.Map;

import org.module.user.domain.itv.DoctorOrganizationDepartmentDuty;
import org.module.user.service.DoctorOrganizationDepartmentDutyService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月15日
 * @Version 
 * @Description 医生机构部门关联
 */
@RestController
public class DoctorOrganizationDepartmentDutyController {
	
	@Autowired
	private DoctorOrganizationDepartmentDutyService doctorOrganizationDepartmentDutyService;

	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorOrganizationDepartmentDuty
	* @param result
	* @return 
	* @date 2018年4月11日
	* @version 1.0
	* @description 机构的医生列表
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:doctorOrganizationDepartmentDuty:member:list" })
	@RequestMapping(value = { "/doctor/organization/department/duty/members" }, method = RequestMethod.GET)
	public JsonApi getOrganizationMemberList(
			@Validated({ BaseEntity.SelectAll.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result){
		doctorOrganizationDepartmentDuty.setOrganizationId(doctorOrganizationDepartmentDuty.getOrganizationId());
		Page<?> page = PageHelper.startPage(doctorOrganizationDepartmentDuty.getPage(), doctorOrganizationDepartmentDuty.getPageSize());
		// 机构的医生列表
		List<Map<String, Object>>  doctorOrganizationDepartmentDutyList=doctorOrganizationDepartmentDutyService.getOrganizationMemberList(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyList!=null && doctorOrganizationDepartmentDutyList.size()>0) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), doctorOrganizationDepartmentDutyList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	
	
}
