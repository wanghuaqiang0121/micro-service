package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.MedicalOrganizationInfo;
import org.module.platform.configuration.service.MedicalOrganizationInfoService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月14日
 * @Version 
 * @Description 医疗机构扩展
 */
@RestController
public class MedicalOrganizationInfoController {
	@Resource
	private MedicalOrganizationInfoService  medicalOrganizationInfoService;
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param medicalOrganizationInfo
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年7月19日
	 * @version 1.0
	 * @description 新增医疗机构扩展
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:medicalOrganizationInfo:insert" })
	@RequestMapping(value = { "/medical/organization/info" }, method = RequestMethod.POST)
	public JsonApi insertMedicalOrganizationInfo(
			@Validated({ BaseEntity.Insert.class }) @RequestBody MedicalOrganizationInfo medicalOrganizationInfo, BindingResult result){
		medicalOrganizationInfo.setCreateDate(new Date());
		Map<String,Object>  medicalOrganizationInfoRepeatMap=medicalOrganizationInfoService.getRepeat(medicalOrganizationInfo);
		if (MapUtils.isNotEmpty(medicalOrganizationInfoRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT);
		}
		if (medicalOrganizationInfoService.insert(medicalOrganizationInfo)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	* @author <font color="green"><b>Chen.Yan</b></font>
	* @param id
	* @param medicalOrganizationInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月19日
	* @version 1.0
	* @description 修改医疗机构扩展
	 */
	@RequiresAuthentication(level=Level.OPERATION,value={"platform:configure:medicalOrganizationInfo:update" })
	@RequestMapping(value = { "/medical/organization/info/{id}" }, method = RequestMethod.PUT)
	public JsonApi updateMedicalOrganizationInfo(
			@PathVariable("id")Integer id,
			@Validated({BaseEntity.Update.class})@RequestBody MedicalOrganizationInfo medicalOrganizationInfo,BindingResult result){
		medicalOrganizationInfo.setId(id);
		Map<String, Object> medicalOrganizationInfoDetailMap=medicalOrganizationInfoService.getDetail(medicalOrganizationInfo);
		if (MapUtils.isEmpty(medicalOrganizationInfoDetailMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if (medicalOrganizationInfoService.update(medicalOrganizationInfo)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param medicalOrganizationInfo
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年7月19日
	 * @version 1.0
	 * @description 查询医疗机构扩展详情
	 */
	@RequiresAuthentication(level=Level.OPERATION,value={"platform:configure:medicalOrganizationInfo:detail" })
	@RequestMapping(value = { "/medical/organization/info" }, method = RequestMethod.GET)
	public JsonApi getMedicalOrganizationInfoDetail(
			@Validated({BaseEntity.SelectOne.class}) MedicalOrganizationInfo medicalOrganizationInfo,BindingResult result){
		Map<String, Object> medicalOrganizationInfoDetail=medicalOrganizationInfoService.getDetail(medicalOrganizationInfo);
		if (MapUtils.isNotEmpty(medicalOrganizationInfoDetail)) {
			return new JsonApi(ApiCode.OK,medicalOrganizationInfoDetail);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	


}