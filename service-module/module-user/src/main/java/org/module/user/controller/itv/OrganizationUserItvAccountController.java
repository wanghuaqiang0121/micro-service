package org.module.user.controller.itv;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.user.domain.itv.OrganizationUser;
import org.module.user.domain.itv.OrganizationUserItvAccount;
import org.module.user.global.BaseGlobal;
import org.module.user.global.UserStatusCode;
import org.module.user.message.Prompt;
import org.module.user.service.OrganizationUserItvAccountService;
import org.module.user.service.OrganizationUserService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.service.tools.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
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
 * @Date 2018年4月10日
 * @Version 
 * @Description itv医生
 */
@RestController
public class OrganizationUserItvAccountController {

	@Autowired
	private OrganizationUserItvAccountService organizationUserItvAccountService;
	
	@Autowired
	private OrganizationUserService organizationUserService;
	
	@Resource
	private RedisCacheManager cacheManager;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserItvAccount
	* @param result
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description itv绑定医生
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:organizationUserItvAccount:binding" })
	@RequestMapping(value = { "/itv/doctor/account/binding" }, method = RequestMethod.POST)
	public JsonApi bindingDoctor(
			@Validated(BaseEntity.Insert.class)@RequestBody OrganizationUserItvAccount organizationUserItvAccount, BindingResult result) {
		// 根据电话号码查询医生
		OrganizationUser organizationUser = new OrganizationUser();
		organizationUser.setPhone(organizationUserItvAccount.getPhone());
		Map<String, Object> organizationUserResultMap = organizationUserService.getDoctorByPhone(organizationUser);
		if (MapUtils.isEmpty(organizationUserResultMap)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.user.is.not.exists"));
		}
		// 拿到验证码
		String validCode = organizationUserItvAccount.getValidCode();
		ValueWrapper valueWrapper = cacheManager.getCache(BaseGlobal.CACHE_CODE).get(organizationUserItvAccount.getPhone());
		if (null == valueWrapper) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("valid.code.error"));
		}
		String code = (String) valueWrapper.get();
		if (!validCode.equals(code)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("valid.code.error"));
		}
		// 清除缓存
		cacheManager.getCache(BaseGlobal.CACHE_CODE).evict(organizationUserItvAccount.getPhone());
		
		organizationUserItvAccount.setStatus(UserStatusCode.ItvAccountStatus.ENABLE.getValue());
		organizationUserItvAccount.setOrganizationUserId(Integer.parseInt(organizationUserResultMap.get("id").toString()));
		// 判断是否重复
		Map<String, Object> repeatResultMap = organizationUserItvAccountService.getRepeat(organizationUserItvAccount);
		if (MapUtils.isNotEmpty(repeatResultMap)) {
			return new JsonApi(ApiCode.CONFLICT);
		}
		organizationUserItvAccount.setCreateDate(new Date());
		// itv绑定医生
		if (organizationUserItvAccountService.insert(organizationUserItvAccount) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organizationUserItvAccount
	 * @param result
	 * @return 
	 * @date 2018年4月9日
	 * @version 1.0
	 * @description 解除医生绑定
	 */
	@RequiresAuthentication(ignore = true, value = { "itv:organizationUserItvAccount:unbind" })
	@RequestMapping(value = { "/itv/doctor/account/unbind/{id}" }, method = RequestMethod.DELETE)
	public JsonApi unbindDoctor(@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@PathVariable("id") Integer id,
			@Validated(OrganizationUserItvAccount.Unbind.class) OrganizationUserItvAccount organizationUserItvAccount, BindingResult result) {
		// 设置用户id
		organizationUserItvAccount.setId(id);
		// 删除
		if (organizationUserItvAccountService.delete(organizationUserItvAccount) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserItvAccount
	* @param result
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description itv绑定医生列表
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:organizationUserItvAccount:doctors" })
	@RequestMapping(value = { "/itv/doctor/account/doctors" }, method = RequestMethod.GET)
	public JsonApi getItvDoctors(
			@Validated(OrganizationUserItvAccount.ItvOrganizationUsers.class) OrganizationUserItvAccount organizationUserItvAccount, BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationUserItvAccount.getPage(), organizationUserItvAccount.getPageSize());
		// itv绑定医生列表
		List<Map<String, Object>> doctorsResultList = organizationUserItvAccountService.getList(organizationUserItvAccount);
		if (CollectionUtils.isNotEmpty(doctorsResultList)) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), doctorsResultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserItvAccount
	* @param result
	* @return 
	* @date 2018年4月10日
	* @version 1.0 
	* @description itv医生登录 获取token
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:organizationUserItvAccount:login" })
	@RequestMapping(value = { "/itv/doctor/account/login" }, method = RequestMethod.GET)
	public JsonApi login(
			@Validated(OrganizationUserItvAccount.Login.class) OrganizationUserItvAccount organizationUserItvAccount, BindingResult result) {
		// 根据itv账号和用户id查询用户信息
		organizationUserItvAccount.setStatus(UserStatusCode.ItvAccountStatus.ENABLE.getValue());
		Map<String, Object> itvDoctorMap = organizationUserItvAccountService.getOne(organizationUserItvAccount);
		if (itvDoctorMap != null && itvDoctorMap.size() > 0) {
			// 查看缓存是否存在
			String doctorId = itvDoctorMap.get("organizationUserId").toString();
			AuthenticationSession authenticationSession = cacheManager.getCache(BaseGlobal.CACHE_ORGANIZATION_USER).get(doctorId, AuthenticationSession.class);
			if (authenticationSession != null) {
				// 查询医生所在机构对应的团队
				OrganizationUser organizationUser=new OrganizationUser();
				organizationUser.setId(Integer.parseInt(doctorId));
				List<Map<String, Object>> organizationAndTeamsMap =organizationUserService.getOrganizationAndTeams(organizationUser);
				Map<String, Object> resultMap = new HashMap<>();
				@SuppressWarnings("unchecked")
				Map<String,Object> doctorMapCache = authenticationSession.get(Map.class);
				String tokenCache = authenticationSession.getIdentify();
				resultMap.put("info", doctorMapCache);
				resultMap.put("token", tokenCache);
				resultMap.put("organizations", organizationAndTeamsMap);
				return new JsonApi(ApiCode.OK, resultMap);
			}else {
				// 查询机构用户详情
				OrganizationUser organizationUser=new OrganizationUser();
				organizationUser.setId(Integer.parseInt(itvDoctorMap.get("organizationUserId").toString()));
				Map<String, Object> doctorMap = organizationUserService.getLoginMsg(organizationUser);
				if (MapUtils.isEmpty(doctorMap)) {
					return new JsonApi(ApiCode.FAIL);
				}
				// 查询医生所在机构对应的团队
				List<Map<String, Object>> organizationAndTeamsList =organizationUserService.getOrganizationAndTeams(organizationUser);
				String token = MD5Util.getInstance().getSessionToken(doctorMap.get("id"));
				// 放入缓存 已实现自动踢出
				cacheManager.AuthenticationToken(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, doctorMap.get("id").toString(), new AuthenticationSession(token, doctorMap)));
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("info", doctorMap);
				resultMap.put("token", token);
				resultMap.put("organizations", organizationAndTeamsList);
				// 返回用户信息
				return new JsonApi(ApiCode.OK, resultMap);
				}
			}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserItvAccount
	* @param result
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description 医生验证密码
	*/
	@RequiresAuthentication(authc = true, value = { "itv:organizationUserItvAccount:validDoctorPassword" })
	@RequestMapping(value = { "/itv/doctor/account/valid/password" }, method = RequestMethod.GET)
	public JsonApi validDoctorPassword(@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@Validated(OrganizationUserItvAccount.validDoctorPassword.class) OrganizationUserItvAccount organizationUserItvAccount, BindingResult result) {
		organizationUserItvAccount.setId(null);
		organizationUserItvAccount.setStatus(UserStatusCode.ItvAccountStatus.ENABLE.getValue());
		// 查询详情
		Map<String, Object> validDoctorResultMap = organizationUserItvAccountService.getOne(organizationUserItvAccount);
		if (MapUtils.isNotEmpty(validDoctorResultMap)) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
}
