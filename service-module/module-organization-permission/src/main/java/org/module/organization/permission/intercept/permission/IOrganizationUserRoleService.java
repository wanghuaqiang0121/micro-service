package org.module.organization.permission.intercept.permission;

import org.module.organization.permission.global.BaseGlobal;
import org.service.core.api.JsonApi;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${micro.service.api.core}",fallback = OrganizationUserRoleServiceFallback.class)
public interface IOrganizationUserRoleService {

	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationUserRole
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 机构角色列表（用于拦截）
	*/
	@RequestMapping(value = { "/organizationUser/module/roles" }, method = RequestMethod.GET)
	JsonApi getOrganizationUserRoleRolesList(
			@RequestParam("page") Integer page, 
			@RequestParam("pageSize") Integer pageSize,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId);
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationUserRole
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 权限列表（用于拦截）
	*/
	@RequestMapping(value = { "/organizationUser/module/permissions" }, method = RequestMethod.GET)
	JsonApi getOrganizationUserRolepermissionsList(
			@RequestParam("page") Integer page, 
			@RequestParam("pageSize") Integer pageSize,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId);
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationUserRole
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 操作列表（用于拦截）
	*/
	@RequestMapping(value = { "/organizationUser/module/operations" }, method = RequestMethod.GET)
	JsonApi getOrganizationUserRoleOperationsList(
			@RequestParam("page") Integer page, 
			@RequestParam("pageSize") Integer pageSize,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId);
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationUserRole
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 菜单列表（用于拦截）
	*/
	@RequestMapping(value = { "/organizationUser/module/menus" }, method = RequestMethod.GET)
	JsonApi getOrganizationUserRoleMenusList(
			@RequestParam("page") Integer page, 
			@RequestParam("pageSize") Integer pageSize,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId);
}
