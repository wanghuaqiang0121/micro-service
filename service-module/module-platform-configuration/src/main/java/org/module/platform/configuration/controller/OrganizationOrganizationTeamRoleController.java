package org.module.platform.configuration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.Organization;
import org.module.platform.configuration.domain.OrganizationOrganizationTeamRole;
import org.module.platform.configuration.domain.OrganizationTeamRole;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.OrganizationOrganizationTeamRoleService;
import org.module.platform.configuration.service.OrganizationTeamRoleService;
import org.module.platform.configuration.service.organization.OrganizationService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @Date 2018年7月2日
 * @Version 
 * @Description 机构团队角色
 */
@RestController
public class OrganizationOrganizationTeamRoleController {

	@Autowired
	private OrganizationOrganizationTeamRoleService organizationOrganizationTeamRoleService ;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private OrganizationTeamRoleService organizationTeamRoleService;
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationOrganizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月2日
	* @version 1.0
	* @description 新增机构和机构团队角色关联
	*/
	@RequiresAuthentication(level=Level.OPERATION,value = { "platform:configure:organizationOrganizationTeamRole:insert" })
	@RequestMapping(value = { "/organization/organization/team/role" }, method = RequestMethod.POST)
	public JsonApi organizationOrganizationTeamRoleInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationOrganizationTeamRole organizationOrganizationTeamRole, BindingResult result) {
		//判断机构是否存在
		Organization organization = new Organization();
		organization.setId(organizationOrganizationTeamRole.getOrganizationId());
		Map<String, Object> organizationOneMap = organizationService.getOne(organization);
		if (MapUtils.isEmpty(organizationOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("organization.code.is.not.fonud"));
		}
		//判断团队角色是否存在
		OrganizationTeamRole organizationTeamRole = new OrganizationTeamRole();
		organizationTeamRole.setId(organizationOrganizationTeamRole.getOrganizationTeamRoleId());
		Map<String, Object> organizationTeamRoleOneMap = organizationTeamRoleService.getOne(organizationTeamRole);
		if (MapUtils.isEmpty(organizationTeamRoleOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("organization.team.role.code.is.not.fonud"));
		}
		// 判断数据是否重复
		Map<String, Object> organizationOrganizationTeamRoleRepeatMap = organizationOrganizationTeamRoleService.getRepeat(organizationOrganizationTeamRole);
		if (MapUtils.isNotEmpty(organizationOrganizationTeamRoleRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.organization.team.role.is.conflict"));
		}
		// 关联
		if (organizationOrganizationTeamRoleService.insert(organizationOrganizationTeamRole) > 0) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("id", organizationOrganizationTeamRole.getId());
			return new JsonApi(ApiCode.OK,resultMap);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationOrganizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月2日
	* @version 1.0
	* @description 机构拥有和未拥有的团队角色列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationOrganizationTeamRole:list" })
	@RequestMapping(value = { "/organization/organization/team/role" }, method = RequestMethod.GET)
	public JsonApi organizationOrganizationTeamRoleisChoose(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationOrganizationTeamRole organizationOrganizationTeamRole,BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationOrganizationTeamRole.getPage(), organizationOrganizationTeamRole.getPageSize());
		List<Map<String, Object>> organizationOrganizationTeamRoleList = organizationOrganizationTeamRoleService.getList(organizationOrganizationTeamRole);
		if (organizationOrganizationTeamRoleList != null && !organizationOrganizationTeamRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationOrganizationTeamRoleList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param organizationOrganizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月2日
	* @version 1.0
	* @description 删除机构和团队角色关联
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationOrganizationTeamRole:delete" })
	@RequestMapping(value = { "/organization/organization/team/role/{id}" }, method = RequestMethod.DELETE)
	@Transactional
	public JsonApi organizationOrganizationTeamRoleisDelete(
			@PathVariable("id") Integer id,
			@Validated({ BaseEntity.Delete.class }) OrganizationOrganizationTeamRole organizationOrganizationTeamRole,BindingResult result) {
		organizationOrganizationTeamRole.setId(id);
		Map<String, Object> organizationOrganizationTeamRoleOneMap = organizationOrganizationTeamRoleService.getOne(organizationOrganizationTeamRole);
		if (MapUtils.isEmpty(organizationOrganizationTeamRoleOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		OrganizationOrganizationTeamRole organizationOrganizationTeamRoleNew = new OrganizationOrganizationTeamRole();
		organizationOrganizationTeamRoleNew.setOrganizationId((Integer) organizationOrganizationTeamRoleOneMap.get("organizationId"));
		organizationOrganizationTeamRoleNew.setOrganizationTeamRoleId((Integer) organizationOrganizationTeamRoleOneMap.get("organizationTeamRoleId"));
		List<Map<String, Object>> OrganizationUserTeamRoleOneMap = organizationOrganizationTeamRoleService.getOrganizationUserTeamRoleOne(organizationOrganizationTeamRoleNew);
		// 删除数据
		if (organizationOrganizationTeamRoleService.delete(organizationOrganizationTeamRole) > 0) {
			if (CollectionUtils.isEmpty(OrganizationUserTeamRoleOneMap)) {
				return new JsonApi(ApiCode.OK);
			}
			if (organizationOrganizationTeamRoleService.deleteOrganizationUserTeamRole(organizationOrganizationTeamRoleNew) > 0) {
				return new JsonApi(ApiCode.OK);
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}

}
