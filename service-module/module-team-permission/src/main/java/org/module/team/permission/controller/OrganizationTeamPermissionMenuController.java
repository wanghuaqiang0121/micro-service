package org.module.team.permission.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.team.permission.domain.OrganizationTeamPermissionMenu;
import org.module.team.permission.service.OrganizationTeamPermissionMenuService;
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
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年5月16日
 * @Version 
 * @Description 团队权限菜单关联
 */
@RestController
public class OrganizationTeamPermissionMenuController {

	@Autowired
	private OrganizationTeamPermissionMenuService organizationTeamPermissionMenuService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamPermissionMenu
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 新增团队权限菜单关联
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/permissionMenu" }, method = RequestMethod.POST)
	public JsonApi teamPermissionMenuInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationTeamPermissionMenu organizationTeamPermissionMenu, BindingResult result) {
		// 判断数据是否重复
		Map<String, Object> teamPermissionMenuRepeatMap = organizationTeamPermissionMenuService.getRepeat(organizationTeamPermissionMenu);
		if (MapUtils.isNotEmpty(teamPermissionMenuRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT);
		}
		// 新增团队权限菜单关联
		if (organizationTeamPermissionMenuService.insert(organizationTeamPermissionMenu) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamPermissionMenu
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 删除团队权限菜单关联
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/{organizationTeamPermissionId}/permissionMenu/{organizationTeamMenuId}" }, method = RequestMethod.DELETE)
	public JsonApi teamPermissionMenuDelete(
			@PathVariable("organizationTeamMenuId")Integer organizationTeamMenuId,
			@PathVariable("organizationTeamPermissionId")Integer organizationTeamPermissionId,
			@Validated({ BaseEntity.Delete.class }) OrganizationTeamPermissionMenu organizationTeamPermissionMenu, BindingResult result) {
		organizationTeamPermissionMenu.setOrganizationTeamPermissionId(organizationTeamPermissionId);
		organizationTeamPermissionMenu.setOrganizationTeamMenuId(organizationTeamMenuId);
		// 删除团队权限菜单关联
		if (organizationTeamPermissionMenuService.delete(organizationTeamPermissionMenu) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param permissionId
	* @param organizationTeamPermissionMenu
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 团队权限（拥有未拥有）的菜单
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/permissionMenu/{permissionId}" }, method = RequestMethod.GET)
	public JsonApi teamPermissionHaveAndNotHaveMenus(
			@PathVariable("permissionId")Integer permissionId,
			@Validated({ OrganizationTeamPermissionMenu.HaveAndNotHaveMenu.class }) OrganizationTeamPermissionMenu organizationTeamPermissionMenu, BindingResult result) {
		organizationTeamPermissionMenu.setOrganizationTeamPermissionId(permissionId);
		Page<?> page = PageHelper.startPage(organizationTeamPermissionMenu.getPage(), organizationTeamPermissionMenu.getPageSize());
		// 团队权限（拥有未拥有）的菜单
		List<Map<String, Object>> teamPermissionHaveAndNotHaveMenusResultList = organizationTeamPermissionMenuService.teamPermissionHaveAndNotHaveMenus(organizationTeamPermissionMenu);
		if (CollectionUtils.isNotEmpty(teamPermissionHaveAndNotHaveMenusResultList)) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),teamPermissionHaveAndNotHaveMenusResultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
