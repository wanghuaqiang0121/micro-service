package org.module.team.permission.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.module.team.permission.domain.OrganizationTeamRole;
import org.module.team.permission.message.Prompt;
import org.module.team.permission.service.OrganizationTeamRoleService;
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
 * @Date 2018年5月17日
 * @Version 
 * @Description 团队角色
 */
@RestController
public class OrganizationTeamRoleController {

	@Autowired
	private OrganizationTeamRoleService organizationTeamRoleService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 新增团队角色
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/role" }, method = RequestMethod.POST)
	public JsonApi teamRoleInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationTeamRole organizationTeamRole, BindingResult result) {
		organizationTeamRole.setCreateDate(new Date());
		organizationTeamRole.setIsUsed(true);
		// 判断数据是否重复
		Map<String, Object> teamRoleRepeatMap = organizationTeamRoleService.getRepeat(organizationTeamRole);
		if (MapUtils.isNotEmpty(teamRoleRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("team.role.code.is.conflict"));
		}
		// 新增团队角色
		if (organizationTeamRoleService.insert(organizationTeamRole) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 查看团队角色详情
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/role/{id}" }, method = RequestMethod.GET)
	public JsonApi teamRoleOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OrganizationTeamRole organizationTeamRole, BindingResult result) {
		organizationTeamRole.setId(id);
		// 查看团队角色详情
		Map<String, Object> teamRoleOneResultMap = organizationTeamRoleService.getOne(organizationTeamRole);
		if (MapUtils.isNotEmpty(teamRoleOneResultMap)) {
			return new JsonApi(ApiCode.OK,teamRoleOneResultMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 删除团队角色
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/role/{id}" }, method = RequestMethod.DELETE)
	public JsonApi teamPermissionDelete(
			@PathVariable("id")Integer id, @Validated({ BaseEntity.Delete.class }) OrganizationTeamRole organizationTeamRole, BindingResult result) {
		organizationTeamRole.setId(id);
		// 删除团队角色
		if (organizationTeamRoleService.delete(organizationTeamRole) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 查看团队角色列表
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/role" }, method = RequestMethod.GET)
	public JsonApi teamRoleList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationTeamRole organizationTeamRole, BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationTeamRole.getPage(), organizationTeamRole.getPageSize());
		// 查看团队角色列表
		List<Map<String, Object>> teamRolesResultMap = organizationTeamRoleService.getList(organizationTeamRole);
		if (teamRolesResultMap !=null && !teamRolesResultMap.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),teamRolesResultMap));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 修改团队角色
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/role/{id}" }, method = RequestMethod.PUT)
	public JsonApi teamRoleUpdate(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OrganizationTeamRole organizationTeamRole, BindingResult result) {
		// 修改的数据是否存在
		organizationTeamRole.setId(id);
		Map<String, Object> teamRoleOneMap = organizationTeamRoleService.getOne(organizationTeamRole);
		if (MapUtils.isEmpty(teamRoleOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if(StringUtils.isNotBlank(organizationTeamRole.getCode())){
			Map<String, Object> teamRoleRepeatMap = organizationTeamRoleService.getRepeat(organizationTeamRole);
			if (MapUtils.isNotEmpty(teamRoleRepeatMap)) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("team.role.code.is.conflict"));
			}
		}
		//修改团队角色
		if (organizationTeamRoleService.update(organizationTeamRole) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
