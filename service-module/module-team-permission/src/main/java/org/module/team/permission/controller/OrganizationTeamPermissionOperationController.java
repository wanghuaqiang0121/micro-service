package org.module.team.permission.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.team.permission.domain.OrganizationTeamPermissionOperation;
import org.module.team.permission.service.OrganizationTeamPermissionOperationService;
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
 * @Description 团队权限操作关联
 */
@RestController
public class OrganizationTeamPermissionOperationController {

	@Autowired
	private OrganizationTeamPermissionOperationService organizationTeamPermissionOperationService;
	

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamPermissionOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 新增团队权限操作关联
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/permissionOperation" }, method = RequestMethod.POST)
	public JsonApi teamPermissionOperationInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationTeamPermissionOperation organizationTeamPermissionOperation, BindingResult result) {
		// 判断数据是否重复
		Map<String, Object> teamPermissionOperationRepeatMap = organizationTeamPermissionOperationService.getRepeat(organizationTeamPermissionOperation);
		if (MapUtils.isNotEmpty(teamPermissionOperationRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT);
		}
		// 新增团队权限操作关联
		if (organizationTeamPermissionOperationService.insert(organizationTeamPermissionOperation) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamPermissionOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 删除团队权限操作关联
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/{organizationTeamPermissionId}/permissionOperation/{organizationTeamOperationId}" }, method = RequestMethod.DELETE)
	public JsonApi teamPermissionOperationDelete(
			@PathVariable("organizationTeamOperationId")Integer organizationTeamOperationId,
			@PathVariable("organizationTeamPermissionId")Integer organizationTeamPermissionId,
			@Validated({ BaseEntity.Delete.class }) OrganizationTeamPermissionOperation organizationTeamPermissionOperation, BindingResult result) {
		organizationTeamPermissionOperation.setOrganizationTeamOperationId(organizationTeamOperationId);
		organizationTeamPermissionOperation.setOrganizationTeamPermissionId(organizationTeamPermissionId);
		// 删除团队权限操作关联
		if (organizationTeamPermissionOperationService.delete(organizationTeamPermissionOperation) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param permissionId
	* @param organizationTeamPermissionOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月16日
	* @version 1.0
	* @description 团队权限（拥有未拥有）的操作
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/permissionOperations/{permissionId}" }, method = RequestMethod.GET)
	public JsonApi teamPermissionHaveAndNotHaveOperations(
			@PathVariable("permissionId")Integer permissionId,
			@Validated({ OrganizationTeamPermissionOperation.HaveAndNotHaveOperation.class }) OrganizationTeamPermissionOperation organizationTeamPermissionOperation, BindingResult result) {
		organizationTeamPermissionOperation.setOrganizationTeamPermissionId(permissionId);
		Page<?> page = PageHelper.startPage(organizationTeamPermissionOperation.getPage(), organizationTeamPermissionOperation.getPageSize());
		// 团队权限（拥有未拥有）的操作
		List<Map<String, Object>> teamPermissionHaveAndNotHaveOperationsResultList = organizationTeamPermissionOperationService.teamPermissionHaveAndNotHaveOperations(organizationTeamPermissionOperation);
		if (CollectionUtils.isNotEmpty(teamPermissionHaveAndNotHaveOperationsResultList)) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),teamPermissionHaveAndNotHaveOperationsResultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
