package org.module.team.permission.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.module.team.permission.domain.OrganizationTeamOperation;
import org.module.team.permission.message.Prompt;
import org.module.team.permission.service.OrganizationTeamOperationService;
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
 * @Date 2018年5月15日
 * @Version 
 * @Description 团队操作
 */
@RestController
public class OrganizationTeamOperationController {

	@Autowired
	private OrganizationTeamOperationService organizationTeamOperationService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 新增团队操作
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/operation" }, method = RequestMethod.POST)
	public JsonApi teamOperationInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationTeamOperation organizationTeamOperation, BindingResult result) {
		organizationTeamOperation.setCreateDate(new Date());
		organizationTeamOperation.setIsUsed(true);
		// 判断数据是否重复
		Map<String, Object> teamOperationRepeatMap = organizationTeamOperationService.getRepeat(organizationTeamOperation);
		if (MapUtils.isNotEmpty(teamOperationRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("team.operation.code.is.conflict"));
		}
		// 新增团队操作
		if (organizationTeamOperationService.insert(organizationTeamOperation) > 0) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", organizationTeamOperation.getId());
			return new JsonApi(ApiCode.OK,map);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 查看团队操作详情
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/operation/{id}" }, method = RequestMethod.GET)
	public JsonApi teamOperationOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OrganizationTeamOperation organizationTeamOperation, BindingResult result) {
		organizationTeamOperation.setId(id);
		// 查看团队操作详情
		Map<String, Object> teamOperationOneResultMap = organizationTeamOperationService.getOne(organizationTeamOperation);
		if (MapUtils.isNotEmpty(teamOperationOneResultMap)) {
			return new JsonApi(ApiCode.OK,teamOperationOneResultMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 删除团队操作
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/operation/{id}" }, method = RequestMethod.DELETE)
	public JsonApi teamOperationDelete(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Delete.class }) OrganizationTeamOperation organizationTeamOperation, BindingResult result) {
		organizationTeamOperation.setId(id);
		// 删除团队操作
		if (organizationTeamOperationService.delete(organizationTeamOperation) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 查看团队操作列表
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/operations" }, method = RequestMethod.GET)
	public JsonApi teamOperationList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationTeamOperation organizationTeamOperation, BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationTeamOperation.getPage(), organizationTeamOperation.getPageSize());
		// 查看团队操作列表
		List<Map<String, Object>> teamOperationsResultMap = organizationTeamOperationService.getList(organizationTeamOperation);
		if (teamOperationsResultMap !=null && !teamOperationsResultMap.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),teamOperationsResultMap));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 修改团队操作
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/operation/{id}" }, method = RequestMethod.PUT)
	public JsonApi teamOperationUpdate(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OrganizationTeamOperation organizationTeamOperation, BindingResult result) {
		// 修改的数据是否存在
		organizationTeamOperation.setId(id);
		Map<String, Object> teamOperationOneMap = organizationTeamOperationService.getOne(organizationTeamOperation);
		if (MapUtils.isEmpty(teamOperationOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if (StringUtils.isNotBlank(organizationTeamOperation.getCode())) {
			Map<String, Object> teamOperationRepeatMap = organizationTeamOperationService.getRepeat(organizationTeamOperation);
			if (MapUtils.isNotEmpty(teamOperationRepeatMap)) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("team.operation.code.is.conflict"));
			}
		}
		// 修改团队操作
		if (organizationTeamOperationService.update(organizationTeamOperation) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
}
