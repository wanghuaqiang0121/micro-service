package org.module.platform.configuration.controller.organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.organization.OrganizationModule;
import org.module.platform.configuration.global.OrganizationStatusCode;
import org.module.platform.configuration.service.organization.OrganizationModuleService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@RestController
public class OrganizationModuleController {
	
	@Autowired
	private OrganizationModuleService organizationModuleService;
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationModule
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 新增机构和模块关联
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationModule:insert" })
	@RequestMapping(value = { "/organization/module" }, method = RequestMethod.POST)
	public JsonApi insertOrganizationModule(@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationModule organizationModule,BindingResult result){
		/*判断数据是否重复*/
		// 存在就修改
		Map<String, Object> organizationModuleMap = organizationModuleService.getRepeat(organizationModule);
		if (organizationModuleMap!=null && !organizationModuleMap.isEmpty()) {
			OrganizationModule organizationModuleNew = new OrganizationModule();
			organizationModuleNew.setId((Integer)organizationModuleMap.get("id"));
			organizationModuleNew.setStatus(OrganizationStatusCode.Organization.ENABLE.getValue());
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("id", (Integer)organizationModuleMap.get("id"));
			if (organizationModuleService.update(organizationModuleNew)>0) {
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}else {// 不存在就新增
			organizationModule.setStatus(OrganizationStatusCode.Organization.ENABLE.getValue());
			if (organizationModuleService.insert(organizationModule)>0) {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("id", organizationModule.getId());
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationModule
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 查询机构模块列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationModule:list" })
	@RequestMapping(value = { "/organization/modules" }, method = RequestMethod.GET)
	public JsonApi getOrganizationModuleList(@Validated({ BaseEntity.SelectAll.class })OrganizationModule organizationModule,BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationModule.getPage(), organizationModule.getPageSize());
		List<Map<String, Object>> organizationModuleList = organizationModuleService.getList(organizationModule);
		if (organizationModuleList != null && !organizationModuleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationModuleList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationModule
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 查询机构关联和未关联的模块列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationModule:isChoose:list" })
	@RequestMapping(value = { "/organization/module/isChoose" }, method = RequestMethod.GET)
	public JsonApi getOrganizationModuleIsChoose(@Validated({OrganizationModule.getOrganizationModuleIsChoose.class})OrganizationModule organizationModule,
			BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationModule.getPage(), organizationModule.getPageSize());
		List<Map<String, Object>> organizationModuleList = organizationModuleService.getOrganizationModuleIsChoose(organizationModule);
		if (organizationModuleList != null && !organizationModuleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationModuleList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param organizationModule
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 删除机构和模块关联 逻辑删除
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationModule:delete" })
	@RequestMapping(value = { "/organization/module/{id}" }, method = RequestMethod.DELETE)
	public JsonApi updateOrganizationModule(@PathVariable("id") Integer id,
			@Validated({ BaseEntity.Delete.class })  OrganizationModule organizationModule, BindingResult result) {
		organizationModule.setId(id);
		organizationModule.setStatus(OrganizationStatusCode.Organization.DISABLE.getValue());
		Map<String, Object> organizationModuleOneMap = organizationModuleService.getOne(organizationModule);
		if (MapUtils.isEmpty(organizationModuleOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 修改数据
		if (organizationModuleService.update(organizationModule) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	

}
