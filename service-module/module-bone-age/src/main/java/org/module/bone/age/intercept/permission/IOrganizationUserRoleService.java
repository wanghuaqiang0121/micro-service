package org.module.bone.age.intercept.permission;

import org.module.bone.age.global.BaseGlobal;
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
	* @param page
	* @param pageSize
	* @param token
	* @param organizationTeamId
	* @return 
	* @date 2018年6月14日
	* @version 1.0
	* @description 查询机构用户团队角色列表
	*/
	@RequestMapping(value = { "/organizationTeam/organization/team/roles" }, method = RequestMethod.GET)
	JsonApi getOrganizationUserRoleRolesList(
			@RequestParam("page") Integer page, 
			@RequestParam("pageSize") Integer pageSize,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId);
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param page
	* @param pageSize
	* @param token
	* @param organizationTeamId
	* @return 
	* @date 2018年6月14日
	* @version 1.0
	* @description 查询机构用户团队权限列表
	*/
	@RequestMapping(value = { "/organizationTeam/organization/team/permissions" }, method = RequestMethod.GET)
	JsonApi getOrganizationUserRolepermissionsList(
			@RequestParam("page") Integer page, 
			@RequestParam("pageSize") Integer pageSize,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId);
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param page
	* @param pageSize
	* @param token
	* @param organizationTeamId
	* @return 
	* @date 2018年6月14日
	* @version 1.0
	* @description 查询机构用户团队操作列表
	*/
	@RequestMapping(value = { "/organizationTeam/organization/team/operations" }, method = RequestMethod.GET)
	JsonApi getOrganizationUserRoleOperationsList(
			@RequestParam("page") Integer page, 
			@RequestParam("pageSize") Integer pageSize,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId);

	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param page
	* @param pageSize
	* @param token
	* @param organizationTeamId
	* @return 
	* @date 2018年6月14日
	* @version 1.0 
	* @description 查询机构用户团队菜单列表
	*/
	@RequestMapping(value = { "/organizationTeam/organization/team/menus" }, method = RequestMethod.GET)
	JsonApi getOrganizationUserRoleMenusList(
			@RequestParam("page") Integer page, 
			@RequestParam("pageSize") Integer pageSize,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId);
}
