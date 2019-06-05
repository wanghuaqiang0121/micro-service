package org.module.team.permission.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.module.team.permission.domain.OrganizationTeamMenu;
import org.module.team.permission.message.Prompt;
import org.module.team.permission.service.OrganizationTeamMenuService;
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
 * @Description 团队菜单
 */
@RestController
public class OrganizationTeamMenuController {

	@Autowired
	private OrganizationTeamMenuService organizationTeamMenuService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamMenu
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 新增团队菜单
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/menu" }, method = RequestMethod.POST)
	public JsonApi teamMenuInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationTeamMenu organizationTeamMenu, BindingResult result) {
		organizationTeamMenu.setCreateDate(new Date());
		organizationTeamMenu.setIsUsed(true);
		// 判断数据是否重复
		Map<String, Object> teamMenuRepeatMap = organizationTeamMenuService.getRepeat(organizationTeamMenu);
		if (MapUtils.isNotEmpty(teamMenuRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("team.menu.code.is.conflict"));
		}
		// 新增团队菜单
		if (organizationTeamMenuService.insert(organizationTeamMenu) > 0) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", organizationTeamMenu.getId());
			return new JsonApi(ApiCode.OK,map);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamMenu
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 查看团队菜单详情
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/menu/{id}" }, method = RequestMethod.GET)
	public JsonApi teamMenuOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OrganizationTeamMenu organizationTeamMenu, BindingResult result) {
		organizationTeamMenu.setId(id);
		// 查看团队菜单详情
		Map<String, Object> teamMenuOneResultMap = organizationTeamMenuService.getOne(organizationTeamMenu);
		if (MapUtils.isNotEmpty(teamMenuOneResultMap)) {
			return new JsonApi(ApiCode.OK,teamMenuOneResultMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamMenu
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 删除团队菜单
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/menu/{id}" }, method = RequestMethod.DELETE)
	public JsonApi teamMenuDelete(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Delete.class }) OrganizationTeamMenu organizationTeamMenu, BindingResult result) {
		organizationTeamMenu.setId(id);
		// 删除团队菜单
		if (organizationTeamMenuService.delete(organizationTeamMenu) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeamMenu
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 查看团队菜单列表
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/menus" }, method = RequestMethod.GET)
	public JsonApi teamMenuList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationTeamMenu organizationTeamMenu, BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationTeamMenu.getPage(), organizationTeamMenu.getPageSize());
		// 查看团队菜单列表
		List<Map<String, Object>> teamOperationsResultList = organizationTeamMenuService.getList(organizationTeamMenu);
		if (teamOperationsResultList !=null && !teamOperationsResultList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),teamOperationsResultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeamMenu
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月15日
	* @version 1.0
	* @description 修改团队菜单
	*/
	@RequiresAuthentication( value = { "team:role:permission:manager" })
	@RequestMapping(value = { "/organizationTeam/menu/{id}" }, method = RequestMethod.PUT)
	public JsonApi teamMenuUpdate(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OrganizationTeamMenu organizationTeamMenu, BindingResult result) {
		// 修改的数据是否存在
		organizationTeamMenu.setId(id);
		Map<String, Object> teamOperationOneMap = organizationTeamMenuService.getOne(organizationTeamMenu);
		if (MapUtils.isEmpty(teamOperationOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 如果修改code唯一标识
		if(StringUtils.isNotEmpty(organizationTeamMenu.getCode())){
			Map<String, Object> teamOperationRepeatOneMap = organizationTeamMenuService.getRepeat(organizationTeamMenu);
			if (MapUtils.isNotEmpty(teamOperationRepeatOneMap)) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("team.menu.code.is.conflict"));
			}
		}
		//修改团队菜单
		if (organizationTeamMenuService.update(organizationTeamMenu) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
