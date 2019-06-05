package org.module.team.permission.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.team.permission.domain.OrganizationTeamRolePermission;
import org.module.team.permission.service.OrganizationTeamRolePermissionService;
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

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年5月28日
 * @Version 
 * @Description 团队角色权限关联表
 */
@RestController
public class OrganizationTeamRolePermissionController {

	@Autowired
	private OrganizationTeamRolePermissionService organizationTeamRolePermissionService;
	
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamRolePermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 添加团队角色权限关联
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/role/permission" }, method = RequestMethod.POST)
	public JsonApi teamOrganizationTeamRolePermissionInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationTeamRolePermission organizationTeamRolePermission, BindingResult result) {
		// 判断数据是否重复
		Map<String, Object> organizationTeamRolePermissionRepeatMap = organizationTeamRolePermissionService.getRepeat(organizationTeamRolePermission);
		if (MapUtils.isNotEmpty(organizationTeamRolePermissionRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT);
		}
		// 新增团队权限操作关联
		if (organizationTeamRolePermissionService.insert(organizationTeamRolePermission) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamRolePermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 删除团队角色关联
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/{organizationTeamRoleId}/rolePermission/{organizationTeamPermissionId}" }, method = RequestMethod.DELETE)
	public JsonApi teamPermissionOperationDelete(
			@PathVariable("organizationTeamRoleId")Integer organizationTeamRoleId,
			@PathVariable("organizationTeamPermissionId")Integer organizationTeamPermissionId,
			@Validated({ BaseEntity.Delete.class }) OrganizationTeamRolePermission organizationTeamRolePermission, BindingResult result) {
		organizationTeamRolePermission.setOrganizationTeamRoleId(organizationTeamRoleId);
		organizationTeamRolePermission.setOrganizationTeamPermissionId(organizationTeamPermissionId);
		// 删除团队权限操作关联
		if (organizationTeamRolePermissionService.delete(organizationTeamRolePermission) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param permissionId
	* @param organizationTeamRolePermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 团队角色（拥有未拥有）的权限
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/rolePermissions/{roleId}" }, method = RequestMethod.GET)
	public JsonApi teamRoleHaveAndNotHavePermissions(
			@PathVariable("roleId")Integer roleId,
			@Validated({ OrganizationTeamRolePermission.HaveAndNotHaveOperation.class }) OrganizationTeamRolePermission organizationTeamRolePermission, BindingResult result) {
		organizationTeamRolePermission.setOrganizationTeamRoleId(roleId);
		Page<?> page = PageHelper.startPage(organizationTeamRolePermission.getPage(), organizationTeamRolePermission.getPageSize());
		// 团队权限（拥有未拥有）的操作
		List<Map<String, Object>> teamRoleHaveAndNotHavePermissionsResultList = organizationTeamRolePermissionService.teamRoleHaveAndNotHavePermissions(organizationTeamRolePermission);
		if (CollectionUtils.isNotEmpty(teamRoleHaveAndNotHavePermissionsResultList)) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),teamRoleHaveAndNotHavePermissionsResultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
