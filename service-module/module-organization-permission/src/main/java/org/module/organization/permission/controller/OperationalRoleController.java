package org.module.organization.permission.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalRole;
import org.module.organization.permission.message.Prompt;
import org.module.organization.permission.service.OperationalRoleService;
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
public class OperationalRoleController {
	
	@Autowired
	private OperationalRoleService operationalRoleService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param operationalRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 新增角色
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/role" }, method = RequestMethod.POST)
	public JsonApi insert(
			@Validated({ BaseEntity.Insert.class })@RequestBody OperationalRole  operationalRole,BindingResult result){
		/*设置时间*/
		operationalRole.setCreateDate(new Date());
		/*判断是否重复*/
		Map<String, Object> operationalRoleMap = operationalRoleService.getRepeat(operationalRole);
		if (operationalRoleMap!=null && !operationalRoleMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("operational.role.code.is.conflict"));
		}
		if (operationalRoleService.insert(operationalRole)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param operationalRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 修改角色
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/role/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OperationalRole  operationalRole,BindingResult result){
		operationalRole.setId(id);
		//判断是否存在
		Map<String, Object> operationalRoleOneMap = operationalRoleService.getOne(operationalRole);
		if (operationalRoleOneMap==null || operationalRoleOneMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		/*判断是否重复*/
		if (null !=operationalRole.getCode() ) {
			
			Map<String, Object> operationalRoleMap = operationalRoleService.getRepeat(operationalRole);
			if (operationalRoleMap!=null && !operationalRoleMap.isEmpty()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("operational.role.code.is.conflict"));
			}
		}
		if (operationalRoleService.update(operationalRole)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param operationalRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 角色详情
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/role/{id}" }, method = RequestMethod.GET)
	public JsonApi getOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OperationalRole  operationalRole,BindingResult result){
		operationalRole.setId(id);
		Map<String, Object> operationalRoleMap = operationalRoleService.getOne(operationalRole);
		if (operationalRoleMap!=null && !operationalRoleMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,operationalRoleMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}


	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param operationalRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 角色列表
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/roles" }, method = RequestMethod.GET)
	public JsonApi getList(
			@Validated({ BaseEntity.SelectAll.class }) OperationalRole  operationalRole,BindingResult result){
		
		Page<?> page = PageHelper.startPage(operationalRole.getPage(), operationalRole.getPageSize());
		List<Map<String, Object>> operationalRoleList = operationalRoleService.getList(operationalRole);
		if (operationalRoleList != null && !operationalRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),operationalRoleList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}

}
