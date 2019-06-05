package org.module.organization.permission.controller;


import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.ModuleOperationalRole;
import org.module.organization.permission.service.ModuleOperationalRoleService;
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
public class ModuleOperationalRoleController {
	
	@Autowired
	private ModuleOperationalRoleService moduleOperationalRoleService;
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param moduleOperationalRole
	 * @param result
	 * @return
	 * @date 2018年5月2日
	 * @version 1.0
	 * @description 新增模块角色关联表
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/module/operational/role" }, method = RequestMethod.POST)
	public JsonApi insert(
			@RequestBody @Validated({ BaseEntity.Insert.class }) ModuleOperationalRole  moduleOperationalRole,
			BindingResult result
			){
		Map<String, Object> moduleOperationalRoleMap = moduleOperationalRoleService.getRepeat(moduleOperationalRole);
		if(moduleOperationalRoleMap!=null && !moduleOperationalRoleMap.isEmpty()){
			return new JsonApi(ApiCode.CONFLICT);
		}
		/*新增*/
		if (moduleOperationalRoleService.insert(moduleOperationalRole)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param systemModuleId
	 * @param operationalRoleId
	 * @param moduleOperationalRole
	 * @param result
	 * @return
	 * @date 2018年5月2日
	 * @version 1.0
	 * @description 删除模块角色关联
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/module/{systemModuleId}/operational/role/{operationalRoleId}" }, method = RequestMethod.DELETE)
	public JsonApi delete(
			@PathVariable("systemModuleId") Integer systemModuleId,
			@PathVariable("operationalRoleId") Integer operationalRoleId,
			@Validated({ BaseEntity.Delete.class }) ModuleOperationalRole  moduleOperationalRole,
			BindingResult result){
		moduleOperationalRole.setSystemModuleId(systemModuleId);
		moduleOperationalRole.setOperationalRoleId(operationalRoleId);
		if (moduleOperationalRoleService.delete(moduleOperationalRole)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param moduleOperationalRole
	 * @param result
	 * @return
	 * @date 2018年5月2日
	 * @version 1.0
	 * @description 查询该模块拥有和未拥有的角色
	 */
	@RequiresAuthentication(value = { "role:permission:manager" })
	@RequestMapping(value = { "/module/operational/roles" }, method = RequestMethod.GET)
	public JsonApi getModuleRoleIsChoose(
			@Validated({ ModuleOperationalRole.GetModuleRoleIsChoose.class }) ModuleOperationalRole  moduleOperationalRole,
			BindingResult result){
		Page<?> page = PageHelper.startPage(moduleOperationalRole.getPage(), moduleOperationalRole.getPageSize());
		List<Map<String, Object>> moduleOperationalRoleList=moduleOperationalRoleService.getModuleRoleIsChoose(moduleOperationalRole);
		if (moduleOperationalRoleList!=null && !moduleOperationalRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), moduleOperationalRoleList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	
	}
	
	
	
}
