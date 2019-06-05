package org.module.organization.configure.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.Department;
import org.module.organization.configure.domain.doctor.DoctorOrganizationDepartmentDuty;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.DepartmentService;
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
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月16日
 * @Version 
 * @Description 部门接口
 */
@RestController
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DoctorOrganizationDepartmentDutyService doctorOrganizationDepartmentDutyService;
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param department
	 * @param result
	 * @return {@link JsonApi}
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 新增部门
	 */
	@RequiresAuthentication( value = { "organization:configure:department:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/department" }, method = RequestMethod.POST)
	public JsonApi insert(@RequestBody @Validated({ BaseEntity.Insert.class }) Department  department,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		/*设置时间*/
		department.setCreateDate(new Date());
		department.setOrganizationId(organizationId);
		/*判断部门是否重复*/
		Map<String, Object> departmentMap = departmentService.getRepeat(department);
		if (departmentMap!=null && !departmentMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("department.name.is.conflict"));
		}
		if (departmentService.insert(department)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param department
	 * @param result
	 * @return {@link JsonApi}
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 修改部门
	 */
	@RequiresAuthentication(value = { "organization:configure:department:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/department/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(@PathVariable("id") Integer id,@RequestBody @Validated({ BaseEntity.Update.class }) Department  department,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		/*判断数据是否存在*/
		department.setId(id);
		Map<String, Object> departmentMap = departmentService.getOne(department);
		if (departmentMap==null || departmentMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		/*判断部门是否重复 名字不能重复*/
		department.setOrganizationId(organizationId);
		Map<String, Object> departmentRepeatMap = departmentService.getRepeat(department);
		if (departmentRepeatMap!=null && !departmentRepeatMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("department.name.is.conflict"));
		}
		if (departmentService.update(department)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param department
	 * @param result
	 * @return {@link JsonApi}
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 查询部门详情
	 */
	@RequiresAuthentication(value = { "organization:configure:department:detail" },level=Level.OPERATION)
	@RequestMapping(value = { "/department/{id}" }, method = RequestMethod.GET)
	public JsonApi getDepartmentDetail(
			@PathVariable("id") Integer id,@Validated({ BaseEntity.SelectOne.class }) Department  department,BindingResult result){
		/*设置主键*/
		department.setId(id);
		Map<String, Object> departmentMap = departmentService.getOne(department);
		if (departmentMap!=null && !departmentMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,departmentMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param department
	 * @param result
	 * @return {@link JsonApi}
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 查询部门列表
	 */
	@RequiresAuthentication( value = { "organization:configure:department:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/departments" }, method = RequestMethod.GET)
	public JsonApi getDepartmentList(@Validated({ BaseEntity.SelectAll.class }) Department  department,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId ){
		department.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(department.getPage(), department.getPageSize());
		List<Map<String, Object>>  departmentList=departmentService.getList(department);
		if (departmentList!=null && !departmentList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), departmentList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param department
	 * @param result
	 * @return {@link JsonApi}
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 删除部门
	 */
	@RequiresAuthentication( value = { "organization:configure:department:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/department/{id}" }, method = RequestMethod.DELETE)
	public JsonApi delete(@PathVariable("id") Integer id,@Validated({ BaseEntity.Delete.class }) Department  department,BindingResult result){
		/*判断数据是否存在*/
		department.setId(id);
		Map<String, Object> departmentMap = departmentService.getOne(department);
		if (departmentMap==null || departmentMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		/*判断机构是否已被使用*/
		DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty=new DoctorOrganizationDepartmentDuty();
		doctorOrganizationDepartmentDuty.setDepartmentId(id);
		List<Map<String, Object>>  doctorOrganizationDepartmentDutyList=doctorOrganizationDepartmentDutyService.getList(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyList!=null && !doctorOrganizationDepartmentDutyList.isEmpty()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("department.is.used"));
		}
		/*删除部门*/
		if (departmentService.delete(department)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
