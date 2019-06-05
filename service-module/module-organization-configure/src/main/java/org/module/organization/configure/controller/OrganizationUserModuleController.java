package org.module.organization.configure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationUserModule;
import org.module.organization.configure.domain.doctor.DoctorOrganizationDepartmentDuty;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.global.OrganizationUserStatusCode;
import org.module.organization.configure.service.OrganizationUserModuleService;
import org.module.organization.configure.service.doctor.DoctorOrganizationDepartmentDutyService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年4月18日
 * @Version 
 * @Description 机构用户模块关联
 */
@RestController
public class OrganizationUserModuleController {

	@Autowired
	private OrganizationUserModuleService organizationUserModuleService;
	@Autowired
	private DoctorOrganizationDepartmentDutyService doctorOrganizationDepartmentDutyService;
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserModule
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 添加机构用户模块关联
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationUserModule:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/module" }, method = RequestMethod.POST)
	public JsonApi organizationUserModuleInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationUserModule organizationUserModule, BindingResult result) {
		
		Map<String, Object> organizationUserModuleMap = organizationUserModuleService.getRepeat(organizationUserModule);
		if (organizationUserModuleMap!=null && !organizationUserModuleMap.isEmpty()) {
			OrganizationUserModule organizationUserModuleNew = new OrganizationUserModule();
			organizationUserModuleNew.setId((Integer)organizationUserModuleMap.get("id"));
			organizationUserModuleNew.setStatus(OrganizationUserStatusCode.organizationUserModule.ENABLE.getValue());
			if (organizationUserModuleService.update(organizationUserModuleNew)>0) {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("id", organizationUserModuleMap.get("id"));
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}else {
			organizationUserModule.setStatus(OrganizationUserStatusCode.organizationUserModule.ENABLE.getValue());
			if (organizationUserModuleService.insert(organizationUserModule)>0) {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("id", organizationUserModule.getId());
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserModule
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 查询机构用户和模块关联列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationUserModule:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/modules" }, method = RequestMethod.GET)
	public JsonApi organizationUserModuleList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationUserModule organizationUserModule, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		// 设置状态为启用
		organizationUserModule.setStatus(OrganizationUserStatusCode.organizationUserModule.ENABLE.getValue());
		// 设置机构id
		organizationUserModule.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationUserModule.getPage(), organizationUserModule.getPageSize());
		List<Map<String, Object>> organizationUserModuleList = organizationUserModuleService.getList(organizationUserModule);
		if (organizationUserModuleList !=null && !organizationUserModuleList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationUserModuleList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organizationUserModule
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 机构用户在该机构下拥有和未拥有的模块列表（判断是否是超级管理员）
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationUserModule:haveAndNotHaveList" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/haveAndNotHaveListmodules" }, method = RequestMethod.GET)
	public JsonApi haveAndNotHaveModules(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationUserModule organizationUserModule, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		// 设置状态为启用
		organizationUserModule.setStatus(OrganizationUserStatusCode.organizationUserModule.ENABLE.getValue());
		// 设置机构id
		organizationUserModule.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationUserModule.getPage(), organizationUserModule.getPageSize());
		// 机构用户在该机构下拥有和未拥有的模块列表
		List<Map<String, Object>> organizationSiteList = organizationUserModuleService.haveAndNotHaveModules(organizationUserModule);
		if (organizationSiteList !=null && !organizationSiteList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationSiteList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organizationUserModule
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 机构用户在该机构下拥有的模块列表（判断是否是超级管理员）
	 */
	@RequiresAuthentication(authc=true, value = { "organization:configure:organizationUserModule:haveList" })
	@RequestMapping(value = { "/organization/user/haveModules" }, method = RequestMethod.GET)
	public JsonApi haveModules(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationUserModule organizationUserModule, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		
		DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty = new DoctorOrganizationDepartmentDuty();
		doctorOrganizationDepartmentDuty.setOrganizationUserId(organizationUserModule.getOrganizationUserId());
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		Map<String, Object> doctorOrganizationDepartmentDutyOneMap = doctorOrganizationDepartmentDutyService.getOne(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyOneMap!=null && !doctorOrganizationDepartmentDutyOneMap.isEmpty()) {
			boolean isManager = (boolean) doctorOrganizationDepartmentDutyOneMap.get("isManager");
			// 如果是管理员
			if (isManager) {
				// 查询当前机构的模块
				// 设置机构id
				organizationUserModule.setOrganizationId(organizationId);
				// 设置状态为启用
				organizationUserModule.setStatus(OrganizationUserStatusCode.organizationUserModule.ENABLE.getValue());
				Page<?> page = PageHelper.startPage(organizationUserModule.getPage(), organizationUserModule.getPageSize());
				// 机构拥有的模块列表
				List<Map<String, Object>> organizationSiteList = organizationUserModuleService.organizationHaveModules(organizationUserModule);
				if (organizationSiteList !=null && !organizationSiteList.isEmpty()) {
					return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationSiteList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}else if (!isManager) {
				// 设置状态为启用
				organizationUserModule.setStatus(OrganizationUserStatusCode.organizationUserModule.ENABLE.getValue());
				// 设置机构id
				organizationUserModule.setOrganizationId(organizationId);
				Page<?> page = PageHelper.startPage(organizationUserModule.getPage(), organizationUserModule.getPageSize());
				// 机构用户在该机构下拥有的模块列表
				List<Map<String, Object>> organizationSiteList = organizationUserModuleService.haveModules(organizationUserModule);
				if (organizationSiteList !=null && !organizationSiteList.isEmpty() ) {
					return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationSiteList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationUserModule
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 删除机构用户模块关联（假删除）
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationUserModule:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/module/{id}" }, method = RequestMethod.DELETE)
	public JsonApi organizationUserModuleDelete(@PathVariable("id") Integer id,
			@Validated({ BaseEntity.Delete.class }) OrganizationUserModule organizationUserModule, BindingResult result) {
		// 设置id
		organizationUserModule.setId(id);
		// 设置状态为禁用
		organizationUserModule.setStatus(OrganizationUserStatusCode.organizationUserModule.DISABLE.getValue());
		// 删除机构用户模块关联（假删除）
		if (organizationUserModuleService.update(organizationUserModule) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	
}
