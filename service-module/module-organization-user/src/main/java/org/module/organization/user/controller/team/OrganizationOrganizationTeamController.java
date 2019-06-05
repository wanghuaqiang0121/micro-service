package org.module.organization.user.controller.team;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.organization.user.domain.DoctorDoctorTeam;
import org.module.organization.user.domain.team.OrganizationOrganizationTeam;
import org.module.organization.user.global.BaseGlobal;
import org.module.organization.user.service.DoctorDoctorTeamService;
import org.module.organization.user.service.team.OrganizationOrganizationTeamService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月9日
 * @Version
 * @Description 机构，机构团队关联
 */
@RestController
public class OrganizationOrganizationTeamController {

	@Autowired
	private OrganizationOrganizationTeamService organizationOrganizationTeamService;
	@Autowired
	private DoctorDoctorTeamService doctorDoctorTeamService;
	@Resource
	private RedisCacheManager cacheManager;

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param operationalMenu
	 * @param result
	 * @return
	 * @date 2018年3月8日
	 * @version 1.0
	 * @description 查询用户团队列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:organization:team:teams" })
	@RequestMapping(value = { "/organizationTeam/organization/teams" }, method = RequestMethod.GET)
	public JsonApi getOrganizationOrganizationTeamList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationOrganizationTeam organizationOrganizationTeam,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		organizationOrganizationTeam.setOrganizationId(organizationId);
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		// 设置参数
		organizationOrganizationTeam.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(),organizationOrganizationTeam.getPageSize());
		// 用户团队列表
		List<Map<String, Object>> teamList = organizationOrganizationTeamService.getList(organizationOrganizationTeam);
		if (teamList != null && !teamList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), teamList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月9日
	 * @version 1.0
	 * @description 查询机构用户团队角色列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:organization:team:teamRoleList" })
	@RequestMapping(value = { "/organizationTeam/organization/team/roles" }, method = RequestMethod.GET)
	public JsonApi getOrganizationOrganizationTeamRoles(
			@Validated({OrganizationOrganizationTeam.TeamRoles.class }) OrganizationOrganizationTeam organizationOrganizationTeam,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId) {
		organizationOrganizationTeam.setOrganizationTeamId(organizationTeamId);
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationOrganizationTeam.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		// 查询用户在团队是否团队长
		DoctorDoctorTeam doctorDoctorTeam = new DoctorDoctorTeam();
		doctorDoctorTeam.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		doctorDoctorTeam.setOrganizationTeamId(organizationTeamId);
		// 医生医生团队详情
		Map<String, Object> doctorDoctorTeamOneMap = doctorDoctorTeamService.getOne(doctorDoctorTeam);
		if (MapUtils.isNotEmpty(doctorDoctorTeamOneMap)) {
			boolean isManager = (boolean) doctorDoctorTeamOneMap.get("isManager");
			// 用户在团队是团队长
			if (isManager) {
				Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(),organizationOrganizationTeam.getPageSize());
				// 查询团队长团队角色=所有团队角色
				List<Map<String, Object>> teamRoleList = organizationOrganizationTeamService.getOrganizationOrganizationTeamManagerRoles(organizationOrganizationTeam);
				if (teamRoleList != null && !teamRoleList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), teamRoleList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			} else {// 用户在团队不是团队长
				Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(),organizationOrganizationTeam.getPageSize());
				// 查询用户在团队拥有的角色
				List<Map<String, Object>> teamRoleList = organizationOrganizationTeamService.getOrganizationOrganizationTeamRoles(organizationOrganizationTeam);
				if (teamRoleList != null && !teamRoleList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), teamRoleList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月9日
	 * @version 1.0
	 * @description 查询机构用户团队权限列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:organization:team:teamPermissionList" })
	@RequestMapping(value = { "/organizationTeam/organization/team/permissions" }, method = RequestMethod.GET)
	public JsonApi getOrganizationOrganizationTeamPermissions(
			@Validated({OrganizationOrganizationTeam.TeamPermissions.class }) OrganizationOrganizationTeam organizationOrganizationTeam,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId) {
		organizationOrganizationTeam.setOrganizationTeamId(organizationTeamId);
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationOrganizationTeam.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		// 查询用户在团队是否团队长
		DoctorDoctorTeam doctorDoctorTeam = new DoctorDoctorTeam();
		doctorDoctorTeam.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		doctorDoctorTeam.setOrganizationTeamId(organizationTeamId);
		Map<String, Object> doctorDoctorTeamOneMap = doctorDoctorTeamService.getOne(doctorDoctorTeam);
		if (MapUtils.isNotEmpty(doctorDoctorTeamOneMap)) {
			boolean isManager = (boolean) doctorDoctorTeamOneMap.get("isManager");
			// 用户在团队是团队长
			if (isManager) {
				Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(),organizationOrganizationTeam.getPageSize());
				// 查询团队长团队权限=团队所有权限
				List<Map<String, Object>> teamPermissionList = organizationOrganizationTeamService.getOrganizationOrganizationTeamManagerPermissions(organizationOrganizationTeam);
				if (teamPermissionList != null && !teamPermissionList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), teamPermissionList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			} else {// 用户在团队不是团队长
				Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(),organizationOrganizationTeam.getPageSize());
				// 用户在团队拥有的权限
				List<Map<String, Object>> teamPermissionList = organizationOrganizationTeamService.getOrganizationOrganizationTeamPermissions(organizationOrganizationTeam);
				if (teamPermissionList != null && !teamPermissionList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), teamPermissionList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月9日
	 * @version 1.0
	 * @description 查询机构用户团队菜单列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:organization:team:teamMenuList" })
	@RequestMapping(value = { "/organizationTeam/organization/team/menus" }, method = RequestMethod.GET)
	public JsonApi getOrganizationOrganizationTeamMenus(
			@Validated({OrganizationOrganizationTeam.TeamMenus.class }) OrganizationOrganizationTeam organizationOrganizationTeam,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		organizationOrganizationTeam.setOrganizationTeamId(organizationTeamId);
		organizationOrganizationTeam.setOrganizationId(organizationId);
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationOrganizationTeam.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		// 查询用户在团队是否团队长
		DoctorDoctorTeam doctorDoctorTeam = new DoctorDoctorTeam();
		doctorDoctorTeam.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		doctorDoctorTeam.setOrganizationTeamId(organizationTeamId);
		Map<String, Object> doctorDoctorTeamOneMap = doctorDoctorTeamService.getOne(doctorDoctorTeam);
		if (MapUtils.isNotEmpty(doctorDoctorTeamOneMap)) {
			boolean isManager = (boolean) doctorDoctorTeamOneMap.get("isManager");
			// 用户在团队是团队长
			/* （团队长默认继承机构拥有的团队权限）根据机构id查询机构拥有的团队权限 */
			if (isManager) {
				Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(),organizationOrganizationTeam.getPageSize());
				// 团队所有菜单
				List<Map<String, Object>> teamMenuList = organizationOrganizationTeamService.getOrganizationOrganizationTeamManagerMenus(organizationOrganizationTeam);
				if (teamMenuList != null && !teamMenuList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), teamMenuList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			} else {// 用户在团队不是团队长
				Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(),organizationOrganizationTeam.getPageSize());
				// 查询用户在团队拥有的菜单
				List<Map<String, Object>> teamMenuList = organizationOrganizationTeamService.getOrganizationOrganizationTeamMenus(organizationOrganizationTeam);
				if (teamMenuList != null && !teamMenuList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), teamMenuList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月9日
	 * @version 1.0
	 * @description 查询机构用户团队操作列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:organization:team:teamOperationList" })
	@RequestMapping(value = { "/organizationTeam/organization/team/operations" }, method = RequestMethod.GET)
	public JsonApi getOrganizationOrganizationTeamOperations(
			@Validated({
					OrganizationOrganizationTeam.TeamOperations.class }) OrganizationOrganizationTeam organizationOrganizationTeam,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId) {
		organizationOrganizationTeam.setOrganizationTeamId(organizationTeamId);
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationOrganizationTeam.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		// 查询用户在团队是否团队长
		DoctorDoctorTeam doctorDoctorTeam = new DoctorDoctorTeam();
		doctorDoctorTeam.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		doctorDoctorTeam.setOrganizationTeamId(organizationTeamId);
		Map<String, Object> doctorDoctorTeamOneMap = doctorDoctorTeamService.getOne(doctorDoctorTeam);
		if (MapUtils.isNotEmpty(doctorDoctorTeamOneMap)) {
			boolean isManager = (boolean) doctorDoctorTeamOneMap.get("isManager");
			// 是团队长
			if (isManager) {
				Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(),organizationOrganizationTeam.getPageSize());
				// 查询团队所有操作
				List<Map<String, Object>> teamOperationList = organizationOrganizationTeamService.getOrganizationOrganizationTeamManagerOperation(organizationOrganizationTeam);
				if (teamOperationList != null && !teamOperationList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), teamOperationList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			} else {// 不是团队长
				Page<?> page = PageHelper.startPage(organizationOrganizationTeam.getPage(),organizationOrganizationTeam.getPageSize());
				// 查询用户在团队的操作
				List<Map<String, Object>> teamOperationList = organizationOrganizationTeamService.getOrganizationOrganizationTeamOperation(organizationOrganizationTeam);
				if (teamOperationList != null && !teamOperationList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), teamOperationList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
