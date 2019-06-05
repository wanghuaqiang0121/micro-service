package org.module.platform.configuration.controller.organization;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.module.platform.configuration.domain.OrganizationSms;
import org.module.platform.configuration.domain.OrganizationSmsInfo;
import org.module.platform.configuration.global.OrganizationSms.Status;
import org.module.platform.configuration.global.OrganizationSms.Type;
import org.module.platform.configuration.service.OrganizationSmsService;
import org.module.platform.configuration.service.organization.OrganizationSmsInfoService;
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

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年11月7日
 * @Version 
 * @Description 机构短信
 * // TODO 待添加到新项目
 */
@RestController
public class OrganizationSmsInfoController {

	@Autowired
	private OrganizationSmsInfoService organizationSmsInfoService;
	@Autowired
	private OrganizationSmsService organizationSmsService;
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param organizationId
	* @param organizationSmsInfo
	* @param result
	* @return 
	* @date 2018年11月7日
	* @version 1.0
	* @description 机构短信账户详情
	*/
	@RequiresAuthentication(ignore = true,value = { "organization:configure:organizationSms:detail" },level = Level.OPERATION)
	@RequestMapping(value = { "/organization/sms/info/{organizationId}" }, method = RequestMethod.GET)
	public JsonApi getOne(
			@PathVariable("organizationId")Integer organizationId,
			@Validated({ BaseEntity.SelectOne.class }) OrganizationSmsInfo organizationSmsInfo, BindingResult result) {
		organizationSmsInfo.setOrganizationId(organizationId);
		Map<String, Object> organizationSmsMap = organizationSmsInfoService.getOne(organizationSmsInfo);
		if (organizationSmsMap!=null && !organizationSmsMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,organizationSmsMap);
		}
		Map<String, Object> organizationSmsMaps = new HashMap<>();
		organizationSmsMaps.put("totalFrequency", 0);
		organizationSmsMaps.put("useFrequency", 0);
		organizationSmsMaps.put("remainNum", 0);
		return new JsonApi(ApiCode.OK,organizationSmsMaps);
	}
	
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param organizationId
	* @param organizationSmsInfo
	* @param result
	* @return 
	* @date 2018年11月7日
	* @version 1.0
	* @description 机构短信修改(充值)
	*/
	@Transactional
	@RequiresAuthentication(ignore = true,value = { "organization:configure:organizationSms:update" },level = Level.OPERATION)
	@RequestMapping(value = { "/organization/sms/update/{organizationId}" }, method = RequestMethod.PUT)
	public JsonApi update(
			@PathVariable("organizationId")Integer organizationId,
			@Validated({ BaseEntity.Update.class }) @RequestBody OrganizationSmsInfo organizationSmsInfo, BindingResult result) {
		organizationSmsInfo.setOrganizationId(organizationId);
		Map<String, Object> organizationSmsMap = organizationSmsInfoService.getOne(organizationSmsInfo);
		if (organizationSmsMap!=null && !organizationSmsMap.isEmpty()) {
			/*直接修改*/
			if (organizationSmsInfoService.update(organizationSmsInfo) > 0) {
				return new JsonApi(ApiCode.OK);
			}
		}
		/*添加*/
		organizationSmsInfo.setUseFrequency(0);
		organizationSmsInfo.setCreateDate(new Date());
		if (organizationSmsInfoService.insert(organizationSmsInfo) > 0) {
			/*添加短信充值记录*/
			OrganizationSms organizationSms = new OrganizationSms();
			organizationSms.setOrganizationId(organizationId);
			organizationSms.setType(Type.RECHARGE.getValue());
			organizationSms.setStatus(Status.RECHARGE.getValue());
			organizationSms.setConsumeTimes(organizationSmsInfo.getTotalFrequency());
			organizationSms.setCreateDate(new Date());
			if (organizationSmsService.insert(organizationSms) < 0) {
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	
}
