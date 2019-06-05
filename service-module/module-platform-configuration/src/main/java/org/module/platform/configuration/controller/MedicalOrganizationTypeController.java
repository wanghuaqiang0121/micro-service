package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.MedicalOrganizationType;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.MedicalOrganizationTypeService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月6日
 * @Version 
 * @Description 医疗机构类别
 */
@RestController
public class MedicalOrganizationTypeController {
	
	@Resource
	private MedicalOrganizationTypeService medicalOrganizationTypeService;
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param medicalOrganizationType
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年7月19日
	 * @version 1.0
	 * @description 新增医疗机构类别表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:medicalOrganizationType:insert" })
	@RequestMapping(value = { "/medical/organization/type" }, method = RequestMethod.POST)
	public JsonApi insertMedicalOrganizationType(
			@Validated({ BaseEntity.Insert.class }) @RequestBody MedicalOrganizationType medicalOrganizationType, BindingResult result){
		medicalOrganizationType.setCreateDate(new Date());
		Map<String,Object>  medicalOrganizationTypeRepeatMap=medicalOrganizationTypeService.getRepeat(medicalOrganizationType);
		if (MapUtils.isNotEmpty(medicalOrganizationTypeRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("medica.organization.type.code.is.exists"));
		}
		if (medicalOrganizationTypeService.insert(medicalOrganizationType)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param medicalOrganizationType
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年7月19日
	 * @version 1.0
	 * @description 修改医疗机构类别表
	 */
	@RequiresAuthentication(level=Level.OPERATION,value={"platform:configure:medicalOrganizationType:update" })
	@RequestMapping(value = { "/medical/organization/type/{id}" }, method = RequestMethod.PUT)
	public JsonApi updateOrganizationGrad(
			@PathVariable("id")Integer id,
			@Validated({BaseEntity.Update.class})@RequestBody MedicalOrganizationType medicalOrganizationType,BindingResult result){
		medicalOrganizationType.setId(id);
		Map<String, Object> medicalOrganizationTypeDetailMap=medicalOrganizationTypeService.getDetail(medicalOrganizationType);
		if (MapUtils.isEmpty(medicalOrganizationTypeDetailMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if (null!=medicalOrganizationType.getCode()) {
			Map<String, Object> medicalOrganizationTypeRepeatMap = medicalOrganizationTypeService.getRepeat(medicalOrganizationType);
			if (MapUtils.isNotEmpty(medicalOrganizationTypeRepeatMap)) {
				if (!medicalOrganizationTypeDetailMap.get("id").equals(medicalOrganizationTypeRepeatMap.get("id"))) {
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("medica.organization.type.code.is.exists"));
				}
			}
		}
		if (medicalOrganizationTypeService.update(medicalOrganizationType)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param medicalOrganizationType
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年7月19日
	 * @version 1.0
	 * @description 查询医疗机构类型表列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:medicalOrganizationType:list" })
	@RequestMapping(value = { "/medical/organization/types" }, method = RequestMethod.GET)
	public JsonApi getMedicalOrganizationTypeList(
			@Validated({ BaseEntity.SelectAll.class })  MedicalOrganizationType medicalOrganizationType, BindingResult result) {
		Page<?> page = PageHelper.startPage(medicalOrganizationType.getPage(), medicalOrganizationType.getPageSize());		
		List<Map<String, Object>> medicalOrganizationTypeList = medicalOrganizationTypeService.getList(medicalOrganizationType);
		if (medicalOrganizationTypeList !=null && !medicalOrganizationTypeList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),medicalOrganizationTypeList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}


}
