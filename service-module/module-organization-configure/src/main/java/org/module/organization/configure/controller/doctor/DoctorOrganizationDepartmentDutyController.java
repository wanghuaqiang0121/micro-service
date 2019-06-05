package org.module.organization.configure.controller.doctor;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.doctor.DoctorInfo;
import org.module.organization.configure.domain.doctor.DoctorOrganizationDepartmentDuty;
import org.module.organization.configure.domain.doctor.OrganizationUser;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.global.OrganizationUserStatusCode;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.doctor.DoctorInfoService;
import org.module.organization.configure.service.doctor.DoctorOrganizationDepartmentDutyService;
import org.module.organization.configure.service.doctor.OrganizationUserService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

@RestController
public class DoctorOrganizationDepartmentDutyController {
	@Autowired
	private DoctorOrganizationDepartmentDutyService doctorOrganizationDepartmentDutyService;
	@Autowired
	private OrganizationUserService organizationUserService;
	@Autowired
	private DoctorInfoService doctorInfoService;
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @param result
	 * @return
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 新增机构,用户,部门关联表
	 */
	@RequiresAuthentication(value = { "organization:configure:doctorOrganizationDepartmentDuty:insert" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/duty" }, method = RequestMethod.POST)
	public JsonApi insert(@RequestBody @Validated({ BaseEntity.Insert.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId
			){
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		doctorOrganizationDepartmentDuty.setIsManager(false);
		doctorOrganizationDepartmentDuty.setIsLocal(true);
		Map<String, Object>  DoctorOrganizationDepartmentDutyMap=doctorOrganizationDepartmentDutyService.getRepeat(doctorOrganizationDepartmentDuty);
		if (DoctorOrganizationDepartmentDutyMap!=null && !DoctorOrganizationDepartmentDutyMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.user.organization.is.exists"));
		}
		if (doctorOrganizationDepartmentDutyService.insert(doctorOrganizationDepartmentDuty)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 删除机构,用户,部门关联表
	 */
	@RequiresAuthentication( value = { "organization:configure:doctorOrganizationDepartmentDuty:delete" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/{organizationUserId}/organization/{organizationId}/department/{departmentId}/duty" }, method = RequestMethod.DELETE)
	public JsonApi delete( 
			@PathVariable("organizationUserId") Integer organizationUserId,
			@PathVariable("organizationId") Integer organizationIdd,
			@PathVariable("departmentId") Integer departmentId,
			@Validated({ BaseEntity.Delete.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		/*设置数据*/
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		doctorOrganizationDepartmentDuty.setOrganizationUserId(organizationUserId);
		doctorOrganizationDepartmentDuty.setDepartmentId(departmentId);
		if (doctorOrganizationDepartmentDutyService.delete(doctorOrganizationDepartmentDuty)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 机构,用户,部门关联表列表
	 */
	@RequiresAuthentication( value = { "organization:configure:doctorOrganizationDepartmentDuty:list" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/dutys" }, method = RequestMethod.GET)
	public JsonApi getList(@Validated({ BaseEntity.SelectAll.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId ){
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(doctorOrganizationDepartmentDuty.getPage(), doctorOrganizationDepartmentDuty.getPageSize());
		List<Map<String, Object>>  doctorOrganizationDepartmentDutyList=doctorOrganizationDepartmentDutyService.getList(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyList!=null && !doctorOrganizationDepartmentDutyList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), doctorOrganizationDepartmentDutyList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorOrganizationDepartmentDuty
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 机构的成员列表
	 */
	@RequiresAuthentication(value = { "organization:configure:doctorOrganizationDepartmentDuty:member:list" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/duty/members" }, method = RequestMethod.GET)
	public JsonApi getOrganizationMemberList(@Validated({ BaseEntity.SelectAll.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(doctorOrganizationDepartmentDuty.getPage(), doctorOrganizationDepartmentDuty.getPageSize());
		List<Map<String, Object>>  doctorOrganizationDepartmentDutyList=doctorOrganizationDepartmentDutyService.getOrganizationMemberList(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyList!=null && !doctorOrganizationDepartmentDutyList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), doctorOrganizationDepartmentDutyList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * 
	* @author <font color="green"><b>Chen.Yan</b></font>
	* @param doctorOrganizationDepartmentDuty
	* @param result
	* @param organizationId
	* @return 
	* @date 2018年11月14日
	* @version 1.0
	* @description 不在该机构下的成员
	 */
	@RequiresAuthentication(authc = true,value = { "organization:configure:doctorOrganizationDepartmentDuty:not:member:list" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/duty/not/members" }, method = RequestMethod.GET)
	public JsonApi getOrganizationNotMemberList(@Validated({ BaseEntity.SelectAll.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(doctorOrganizationDepartmentDuty.getPage(), doctorOrganizationDepartmentDuty.getPageSize());
		List<Map<String, Object>>  doctorOrganizationDepartmentDutyList=doctorOrganizationDepartmentDutyService.getOrganizationNotMember(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyList!=null && !doctorOrganizationDepartmentDutyList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), doctorOrganizationDepartmentDutyList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationTeamId
	* @param doctorOrganizationDepartmentDuty
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月17日
	* @version 1.0
	* @description 查询团队没有的机构成员列表
	*/
	@RequiresAuthentication( value = { "organization:configure:doctorOrganizationDepartmentDuty:isNull:list" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/duty/{organizationTeamId}/members" }, method = RequestMethod.GET)
	public JsonApi getOrganizationMemberIsNullList(
			@PathVariable("organizationTeamId")Integer organizationTeamId,
			@Validated({ BaseEntity.SelectAll.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		doctorOrganizationDepartmentDuty.setOrganizationTeamId(organizationTeamId);
		Page<?> page = PageHelper.startPage(doctorOrganizationDepartmentDuty.getPage(), doctorOrganizationDepartmentDuty.getPageSize());
		List<Map<String, Object>>  doctorOrganizationDepartmentDutyList=doctorOrganizationDepartmentDutyService.getOrganizationMemberIsNullList(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyList!=null && !doctorOrganizationDepartmentDutyList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), doctorOrganizationDepartmentDutyList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param doctorOrganizationDepartmentDuty
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月23日
	 * @version 1.0
	 * @description 修改机构,用户,部门关联表
	 */
	@RequiresAuthentication(value = { "organization:configure:doctorOrganizationDepartmentDuty:update" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/duty/{id}" }, method = RequestMethod.PUT)
	@Transactional
	public JsonApi update(@PathVariable("id") Integer id,
			@RequestBody @Validated({ BaseEntity.Update.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result){
		doctorOrganizationDepartmentDuty.setId(id);
		//查看数据是否存在
		Map<String, Object> doctorOrganizationDepartmentDutyMap = doctorOrganizationDepartmentDutyService.getDoctorOrganizationDepartmentDutyOne(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyMap==null || doctorOrganizationDepartmentDutyMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("doctor.organization.department.duty.not.found"));
		}
		//设置机构用户id
		Integer organizationUserId = (Integer) doctorOrganizationDepartmentDutyMap.get("organizationUserId");
		OrganizationUser organizationUser = doctorOrganizationDepartmentDuty.getOrganizationUser();
		if (null == organizationUser) {
			organizationUser = new OrganizationUser();
		}
		//不能修改身份证和账号		
		organizationUser.setAccount(null);
		organizationUser.setId(organizationUserId);
		Map<String, Object> organizationUserMap = organizationUserService.getOne(organizationUser);
		if (organizationUserMap==null || organizationUserMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("organization.user.one.not.found"));
		}
		Integer doctorInfoId = (Integer) organizationUserMap.get("doctorInfoId");
		//判断是否需要修改执行证号
		DoctorInfo doctorInfo = doctorOrganizationDepartmentDuty.getDoctorInfo();
		if (null == doctorInfo) {
			doctorInfo = new DoctorInfo();
		}
		doctorInfo.setId(doctorInfoId);
		String certification = doctorInfo.getCertification();
		if (null != certification && !"".equals(certification)) {
			Map<String, Object> doctorInfoMap = doctorInfoService.getRepeat(doctorInfo);
			if (doctorInfoMap!=null && !doctorInfoMap.isEmpty()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("doctor.info.is.conflict"));
			}
		}
		//修改数据
		if (doctorOrganizationDepartmentDutyService.update(doctorOrganizationDepartmentDuty)>0) {
			if (organizationUserService.update(organizationUser) >0) {
				if (doctorInfoId!=null) {
					
					if (doctorInfoService.update(doctorInfo) >0) {
						return new JsonApi(ApiCode.OK);
					}
					throw new RuntimeException();
				}
				return new JsonApi(ApiCode.OK);
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/**
	 * 
	* @author <font color="green"><b>Chen.Yan</b></font>
	* @param doctorOrganizationDepartmentDuty
	* @param result
	* @param organizationId
	* @return 
	* @date 2018年11月14日
	* @version 1.0
	* @description 添加外部机构成员
	 */
	@RequiresAuthentication(authc = true,value = { "organization:configure:doctorOrganizationDepartmentDuty:insert:external" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/duty/external" }, method = RequestMethod.POST)
	public JsonApi insertExternal(@RequestBody @Validated({ BaseEntity.Insert.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId
			){
		doctorOrganizationDepartmentDuty.setOrganizationUser(doctorOrganizationDepartmentDuty.getOrganizationUser());
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		doctorOrganizationDepartmentDuty.setIsManager(false);
		doctorOrganizationDepartmentDuty.setIsLocal(false);
		doctorOrganizationDepartmentDuty.setStatus(OrganizationUserStatusCode.OrganizationUser.ENABLE.getValue());
		Map<String, Object>  DoctorOrganizationDepartmentDutyMap=doctorOrganizationDepartmentDutyService.getRepeat(doctorOrganizationDepartmentDuty);
		if (DoctorOrganizationDepartmentDutyMap!=null && !DoctorOrganizationDepartmentDutyMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.user.organization.is.exists"));
		}
		if (doctorOrganizationDepartmentDutyService.insert(doctorOrganizationDepartmentDuty)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/**
	 * 
	* @author <font color="green"><b>Chen.Yan</b></font>
	* @param doctorOrganizationDepartmentDuty
	* @param result
	* @param organizationId
	* @return 
	* @date 2018年11月14日
	* @version 1.0
	* @description 查询在该机构下的用户并且不在某个团队
	 */
	@RequiresAuthentication(authc = true,value = { "organization:configure:doctorOrganizationDepartmentDuty:member:not:team:list" },level = Level.OPERATION)
	@RequestMapping(value = { "/doctor/organization/department/duty/not/team/members" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserNotTeamMember(@Validated({ DoctorOrganizationDepartmentDuty.GetOrganizationUserNotTeamMember.class }) DoctorOrganizationDepartmentDuty  doctorOrganizationDepartmentDuty,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(doctorOrganizationDepartmentDuty.getPage(), doctorOrganizationDepartmentDuty.getPageSize());
		List<Map<String, Object>>  doctorOrganizationDepartmentDutyList=doctorOrganizationDepartmentDutyService.getOrganizationUserNotTeamMember(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyList!=null && !doctorOrganizationDepartmentDutyList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), doctorOrganizationDepartmentDutyList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
