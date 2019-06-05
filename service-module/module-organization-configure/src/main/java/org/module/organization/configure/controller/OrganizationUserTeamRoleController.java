package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.organization.configure.domain.OrganizationUserTeamRole;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.global.TeamStatusCode;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationUserTeamRoleService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月20日
 * @Version 
 * @Description 机构用户所在团队和团队角色关联表
 */
@RestController
public class OrganizationUserTeamRoleController {
	
	@Autowired
	private OrganizationUserTeamRoleService organizationOrganizationTeamService;
	
	@Resource
	private RedisCacheManager cacheManager;
	
	/**
	 * 
	* @author <font color="green"><b>Chen.Yan</b></font>
	* @param organizationOrganizationTeam
	* @param token
	* @param organizationId
	* @param moduleId
	* @param result
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 新增机构用户所在团队和团队角色关联
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationUserTeamRole:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/team/role" }, method = RequestMethod.POST)
	public JsonApi insert( @Validated({ BaseEntity.Insert.class })@RequestBody OrganizationUserTeamRole  organizationOrganizationTeam,BindingResult result
			){
		/*设置状态*/
		organizationOrganizationTeam.setStatus(TeamStatusCode.OrganizationTeam.ENABLE.getValue());
		/*判断是否重复*/
		Map<String, Object> organizationOrganizationTeamMap = organizationOrganizationTeamService.getRepeat(organizationOrganizationTeam);
		if (organizationOrganizationTeamMap!=null && !organizationOrganizationTeamMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.rganization.team.role.is.exists"));
		}
		if (organizationOrganizationTeamService.insert(organizationOrganizationTeam)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param doctorDoctorTeamId
	 * @param organizationTeamRoleId
	 * @param organizationOrganizationTeam
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 删除机构用户所在团队和团队角色关联（删除用户在团队具体角色）
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationUserTeamRole:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/{organizationUserTeamId}/organization/team/{organizationTeamRoleId}" }, method = RequestMethod.DELETE)
	public JsonApi delete(
			@PathVariable("organizationUserTeamId") Integer organizationUserTeamId,
			@PathVariable("organizationTeamRoleId") Integer organizationTeamRoleId,
			@Validated({ BaseEntity.Delete.class }) OrganizationUserTeamRole  organizationOrganizationTeam,BindingResult result){
		organizationOrganizationTeam.setOrganizationUserTeamId(organizationUserTeamId);
		organizationOrganizationTeam.setOrganizationTeamRoleId(organizationTeamRoleId);
		if (organizationOrganizationTeamService.delete(organizationOrganizationTeam)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param organizationOrganizationTeam
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 删除机构用户所在团队和团队角色关联
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationUserTeamRole:role:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/organization/team/{id}" }, method = RequestMethod.DELETE)
	public JsonApi deleteRole(
			@PathVariable("id") Integer id,		
			@Validated({ BaseEntity.Delete.class }) OrganizationUserTeamRole  organizationOrganizationTeam,BindingResult result){
		organizationOrganizationTeam.setId(id);
		if (organizationOrganizationTeamService.deleteRole(organizationOrganizationTeam)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationOrganizationTeam
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description  查询机构用户在这个团队拥有和未拥有的权限
	 */
	@RequiresAuthentication(value = { "organization:configure:department:role:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/team/roles" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserRoleIsChoose(@Validated({OrganizationUserTeamRole.GetOrganizationUserRoleIsChoose.class })  OrganizationUserTeamRole  organizationOrganizationTeam,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		organizationOrganizationTeam.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(), organizationOrganizationTeam.getPageSize());
		List<Map<String, Object>>  organizationUserRoleList=organizationOrganizationTeamService.getOrganizationUserRoleIsChoose(organizationOrganizationTeam);
		if (organizationUserRoleList!=null && !organizationUserRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserRoleList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
}
