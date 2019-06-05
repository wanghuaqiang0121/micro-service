package org.module.organization.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.organization.user.domain.DoctorOrganizationDepartmentDuty;
import org.module.organization.user.domain.OrganizationUser;
import org.module.organization.user.global.BaseGlobal;
import org.module.organization.user.global.IOrganization.OrganizationStatus;
import org.module.organization.user.message.Prompt;
import org.module.organization.user.service.DoctorOrganizationDepartmentDutyService;
import org.module.organization.user.service.OrganizationUserService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.core.entity.BaseEntity.SelectAll;
import org.service.core.entity.BaseEntity.Update;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.service.tools.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2018年1月8日
 * @version 1.0
 * @description 机构用户
 */
@RestController
public class OrganizationUserController {

	@Autowired
	private OrganizationUserService organizationUserService;
	@Autowired
	private DoctorOrganizationDepartmentDutyService doctorOrganizationDepartmentDutyService;
	@Resource
	private RedisCacheManager cacheManager;

	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param authenticationToken
	* @return 
	* @date 2018年11月30日
	* @version 1.0
	* @description 通过token 获取用户id
	*/
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/organization/user/info/by/token" }, method = RequestMethod.GET)
	public JsonApi getSession(String cacheName, String key,@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		if (cacheName == null || cacheName == "") {
			throw new RuntimeException("redis cache name is empty");
		}
		/* 缓存key */
		if (key == null || key.length() <= 32) {
			throw new RuntimeException("login token identify length lacks");
		}
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			AuthenticationSession session = cache.get("1"+key.substring(33), AuthenticationSession.class);
			if (session != null) {
				return key.equals(session.getIdentify()) ? new JsonApi(ApiCode.OK,session.get(Map.class).get("id").toString()) : null;
			}
		}
		return new JsonApi(ApiCode.FAIL);
	}
	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param user
	 * @param result
	 * @return {@link JsonApi}
	 * @date 2018年3月8日
	 * @version 1.0
	 * @description 机构用户登录
	 */
	@RequiresAuthentication(ignore = true, value = { "organization:user:organizationUserLogin" })
	@RequestMapping(value = { "/organizationUser/login" }, method = RequestMethod.GET)
	public JsonApi organizationUserLogin(@Validated({ OrganizationUser.Login.class }) OrganizationUser user,
			BindingResult result) {
		String password = user.getPassword();
		user.setPassword(MD5Util.getInstance().getMD5Code(password));
		// 查询账号是否存在
		List<Map<String, Object>> userMap = organizationUserService.getUserByAccount(user);
		// 账号不存在
		if (userMap == null || userMap.isEmpty()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.user.account.is.not.exists"));
		}
		else if (userMap != null && !userMap.isEmpty()) {// 账号存在
			List<Map<String, Object>> userList = organizationUserService.getUserByAccountAndPassword(user);
			if (userList == null || userList.isEmpty()) {
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.user.password.is.not.exists"));
			}
			/* 如果查询出来的userList中organizationStatus 全都被禁用 不允许用户登录*/
			List<Map<String, Object>> organizationStatusList = userList.stream().filter(map -> (Integer)map.get("organizationStatus")== OrganizationStatus.ENABLE.getValue()).collect(Collectors.toList());
			if (organizationStatusList == null || organizationStatusList.isEmpty()) {
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.status.is.disabled"));
			}
			/* 如果查询出来的userList中bdoddStatus（关联表状态） 全都被禁用 不允许用户登录*/
			List<Map<String, Object>> bdoddStatusList = userList.stream().filter(map -> (Integer)map.get("bdoddStatus")== OrganizationStatus.ENABLE.getValue()).collect(Collectors.toList());
			if (bdoddStatusList == null || bdoddStatusList.isEmpty()) {
				// 判断用户是否启用
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.user.status.is.disabled"));
			}
			// 登录成功
			// 存入缓存 1 代表平台
			String token = "1"+MD5Util.getInstance().getSessionToken(userList.get(0).get("id"));
			/* 取一条数据去除机构相关信息 */
			Map<String, Object> userResultMap = userList.get(0);
			Integer organizationUserId = (Integer) userResultMap.get("id");
			user.setId(organizationUserId);
			user.setCertificateType("1");
			Map<String, Object> organizationUserCertificateMap = organizationUserService.getOrganizationUserCertificate(user);
			
			userResultMap.put("certificateNumber", organizationUserCertificateMap != null ? organizationUserCertificateMap.get("certificateNumber") : null);
			userResultMap.put("positive", organizationUserCertificateMap != null ? organizationUserCertificateMap.get("positive") : null);
			userResultMap.put("opposite", organizationUserCertificateMap != null ? organizationUserCertificateMap.get("opposite") : null);
			userResultMap.remove("bdoddStatus");
			// 放入缓存 已实现自动踢出
			cacheManager.AuthenticationToken(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER,
					"1"+userList.get(0).get("id").toString(), new AuthenticationSession(token, userResultMap)));
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("info", userResultMap);
			resultMap.put("token", token);
			// 返回用户信息
			return new JsonApi(ApiCode.OK, resultMap);
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.user.login.account.error"));
	}

	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param user
	* @param result
	* @param token
	* @return 
	* @date 2018年5月31日
	* @version 1.0
	* @description 修改密码
	*/
	@RequiresAuthentication(authc = true, value = { "organization:user:organizationUser:changePassword" })
	@RequestMapping(value = { "/organizationUser/change/password" }, method = RequestMethod.PUT)
	public JsonApi organizationUserChangePassword(
			@Validated({ OrganizationUser.updatePassword.class })@RequestBody OrganizationUser organizationUser,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		Integer organizationUserId = Integer.parseInt(session.get(Map.class).get("id").toString());
		organizationUser.setId(organizationUserId);
		// 机构用户详情
		Map<String, Object> organizationUserOneMap = organizationUserService.getOne(organizationUser);
		String oldPassword = (String) organizationUserOneMap.get("password");
		if (!oldPassword.equals(MD5Util.getInstance().getMD5Code(organizationUser.getPassword()))) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.user.old.password.error"));
		}
		OrganizationUser organizationUserNew = new OrganizationUser();
		organizationUserNew.setId(organizationUserId);
		organizationUserNew.setPassword(MD5Util.getInstance().getMD5Code(organizationUser.getNewPassword()));
		organizationUserNew.setIsInitPassword(false);
		if (organizationUserService.update(organizationUserNew) >0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 通过token查用户
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:token:detail" })
	@RequestMapping(value = { "/organizationUser/token/user" }, method = RequestMethod.GET)
	public JsonApi organizationUserTokenDetail(
			@Validated({ BaseEntity.SelectOne.class }) OrganizationUser organizationUser, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		@SuppressWarnings("unchecked")
		Map<String, Object> organizationUserMap = session.get(Map.class);
		if (MapUtils.isEmpty(organizationUserMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		return new JsonApi(ApiCode.OK, organizationUserMap);
	}


	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月9日
	 * @version 1.0
	 * @description 查询机构用户模块菜单列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:getOrganizationUserModuleMenuList" })
	@RequestMapping(value = { "/organizationUser/module/menus" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserModuleMenuList(
			@Validated({ OrganizationUser.ModuleMenuList.class }) OrganizationUser organizationUser,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		organizationUser.setModuleId(moduleId);
		organizationUser.setOrganizationId(organizationId);
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationUser.setId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		/* 判断是否是超级管理员 */
		DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty = new DoctorOrganizationDepartmentDuty();
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		doctorOrganizationDepartmentDuty.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		Map<String, Object> repeatDoctorOrganizationMap = doctorOrganizationDepartmentDutyService.getRepeat(doctorOrganizationDepartmentDuty);
		if (MapUtils.isNotEmpty(repeatDoctorOrganizationMap)) {
			boolean isManager = (boolean) repeatDoctorOrganizationMap.get("isManager");
			/* 是超级管理员 */
			if (isManager) {
				Page<?> page = PageHelper.startPage(organizationUser.getPage(), organizationUser.getPageSize());
				// 查询该机构该模块下所有菜单
				List<Map<String, Object>> ManagerModuleMenuList = organizationUserService.getManagerModuleMenuList(organizationUser);
				if (ManagerModuleMenuList != null && !ManagerModuleMenuList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), ManagerModuleMenuList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			} else {
				/* 不是超级管理员 */
				Page<?> page = PageHelper.startPage(organizationUser.getPage(), organizationUser.getPageSize());
				// 查询该机构该用户该模块下所有菜单
				List<Map<String, Object>> organizationUserMenuList = organizationUserService.getOrganizationUserModuleMenuList(organizationUser);
				if (organizationUserMenuList != null && !organizationUserMenuList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserMenuList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
		
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @return {@link JsonApi}
	 * @date 2018年3月12日
	 * @version 1.0
	 * @description 查询用户模块角色列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:getOrganizationUserModuleRoleList" })
	@RequestMapping(value = { "/organizationUser/module/roles" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserModuleRoleList(
			@Validated({ OrganizationUser.ModuleRoleList.class }) OrganizationUser organizationUser,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		organizationUser.setModuleId(moduleId);
		organizationUser.setOrganizationId(organizationId);
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationUser.setId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		/* 判断是否是超级管理员 */
		DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty = new DoctorOrganizationDepartmentDuty();
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		doctorOrganizationDepartmentDuty.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		Map<String, Object> repeatDoctorOrganizationMap = doctorOrganizationDepartmentDutyService.getRepeat(doctorOrganizationDepartmentDuty);
		if (MapUtils.isNotEmpty(repeatDoctorOrganizationMap)) {
			boolean isManager = (boolean) repeatDoctorOrganizationMap.get("isManager");
			/* 是超级管理员 */
			if (isManager) {
				Page<?> page = PageHelper.startPage(organizationUser.getPage(), organizationUser.getPageSize());
				// 查询机构角色列表
				List<Map<String, Object>> ManagerRoleList = organizationUserService.getManagerRoleList(organizationUser);
				if (ManagerRoleList != null && !ManagerRoleList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), ManagerRoleList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			} else {
				/* 不是超级管理员 */
				Page<?> page = PageHelper.startPage(organizationUser.getPage(), organizationUser.getPageSize());
				List<Map<String, Object>> organizationUserRoleList = organizationUserService.getOrganizationUserModuleRoleList(organizationUser);
				if (organizationUserRoleList != null && !organizationUserRoleList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserRoleList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @return {@link JsonApi}
	 * @date 2018年3月12日
	 * @version 1.0
	 * @description 查询用户模块权限列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:getOrganizationUserModulePermissionList" })
	@RequestMapping(value = { "/organizationUser/module/permissions" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserModulePermissionList(
			@Validated({ OrganizationUser.ModulePermissionList.class }) OrganizationUser organizationUser,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		organizationUser.setModuleId(moduleId);
		organizationUser.setOrganizationId(organizationId);
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationUser.setId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		/* 判断是否是超级管理员 */
		DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty = new DoctorOrganizationDepartmentDuty();
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		doctorOrganizationDepartmentDuty.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		Map<String, Object> repeatDoctorOrganizationMap = doctorOrganizationDepartmentDutyService.getRepeat(doctorOrganizationDepartmentDuty);
		if (MapUtils.isNotEmpty(repeatDoctorOrganizationMap)) {
			boolean isManager = (boolean) repeatDoctorOrganizationMap.get("isManager");
			/* 是超级管理员 */
			if (isManager) {
				Page<?> page = PageHelper.startPage(organizationUser.getPage(), organizationUser.getPageSize());
				List<Map<String, Object>> ManagerPermissionList = organizationUserService.getManagerPermissionList(organizationUser);
				if (ManagerPermissionList != null && !ManagerPermissionList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), ManagerPermissionList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			} else {
				/* 不是超级管理员 */
				Page<?> page = PageHelper.startPage(organizationUser.getPage(), organizationUser.getPageSize());
				List<Map<String, Object>> organizationUserPermissionList = organizationUserService.getOrganizationUserModulePermissionList(organizationUser);
				if (organizationUserPermissionList != null && !organizationUserPermissionList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserPermissionList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @return {@link JsonApi}
	 * @date 2018年3月12日
	 * @version 1.0
	 * @description 查询用户模块操作列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:getOrganizationUserModuleOperationList" })
	@RequestMapping(value = { "/organizationUser/module/operations" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserModuleOperationList(
			@Validated({ OrganizationUser.ModuleOperationList.class }) OrganizationUser organizationUser,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		organizationUser.setModuleId(moduleId);
		organizationUser.setOrganizationId(organizationId);
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationUser.setId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		/* 判断是否是超级管理员 */
		DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty = new DoctorOrganizationDepartmentDuty();
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		doctorOrganizationDepartmentDuty.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		Map<String, Object> repeatDoctorOrganizationMap = doctorOrganizationDepartmentDutyService.getRepeat(doctorOrganizationDepartmentDuty);
		if (MapUtils.isNotEmpty(repeatDoctorOrganizationMap)) {
			boolean isManager = (boolean) repeatDoctorOrganizationMap.get("isManager");
			/* 是超级管理员 */
			if (isManager) {
				Page<?> page = PageHelper.startPage(organizationUser.getPage(), organizationUser.getPageSize());
				List<Map<String, Object>> ManagerOperationList = organizationUserService.getManagerOperationList(organizationUser);
				if (ManagerOperationList != null && !ManagerOperationList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), ManagerOperationList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			} else {
				/* 不是超级管理员 */
				Page<?> page = PageHelper.startPage(organizationUser.getPage(), organizationUser.getPageSize());
				List<Map<String, Object>> organizationUserOperationList = organizationUserService.getOrganizationUserModuleOperationList(organizationUser);
				if (organizationUserOperationList != null && !organizationUserOperationList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserOperationList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
			}
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @return {@link JsonApi}
	 * @date 2018年3月12日
	 * @version 1.0
	 * @description 修改用户信息
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:update" })
	@RequestMapping(value = { "/organizationUser/update" }, method = RequestMethod.PUT)
	public JsonApi updateOrganizationUser(@Validated({ Update.class }) @RequestBody OrganizationUser organizationUser,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		// 查询用户缓存数据
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationUser.setId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		if (organizationUserService.update(organizationUser) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 查询机构用户的机构列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:organizations" })
	@RequestMapping(value = { "/organizationUser/organizations" }, method = RequestMethod.GET)
	public JsonApi getOrganzationUserOrganzationList(
			@Validated({ SelectAll.class }) OrganizationUser organizationUser,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {		
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationUser.setId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		Page<?> page = PageHelper.startPage(organizationUser.getPage(), organizationUser.getPageSize());
		List<Map<String, Object>> organizationList = organizationUserService.getOrganzationUserOrganzationList(organizationUser);
		if (organizationList != null && !organizationList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年11月20日
	 * @version 1.0
	 * @description 用于数据回传（杭创）
	 */
	@RequiresAuthentication(authc = true, value = { "organization:user:data:return" })
	@RequestMapping(value = { "/organizationUser/data/return" }, method = RequestMethod.GET)
	public JsonApi getDataReturn(OrganizationUser organizationUser,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId){
		/*获取缓存信息*/
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		/*设置用户ID*/
		organizationUser.setId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		/*设置机构ID*/
		organizationUser.setOrganizationId(organizationId);
		
		Map<String, Object> dataReturnList = organizationUserService.getDataReturn(organizationUser);
		if (dataReturnList != null && !dataReturnList.isEmpty()) {
			return new JsonApi(ApiCode.OK,  dataReturnList);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
