package org.module.organization.configure.controller.doctor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.organization.configure.domain.OrganizationUserTeam;
import org.module.organization.configure.domain.OrganizationUserTeamRole;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationUserTeamRoleService;
import org.module.organization.configure.service.doctor.OrganizationUserTeamService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年5月7日
 * @Version 
 * @Description 机构用户,团队关联
 */
@RestController
public class OrganizationUserTeamController {

	@Autowired
	private OrganizationUserTeamService organizationUserTeamService;
	@Autowired
	private OrganizationUserTeamRoleService organizationUserTeamRoleService;
	@Resource
	private RedisCacheManager cacheManager;
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserTeam
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月7日
	* @version 1.0
	* @description 添加机构用户,团队关联
	*/
	@RequiresAuthentication(value = { "organization:configure:organizationUserTeam:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/team" }, method = RequestMethod.POST)
	public JsonApi insert( @Validated({ BaseEntity.Insert.class })@RequestBody OrganizationUserTeam  organizationUserTeam,BindingResult result
			){
		/*判断是否重复*/
		Map<String, Object> organizationUserTeamMap = organizationUserTeamService.getRepeat(organizationUserTeam);
		if (organizationUserTeamMap!=null && !organizationUserTeamMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("doctor.doctor.team.is.exists"));
		}
		organizationUserTeam.setIsManager(false);
		organizationUserTeam.setCreateDate(new Date());
		if (organizationUserTeamService.insert(organizationUserTeam)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserTeam
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月7日
	* @version 1.0
	* @description 移除成员(并删除成员所在团队的所有权限)
	*/
	@RequiresAuthentication(value = { "organization:configure:organizationUserTeam:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/{organizationUserId}/team/{organizationTeamId}" }, method = RequestMethod.DELETE)
	@Transactional
	public JsonApi delete( @Validated({ BaseEntity.Delete.class }) OrganizationUserTeam  organizationUserTeam,BindingResult result,
			@PathVariable("organizationUserId") Integer organizationUserId,
			@PathVariable("organizationTeamId") Integer organizationTeamId
			){
		organizationUserTeam.setOrganizationTeamId(organizationTeamId);
		organizationUserTeam.setOrganizationUserId(organizationUserId);
		/*判断是否重复*/
		Map<String, Object> organizationUserTeamMap = organizationUserTeamService.getRepeat(organizationUserTeam);
		if (organizationUserTeamMap==null || organizationUserTeamMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if ((boolean)organizationUserTeamMap.get("isManager")) {
			return new JsonApi(ApiCode.FAIL,Prompt.bundle("team.leader.cannot.be.deleted"));
		}
		// 移除成员
		if (organizationUserTeamService.delete(organizationUserTeam) > 0) {
			// 删除在团队的角色
			OrganizationUserTeamRole organizationOrganizationTeam = new OrganizationUserTeamRole();
			organizationOrganizationTeam.setOrganizationUserTeamId(Integer.parseInt(organizationUserTeamMap.get("id").toString()));
			// 查询医生所在团队角色
			List<Map<String, Object>> organizationUserRoleList = organizationUserTeamRoleService.getOrganizationUserRoles(organizationOrganizationTeam);
			if(organizationUserRoleList!=null && !organizationUserRoleList.isEmpty()){
				if(organizationUserTeamRoleService.deleteByDoctorDoctorTeamId(organizationOrganizationTeam) > 0){
					return new JsonApi(ApiCode.OK);
				}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserTeam
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月7日
	* @version 1.0
	* @description 医生所属团队列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationUserTeam:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/teams" }, method = RequestMethod.GET)
	public JsonApi getDoctorDoctorTeams( @Validated({ BaseEntity.SelectAll.class }) OrganizationUserTeam  organizationUserTeam,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token){
		// 获取机构用户id
		AuthenticationSession session = cacheManager
				.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		int organizationUserId = (Integer) session.get(Map.class).get("id");
		// 设置机构用户id
		organizationUserTeam.setOrganizationUserId(organizationUserId);
		Page<?> page = PageHelper.startPage(organizationUserTeam.getPage(), organizationUserTeam.getPageSize());
		// 查询列表
		List<Map<String, Object>> organizationUserTeamList = organizationUserTeamService.getList(organizationUserTeam);
		if (organizationUserTeamList!=null && !organizationUserTeamList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserTeamList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserTeam
	* @param result
	* @param organizationTeamId
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月7日
	* @version 1.0
	* @description 团队成员列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationUserTeam:teamDoctors" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/team/{organizationTeamId}/teamDoctors" }, method = RequestMethod.GET)
	public JsonApi getTeamDoctors( @Validated({ OrganizationUserTeam.TeamDoctors.class }) OrganizationUserTeam  organizationUserTeam,BindingResult result,
			@PathVariable("organizationTeamId") Integer organizationTeamId){
		// 设置机构团队id
		organizationUserTeam.setOrganizationTeamId(organizationTeamId);
		Page<?> page = PageHelper.startPage(organizationUserTeam.getPage(), organizationUserTeam.getPageSize());
		// 查询列表
		List<Map<String, Object>> organizationUserTeamList = organizationUserTeamService.getTeamDoctors(organizationUserTeam);
		if (organizationUserTeamList!=null && !organizationUserTeamList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserTeamList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * @param organizationUserTeam
	 * @param result
	 * @param organizationTeamId
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 团队没有的机构用户
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationUserTeam:members" },level=Level.OPERATION)
	@RequestMapping(value = { "/doctor/doctor/team/{organizationTeamId}/members" }, method = RequestMethod.GET)
	public JsonApi getTeamDoctorsMembers( 
			@Validated({ OrganizationUserTeam.SelectAll.class }) OrganizationUserTeam  organizationUserTeam,BindingResult result,
			@PathVariable("organizationTeamId") Integer organizationTeamId){
		// 设置机构团队id
		organizationUserTeam.setOrganizationTeamId(organizationTeamId);
		Page<?> page = PageHelper.startPage(organizationUserTeam.getPage(), organizationUserTeam.getPageSize());
		// 查询列表
		List<Map<String, Object>> organizationUserTeamList = organizationUserTeamService.getOrganizationUserMembers(organizationUserTeam);
		if (organizationUserTeamList!=null && !organizationUserTeamList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserTeamList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
}
