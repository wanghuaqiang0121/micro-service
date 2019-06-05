package org.module.organization.configure.controller.doctor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.module.organization.configure.domain.doctor.DoctorDoctorSkill;
import org.module.organization.configure.domain.doctor.DoctorInfo;
import org.module.organization.configure.domain.doctor.DoctorSkill;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.doctor.DoctorDoctorSkillService;
import org.module.organization.configure.service.doctor.DoctorInfoService;
import org.module.organization.configure.service.doctor.DoctorSkillService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorInfoController {

	@Autowired
	private DoctorInfoService doctorInfoService;
	
	@Autowired
	private DoctorSkillService doctorSkillService;
	@Autowired
	private DoctorDoctorSkillService doctorDoctorSkillService;
	
	//@Autowired
	//private OrganizationUserPersonTypeService organizationUserPersonTypeService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param doctorInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月8日
	* @version 1.0
	* @description 医生信息详情
	*/
	@RequiresAuthentication(value = { "organization:configure:doctorInfo:detail" },level=Level.OPERATION)
	@RequestMapping(value = { "/doctor/info/{id}" }, method = RequestMethod.GET)
	public JsonApi getDoctorInfoDetail(@PathVariable("id") Integer id,
			@Validated({ BaseEntity.SelectOne.class }) DoctorInfo  doctorInfo,BindingResult result ){
		/*设置主键*/
		doctorInfo.setOrganizationUserId(id);
		Map<String, Object> doctorInfoMap = doctorInfoService.getOne(doctorInfo);
		if (doctorInfoMap!=null && !doctorInfoMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,doctorInfoMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
	
	
	/** 
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param doctorInfo
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年5月8日
	 * @version 1.0
	 * @description 验证执行证号重复
	 * * // TODO 待添加到新项目
	 */
	@RequiresAuthentication(value = { "organization:configure:doctorInfo:validCertificationRepeat" },level=Level.OPERATION)
	@RequestMapping(value = { "/doctor/info/valid/certification" }, method = RequestMethod.GET)
	public JsonApi validCertificationRepeat(
			@Validated({ DoctorInfo.ValidCertificationRepeat.class }) DoctorInfo  doctorInfo,BindingResult result){
		/*判断执行证号是否重复*/
		Map<String, Object> doctorInfoMap = doctorInfoService.getRepeat(doctorInfo);
		Map<String, Object> resultMap = new HashMap<>(); 
		if (doctorInfoMap!=null && !doctorInfoMap.isEmpty()) {
			resultMap.put("isRepeat",true);
			return new JsonApi(ApiCode.OK,resultMap).setMsg(Prompt.bundle("doctorInfo.certification.is.exists"));
		}
		resultMap.put("isRepeat",false);
		return new JsonApi(ApiCode.OK,resultMap);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param doctorInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月8日
	* @version 1.0
	* @description 新增医生信息
	*/
	@RequiresAuthentication(value = { "organization:configure:doctorInfo:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/doctor/info" }, method = RequestMethod.POST)
	@Transactional
	public JsonApi insertDoctorInfo(
			@Validated({ BaseEntity.Insert.class })@RequestBody DoctorInfo  doctorInfo,BindingResult result){
		doctorInfo.setCreateDate(new Date());
		if (StringUtils.isNotEmpty(doctorInfo.getCertification())) {
			//判断执行证号是否重复
			Map<String, Object> doctorInfoMap = doctorInfoService.getRepeat(doctorInfo);
			if (doctorInfoMap!=null && !doctorInfoMap.isEmpty()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("doctorInfo.certification.is.exists"));
			}
		}
		// 医生擅长领域
		List<DoctorSkill> doctorSkillList = doctorInfo.getDoctorSkills();
		//新增医生信息
		if (doctorInfoService.insert(doctorInfo) > 0) {
			// 新增医生级别类别
			Map<String, Object> doctorSkillResultMap = null;
			//新增医生擅长领域
			if(doctorSkillList!=null && !doctorSkillList.isEmpty()) {
				for (DoctorSkill doctorSkill : doctorSkillList) {
					// 医生擅长领域是否存在
					doctorSkillResultMap = doctorSkillService.getOne(doctorSkill);
					// 不存在 新增
					if (doctorSkillResultMap==null || doctorSkillResultMap.isEmpty()) {
						doctorSkill.setCreateDate(new Date());
						// 新增医生擅长领域
						if (doctorSkillService.insert(doctorSkill) > 0) {
							// 新增医生擅长领域关联
							DoctorDoctorSkill doctorDoctorSkill = new DoctorDoctorSkill();
							doctorDoctorSkill.setDoctorSkillId(doctorSkill.getId());
							doctorDoctorSkill.setDoctorInfoId(doctorInfo.getId());
							if (doctorDoctorSkillService.insert(doctorDoctorSkill) <= 0) {
								throw new RuntimeException();
							}
						}else{
							throw new RuntimeException();
						}
					}else{//医生擅长领域存在
						// 新增医生擅长领域关联
						DoctorDoctorSkill doctorDoctorSkill = new DoctorDoctorSkill();
						doctorDoctorSkill.setDoctorSkillId(Integer.parseInt(doctorSkillResultMap.get("id").toString()));
						doctorDoctorSkill.setDoctorInfoId(doctorInfo.getId());
						if (doctorDoctorSkillService.insert(doctorDoctorSkill) <= 0) {
							throw new RuntimeException();
						}
					}
				}
			}
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param doctorInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月8日
	* @version 1.0
	* @description 修改医生信息
	*/
	@RequiresAuthentication(value = { "organization:configure:doctorInfo:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/doctor/info/{id}" }, method = RequestMethod.PUT)
	public JsonApi updateDoctorInfo(
			@PathVariable("id") Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody DoctorInfo  doctorInfo,BindingResult result){
		doctorInfo.setId(id);
		if (null != doctorInfo.getCertification() && !"".equals(doctorInfo.getCertification())) {
			//判断执行证号是否重复
			Map<String, Object> doctorInfoMap = doctorInfoService.getRepeat(doctorInfo);
			if (doctorInfoMap!=null && !doctorInfoMap.isEmpty()) {
				Integer doctorId = (Integer) doctorInfoMap.get("id");
				if (id.intValue() != doctorId.intValue()) {
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("doctorInfo.certification.is.exists"));
				}
			}
		}
		// 医生擅长领域
		List<DoctorSkill> doctorSkillList = doctorInfo.getDoctorSkills();
		//修改数据
		if (doctorInfoService.update(doctorInfo) > 0) {
			// 删除医生擅长领域关联
			DoctorDoctorSkill doctorDoctorSkillDelete = new DoctorDoctorSkill();
			doctorDoctorSkillDelete.setDoctorInfoId(doctorInfo.getId());
			doctorDoctorSkillService.deleteByDoctorInfoId(doctorDoctorSkillDelete);
			Map<String, Object> doctorSkillResultMap = null;
			//新增医生擅长领域
			if(doctorSkillList!=null && !doctorSkillList.isEmpty()) {
				for (DoctorSkill doctorSkill : doctorSkillList) {
					// 医生擅长领域是否存在
					doctorSkillResultMap = doctorSkillService.getOne(doctorSkill);
					// 不存在 则新增
					if (doctorSkillResultMap==null || doctorSkillResultMap.isEmpty()) {
						// 新增医生擅长领域
						doctorSkill.setCreateDate(new Date());
						if (doctorSkillService.insert(doctorSkill) > 0) {
							// 新增医生擅长领域关联
							DoctorDoctorSkill doctorDoctorSkill = new DoctorDoctorSkill();
							doctorDoctorSkill.setDoctorSkillId(doctorSkill.getId());
							doctorDoctorSkill.setDoctorInfoId(doctorInfo.getId());
							if (doctorDoctorSkillService.insert(doctorDoctorSkill) <= 0) {
								throw new RuntimeException();
							}
						}else{
							throw new RuntimeException();
						}
					}else{//医生擅长领域存在
						// 新增医生擅长领域关联
						DoctorDoctorSkill doctorDoctorSkill = new DoctorDoctorSkill();
						doctorDoctorSkill.setDoctorSkillId(Integer.parseInt(doctorSkillResultMap.get("id").toString()));
						doctorDoctorSkill.setDoctorInfoId(doctorInfo.getId());
						if (doctorDoctorSkillService.insert(doctorDoctorSkill) <= 0) {
							throw new RuntimeException();
						}
					}
				}
				return new JsonApi(ApiCode.OK);
			}
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
