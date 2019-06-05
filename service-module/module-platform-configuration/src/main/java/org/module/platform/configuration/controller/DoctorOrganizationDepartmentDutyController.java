package org.module.platform.configuration.controller;

import java.util.Map;

import org.module.platform.configuration.domain.organization.DoctorOrganizationDepartmentDuty;
import org.module.platform.configuration.global.BaseGlobal;
import org.module.platform.configuration.global.OrganizationStatusCode;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.organization.DoctorOrganizationDepartmentDutyService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月14日
 * @Version 
 * @Description 医生机构部门关联表
 */
@RestController
public class DoctorOrganizationDepartmentDutyController {
	@Autowired
	private DoctorOrganizationDepartmentDutyService doctorOrganizationDepartmentDutyService;

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @param result
	 * @return
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 新增医生机构部门关联表
	 */
	@RequiresAuthentication(value = { "platform:configure:doctorOrganizationDepartmentDuty:insert" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/duty" }, method = RequestMethod.POST)
	public JsonApi insert(@RequestBody @Validated({ BaseEntity.Insert.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		doctorOrganizationDepartmentDuty.setIsManager(true);
		doctorOrganizationDepartmentDuty.setStatus(OrganizationStatusCode.Organization.ENABLE.getValue());
		Map<String, Object>  resultMap=doctorOrganizationDepartmentDutyService.getRepeat(doctorOrganizationDepartmentDuty);
		if (resultMap!=null && !resultMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.user.organization.is.exists"));
		}
		if (doctorOrganizationDepartmentDutyService.insert(doctorOrganizationDepartmentDuty)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}	
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param doctorOrganizationDepartmentDuty
	* @param result
	* @return 
	* @date 2018年8月28日
	* @version 1.0
	* @description 查询管理员关联信息
	*/
	@RequiresAuthentication(ignore = true, value = { "platform:configure:doctorOrganizationDepartmentDuty:detail" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/duty/{organizationUserId}"}, method = RequestMethod.GET)
	public JsonApi getOne(@PathVariable("organizationUserId") Integer organizationUserId, @Validated({ BaseEntity.SelectOne.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result){
		doctorOrganizationDepartmentDuty.setOrganizationUserId(organizationUserId);
		Map<String, Object>  resultMap = doctorOrganizationDepartmentDutyService.getOne(doctorOrganizationDepartmentDuty);
		if (resultMap!=null && !resultMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,resultMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}	
}
