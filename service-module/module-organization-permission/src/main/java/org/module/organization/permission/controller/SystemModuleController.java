package org.module.organization.permission.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.SystemModule;
import org.module.organization.permission.message.Prompt;
import org.module.organization.permission.service.SystemModuleService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
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
public class SystemModuleController {

	@Autowired
	private SystemModuleService systemModuleService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param systemModule
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月17日
	* @version 1.0
	* @description 新增系统模块
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/system/module" }, method = RequestMethod.POST)
	public JsonApi insert(
			@Validated({ BaseEntity.Insert.class })@RequestBody SystemModule  systemModule,BindingResult result){
		/*设置时间*/
		systemModule.setCreateDate(new Date());
		/*判断是否重复*/
		Map<String, Object> systemModuleMap = systemModuleService.getRepeat(systemModule);
		if (systemModuleMap!=null && !systemModuleMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("system.module.code.is.conflict"));
		}
		if (systemModuleService.insert(systemModule)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param systemModule
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月17日
	* @version 1.0
	* @description 修改系统模块
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/system/module/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody SystemModule  systemModule,BindingResult result){
		systemModule.setId(id);
		//判断是否存在
		Map<String, Object> systemModuleOneMap = systemModuleService.getOne(systemModule);
		if (systemModuleOneMap==null || systemModuleOneMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		/*判断是否重复*/
		if (null != systemModule.getCode()) {
			Map<String, Object> systemModuleMap = systemModuleService.getRepeat(systemModule);
			if( systemModuleMap!=null && !systemModuleMap.isEmpty()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("system.module.code.is.conflict"));
			}
		}
		if (systemModuleService.update(systemModule)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param systemModule
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月17日
	* @version 1.0
	* @description 系统模块详情
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/system/module/{id}" }, method = RequestMethod.GET)
	public JsonApi getOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) SystemModule  systemModule,BindingResult result){
		systemModule.setId(id);
		Map<String, Object> systemModuleMap = systemModuleService.getOne(systemModule);
		if (systemModuleMap!=null && !systemModuleMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,systemModuleMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param systemModule
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月17日
	* @version 1.0
	* @description 查询系统模块列表
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/system/modules" }, method = RequestMethod.GET)
	public JsonApi getList(
			@Validated({ BaseEntity.SelectAll.class }) SystemModule  systemModule,BindingResult result){
		
		Page<?> page = PageHelper.startPage(systemModule.getPage(), systemModule.getPageSize());
		List<Map<String, Object>> systemModuleList = systemModuleService.getList(systemModule);
		if (systemModuleList != null && !systemModuleList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),systemModuleList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
