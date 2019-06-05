package org.module.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.module.common.domain.SystemKeyInformation;
import org.module.common.message.Prompt;
import org.module.common.service.SystemKeyInformationService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.tools.rsa.RestToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年4月2日
 * @Version 
 * @Description 系统秘钥
 */
@RestController
public class SystemKeyInformationController {

	@Autowired
	private SystemKeyInformationService systemKeyInformationService;
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param platform
	* @param systemKeyInformation
	* @param result
	* @return 
	* @date 2018年4月2日
	* @version 1.0
	* @description 根据平台查询系统密钥信息详情
	*/
	@RequiresAuthentication(authc = true, value = { "common:system:key:information:details" })
	@RequestMapping(value = { "/sys/key/{platform}" }, method = RequestMethod.GET)
	public JsonApi getOne(@PathVariable("platform") String platform,
			@Validated({ BaseEntity.SelectOne.class }) SystemKeyInformation systemKeyInformation, BindingResult result) {
		/* 设置条件 */
		systemKeyInformation.setPlatform(platform);
		/* 查询详情 */
		Map<String, Object> resultMap = systemKeyInformationService.getOne(systemKeyInformation);

		if (resultMap!=null && !resultMap.isEmpty()) {
			/* 定义返回结果集 */
			Map<String, Map<String, Object>> resultTokenMap = new HashMap<String, Map<String, Object>>();
			resultTokenMap.put(systemKeyInformation.getPlatform(),
					RestToken.getInstance().getPermissionToken(resultMap.get("username").toString(),
							resultMap.get("password").toString(), resultMap.get("publicKey").toString(),
							resultMap.get("url").toString(), (int) resultMap.get("expire")));
			return new JsonApi(ApiCode.OK, resultTokenMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("system.key.information.empty"));
	}
}
