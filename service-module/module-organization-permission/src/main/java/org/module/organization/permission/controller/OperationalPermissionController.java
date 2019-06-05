package org.module.organization.permission.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalPermission;
import org.module.organization.permission.message.Prompt;
import org.module.organization.permission.service.OperationalPermissionService;
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
public class OperationalPermissionController {

	@Autowired
	private OperationalPermissionService operationalPermissionService;
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param operationalPermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 新增机构权限
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permission" }, method = RequestMethod.POST)
	public JsonApi insert(
			@Validated({ BaseEntity.Insert.class })@RequestBody OperationalPermission  operationalPermission,BindingResult result){
		/*设置时间*/
		operationalPermission.setCreateDate(new Date());
		/*判断是否重复*/
		Map<String, Object> operationalPermissionMap = operationalPermissionService.getRepeat(operationalPermission);
		if (operationalPermissionMap!=null && !operationalPermissionMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("operational.permission.code.is.conflict"));
		}
		if (operationalPermissionService.insert(operationalPermission)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param operationalPermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 修改机构权限
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permission/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OperationalPermission  operationalPermission,BindingResult result){
		operationalPermission.setId(id);
		//判断是否存在
		Map<String, Object> operationalPermissionOneMap = operationalPermissionService.getOne(operationalPermission);
		if (operationalPermissionOneMap==null || operationalPermissionOneMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		/*判断是否重复*/
		if (null !=operationalPermission.getCode()) {
			Map<String, Object> operationalPermissionMap = operationalPermissionService.getRepeat(operationalPermission);
			if (operationalPermissionMap!=null && !operationalPermissionMap.isEmpty()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("operational.permission.code.is.conflict"));
			}
		}
		if (operationalPermissionService.update(operationalPermission)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param operationalPermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 机构权限详情
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permission/{id}" }, method = RequestMethod.GET)
	public JsonApi getOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OperationalPermission  operationalPermission,BindingResult result){
		operationalPermission.setId(id);
		Map<String, Object> operationalPermissionMap = operationalPermissionService.getOne(operationalPermission);
		if (operationalPermissionMap!=null && !operationalPermissionMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,operationalPermissionMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}


	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param operationalPermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 机构权限列表
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permissions" }, method = RequestMethod.GET)
	public JsonApi getList(
			@Validated({ BaseEntity.SelectAll.class }) OperationalPermission  operationalPermission,BindingResult result){
		
		Page<?> page = PageHelper.startPage(operationalPermission.getPage(), operationalPermission.getPageSize());
		List<Map<String, Object>> operationalPermissionList = operationalPermissionService.getList(operationalPermission);
		if (operationalPermissionList != null && !operationalPermissionList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),operationalPermissionList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
