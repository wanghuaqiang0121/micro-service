package org.module.team.permission.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.module.team.permission.domain.OrganizationTeamPermission;
import org.module.team.permission.message.Prompt;
import org.module.team.permission.service.OrganizationTeamPermissionService;
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
 * @Description 团队权限
 */
@RestController
public class OrganizationTeamPermissionController {

	@Autowired
	private OrganizationTeamPermissionService organizationTeamPermissionService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamPermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 新增团队权限
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/permission" }, method = RequestMethod.POST)
	public JsonApi teamPermissionInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationTeamPermission organizationTeamPermission, BindingResult result) {
		organizationTeamPermission.setCreateDate(new Date());
		organizationTeamPermission.setIsUsed(true);
		// 判断数据是否重复
		Map<String, Object> teamPermissionRepeatMap = organizationTeamPermissionService.getRepeat(organizationTeamPermission);
		if (MapUtils.isNotEmpty(teamPermissionRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("team.permission.code.is.conflict"));
		}
		// 新增团队权限
		if (organizationTeamPermissionService.insert(organizationTeamPermission) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamPermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 查看团队权限详情
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/permission/{id}" }, method = RequestMethod.GET)
	public JsonApi teamPermissionOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OrganizationTeamPermission organizationTeamPermission, BindingResult result) {
		organizationTeamPermission.setId(id);
		// 查看团队权限详情
		Map<String, Object> teamPermissionOneResultMap = organizationTeamPermissionService.getOne(organizationTeamPermission);
		if (MapUtils.isNotEmpty(teamPermissionOneResultMap)) {
			return new JsonApi(ApiCode.OK,teamPermissionOneResultMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamPermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 删除团队权限
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/permission/{id}" }, method = RequestMethod.DELETE)
	public JsonApi teamPermissionDelete(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Delete.class }) OrganizationTeamPermission organizationTeamPermission, BindingResult result) {
		organizationTeamPermission.setId(id);
		// 删除团队权限
		if (organizationTeamPermissionService.delete(organizationTeamPermission) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamPermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 查看团队权限列表
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/permissions" }, method = RequestMethod.GET)
	public JsonApi teamPermissionList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationTeamPermission organizationTeamPermission, BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationTeamPermission.getPage(), organizationTeamPermission.getPageSize());
		// 查看团队权限列表
		List<Map<String, Object>> teamPermissionsResultList = organizationTeamPermissionService.getList(organizationTeamPermission);
		if (teamPermissionsResultList !=null && !teamPermissionsResultList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),teamPermissionsResultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamPermission
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 修改团队权限
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/permission/{id}" }, method = RequestMethod.PUT)
	public JsonApi teamPermissionUpdate(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OrganizationTeamPermission organizationTeamPermission, BindingResult result) {
		// 修改的数据是否存在
		organizationTeamPermission.setId(id);
		Map<String, Object> teamPermissionOneMap = organizationTeamPermissionService.getOne(organizationTeamPermission);
		if (MapUtils.isEmpty(teamPermissionOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 如果修改code 唯一标识
		if (StringUtils.isNotBlank(organizationTeamPermission.getCode())) {
			Map<String, Object> teamPermissionRepeatMap = organizationTeamPermissionService.getRepeat(organizationTeamPermission);
			if (MapUtils.isNotEmpty(teamPermissionRepeatMap)) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("team.permission.code.is.conflict"));
			}
		}
		//修改团队权限
		if (organizationTeamPermissionService.update(organizationTeamPermission) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
