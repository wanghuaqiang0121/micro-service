package org.module.organization.configure.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.organization.configure.domain.HospitalDepartment;
import org.module.organization.configure.domain.Organization;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.HospitalDepartmentService;
import org.module.organization.configure.service.OrganizationService;
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

@RestController
public class HospitalDepartmentController {

	@Autowired
	private HospitalDepartmentService hospitalDepartmentService;
	
	@Autowired
	private OrganizationService organizationService;
	
	
	/** 
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param hospitalDepartment
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年7月30日
	 * @version 1.0 
	 * @description 医院科室判断重复
	 * // TODO 待添加到新项目
	 */
	@RequiresAuthentication(value = { "organization:configure:hospitalDepartment:validRepeat" },level=Level.OPERATION)
	@RequestMapping(value = { "/hospital/department/valid/repeat" }, method = RequestMethod.GET)
	public JsonApi validRepeat(
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@Validated({ HospitalDepartment.Repeat.class }) HospitalDepartment  hospitalDepartment,BindingResult result){
		
		// 根据机构id查询医疗机构id
		Organization organization =new  Organization();
		organization.setId(organizationId);
		// 设置医疗机构id
		Map<String, Object> organizationMap = organizationService.getOne(organization);
		if (organizationMap == null || organizationMap.isEmpty()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organizationInfo.is.null"));
		}
		Integer medicalOrganizationInfoId = (Integer)organizationMap.get("medicalOrganizationInfoId");
		if (medicalOrganizationInfoId == null) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("medicalOrganizationInfo.is.null"));
		}
		hospitalDepartment.setMedicalOrganizationInfoId(medicalOrganizationInfoId);
		// 判断重复
		Map<String, Object> resultMap = new HashMap<>(); 
		Map<String, Object> hospitalDepartmentMap = hospitalDepartmentService.getRepeat(hospitalDepartment);
		if (MapUtils.isNotEmpty(hospitalDepartmentMap)) {
			resultMap.put("isRepeat",true);
			return new JsonApi(ApiCode.OK,resultMap).setMsg(Prompt.bundle("hospitalDepartment.name.is.conflict"));
		}
		resultMap.put("isRepeat",false);
		return new JsonApi(ApiCode.OK,resultMap);
		
	}
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param hospitalDepartment
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月30日
	* @version 1.0 
	* @description 新增医院科室表
	*/
	@RequiresAuthentication(value = { "organization:configure:hospitalDepartment:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/hospital/department" }, method = RequestMethod.POST)
	public JsonApi insert(
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestBody @Validated({ BaseEntity.Insert.class }) HospitalDepartment  hospitalDepartment,BindingResult result){
		
		// 根据机构id查询医疗机构id
		Organization organization =new  Organization();
		organization.setId(organizationId);
		// 设置医疗机构id
		Map<String, Object> organizationMap = organizationService.getOne(organization);
		if (organizationMap == null || organizationMap.isEmpty()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organizationInfo.is.null"));
		}
		Integer medicalOrganizationInfoId = (Integer)organizationMap.get("medicalOrganizationInfoId");
		if (medicalOrganizationInfoId == null) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("medicalOrganizationInfo.is.null"));
		}
		hospitalDepartment.setMedicalOrganizationInfoId(medicalOrganizationInfoId);
		// 判断重复
		Map<String, Object> hospitalDepartmentMap = hospitalDepartmentService.getRepeat(hospitalDepartment);
		if (MapUtils.isNotEmpty(hospitalDepartmentMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("hospitalDepartment.name.is.conflict"));
		}
		/*设置时间*/
		hospitalDepartment.setCreateDate(new Date());
		if (hospitalDepartmentService.insert(hospitalDepartment)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param hospitalDepartment
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月30日
	* @version 1.0
	* @description 修改医院科室表
	*/
	@RequiresAuthentication(value = { "organization:configure:hospitalDepartment:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/hospital/department/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(
			@PathVariable("id") Integer id,@RequestBody @Validated({ BaseEntity.Update.class }) HospitalDepartment  hospitalDepartment,BindingResult result
			){
		/*判断数据是否存在*/
		hospitalDepartment.setId(id);
		Map<String, Object> hospitalDepartmentMap = hospitalDepartmentService.getOne(hospitalDepartment);
		if (hospitalDepartmentMap==null || hospitalDepartmentMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		
		if (hospitalDepartmentService.update(hospitalDepartment)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param hospitalDepartment
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月30日
	* @version 1.0
	* @description 查询医院科室表详情
	*/
	@RequiresAuthentication(value = { "organization:configure:hospitalDepartment:detail" },level=Level.OPERATION)
	@RequestMapping(value = { "/hospital/department/{id}" }, method = RequestMethod.GET)
	public JsonApi getHospitalDepartmentDetail(
			@PathVariable("id") Integer id,@Validated({ BaseEntity.SelectOne.class }) HospitalDepartment  hospitalDepartment,BindingResult result){
		/*设置主键*/
		hospitalDepartment.setId(id);
		Map<String, Object> hospitalDepartmentMap = hospitalDepartmentService.getOne(hospitalDepartment);
		if (hospitalDepartmentMap!=null && !hospitalDepartmentMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,hospitalDepartmentMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param hospitalDepartment
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月30日
	* @version 1.0
	* @description 查询医疗科室表列表
	*/
	@RequiresAuthentication(value = { "organization:configure:hospitalDepartment:list" },level=Level.OPERATION)
	@RequestMapping( value = { "/hospital/departments" }, method = RequestMethod.GET)
	public JsonApi getHospitalDepartmentList(
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@Validated({ BaseEntity.SelectAll.class }) HospitalDepartment  hospitalDepartment,BindingResult result){
		hospitalDepartment.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(hospitalDepartment.getPage(), hospitalDepartment.getPageSize());
		// hospitalDepartment.setStatus(Department.ENABLE.getValue());
		List<Map<String, Object>>  departmentList=hospitalDepartmentService.getList(hospitalDepartment);
		if (departmentList!=null && !departmentList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), departmentList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
	
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param hospitalDepartment
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月30日
	* @version 1.0
	* @description 删除医院科室表
	*/
	@RequiresAuthentication(value = { "organization:configure:hospitalDepartment:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/hospital/department/{id}" }, method = RequestMethod.DELETE)
	public JsonApi delete(@PathVariable("id") Integer id,@Validated({ BaseEntity.Delete.class }) HospitalDepartment  hospitalDepartment,BindingResult result){
		/*判断数据是否存在*/
		hospitalDepartment.setId(id);
		Map<String, Object> hospitalDepartmentMap = hospitalDepartmentService.getOne(hospitalDepartment);
		if (hospitalDepartmentMap==null || hospitalDepartmentMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		/*删除科室*/
		if (hospitalDepartmentService.delete(hospitalDepartment)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
