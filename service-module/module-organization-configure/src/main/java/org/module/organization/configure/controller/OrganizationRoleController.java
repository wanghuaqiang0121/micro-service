package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationRole;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.service.OrganizationRoleService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@RestController
public class OrganizationRoleController {

	@Autowired
	private OrganizationRoleService organizationRoleService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月17日
	* @version 1.0
	* @description 本机构的角色列表（有没有给用户授权）
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationRole:authorizationUser:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/role/authorization/{organizationUserId}" }, method = RequestMethod.GET)
	public JsonApi getOrganizationRoleAuthorizationUserList(
			@PathVariable("organizationUserId")Integer organizationUserId,
			@Validated({ BaseEntity.SelectAll.class }) OrganizationRole organizationRole,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		organizationRole.setOrganizationId(organizationId);
		organizationRole.setOrganizationUserId(organizationUserId);
		Page<?> page = PageHelper.startPage(organizationRole.getPage(), organizationRole.getPageSize());
		List<Map<String, Object>>  organizationRoleList=organizationRoleService.getOrganizationRoleAuthorizationUserList(organizationRole);
		if (organizationRoleList!=null && !organizationRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationRoleList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
