package org.module.user.controller.itv;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.user.domain.User;
import org.module.user.domain.itv.UserItvAccount;
import org.module.user.global.BaseGlobal;
import org.module.user.global.UserStatusCode;
import org.module.user.message.Prompt;
import org.module.user.service.UserItvAccountService;
import org.module.user.service.UserService;
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
 * @Description itv用户
 */
@RestController
public class UserItvAccountController {
	
	@Autowired
	private UserItvAccountService  userItvAccountService;
	
	@Autowired
	private UserService  userService;
	
	@Resource
	private RedisCacheManager cacheManager;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userItvAccount
	* @param result
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 绑定用户
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:userItvAccount:binding" })
	@RequestMapping(value = { "/user/itv/account/binding" }, method = RequestMethod.POST)
	public JsonApi bindingUser(
			@Validated(BaseEntity.Insert.class)@RequestBody UserItvAccount userItvAccount, BindingResult result) {
		// 根据电话号码查询用户
		User user = new User();
		user.setPhone(userItvAccount.getPhone());
		Map<String, Object> userResultMap = userService.getUserByPhone(user);
		if (MapUtils.isEmpty(userResultMap)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.is.not.exists"));
		}
		// 拿到验证码
		String validCode = userItvAccount.getValidCode();
		ValueWrapper valueWrapper = cacheManager.getCache(BaseGlobal.CACHE_CODE).get(userItvAccount.getPhone());
		if (null == valueWrapper) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("valid.code.error"));
		}
		String code = (String) valueWrapper.get();
		if (!validCode.equals(code)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("valid.code.error"));
		}
		// 清除缓存
		cacheManager.getCache(BaseGlobal.CACHE_CODE).evict(userItvAccount.getPhone());
		
		userItvAccount.setStatus(UserStatusCode.ItvAccountStatus.ENABLE.getValue());
		userItvAccount.setUserId(Integer.parseInt(userResultMap.get("id").toString()));
		// 判断是否重复
		Map<String, Object> repeatResultMap = userItvAccountService.getRepeat(userItvAccount);
		if (MapUtils.isNotEmpty(repeatResultMap)) {
			return new JsonApi(ApiCode.CONFLICT);
		}
		userItvAccount.setCreateDate(new Date());
		// 绑定用户
		if (userItvAccountService.insert(userItvAccount) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param userItvAccount
	 * @param result
	 * @return 
	 * @date 2018年4月9日
	 * @version 1.0
	 * @description 解除用户绑定
	 */
	@RequiresAuthentication(ignore = true, value = { "itv:userItvAccount:unbind" })
	@RequestMapping(value = { "/user/itv/account/unbind/{id}" }, method = RequestMethod.DELETE)
	public JsonApi unbindUser(@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@PathVariable("id") Integer id,
			@Validated(UserItvAccount.Unbind.class) UserItvAccount userItvAccount, BindingResult result) {
		// 设置用户id
		userItvAccount.setId(id);
		// 删除用户
		if (userItvAccountService.delete(userItvAccount) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userItvAccount
	* @param result
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description itv绑定用户列表
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:userItvAccount:users" })
	@RequestMapping(value = { "/user/itv/account/users" }, method = RequestMethod.GET)
	public JsonApi getItvUsers(
			@Validated(UserItvAccount.ItvUsers.class) UserItvAccount userItvAccount, BindingResult result) {
		Page<?> page = PageHelper.startPage(userItvAccount.getPage(), userItvAccount.getPageSize());
		// itv绑定用户列表
		List<Map<String, Object>> usersResultList = userItvAccountService.getList(userItvAccount);
		if (CollectionUtils.isNotEmpty(usersResultList)) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), usersResultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userItvAccount
	* @param result
	* @return 
	* @date 2018年4月10日
	* @version 1.0 
	* @description itv用户登录 获取token
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:userItvAccount:login" })
	@RequestMapping(value = { "/user/itv/account/login" }, method = RequestMethod.GET)
	public JsonApi login(
			@Validated(UserItvAccount.Login.class) UserItvAccount userItvAccount, BindingResult result) {
		// 根据itv账号和用户id查询用户信息
		userItvAccount.setStatus(UserStatusCode.ItvAccountStatus.ENABLE.getValue());
		Map<String, Object> itvUserMap = userItvAccountService.getOne(userItvAccount);
		if (itvUserMap != null && itvUserMap.size() > 0) {
			// 查看缓存是否存在
			String userId = itvUserMap.get("userId").toString();
			AuthenticationSession authenticationSession = cacheManager.getCache(BaseGlobal.CACHE_USER).get(userId, AuthenticationSession.class);
			if (authenticationSession != null) {
				Map<String, Object> resultMap = new HashMap<>();
				@SuppressWarnings("unchecked")
				Map<String,Object> userMapCache = authenticationSession.get(Map.class);
				String tokenCache = authenticationSession.getIdentify();
				resultMap.put("info", userMapCache);
				resultMap.put("token", tokenCache);
				return new JsonApi(ApiCode.OK, resultMap);
			}else {
				// 查询用户详情
				User user=new User();
				user.setId(Integer.parseInt(itvUserMap.get("userId").toString()));
				Map<String, Object> userMap = userService.getLoginMsg(user);
				if (MapUtils.isEmpty(userMap)) {
					return new JsonApi(ApiCode.FAIL);
				}
				String token = MD5Util.getInstance().getSessionToken(itvUserMap.get("userId"));
				// 放入缓存 已实现自动踢出
				cacheManager.AuthenticationToken(new AuthenticationToken(BaseGlobal.CACHE_USER,
						userMap.get("id").toString(), new AuthenticationSession(token, userMap)));
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("info", userMap);
				resultMap.put("token", token);
				// 返回用户信息
				return new JsonApi(ApiCode.OK, resultMap);
			}
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userItvAccount
	* @param result
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description 验证密码
	*/
	@RequiresAuthentication(authc = true, value = { "itv:userItvAccount:validPassword" })
	@RequestMapping(value = { "/user/itv/account/valid/password" }, method = RequestMethod.GET)
	public JsonApi validPassword(@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@Validated(UserItvAccount.ValidPassword.class) UserItvAccount userItvAccount, BindingResult result) {
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		// 用户ID
		userItvAccount.setUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		// 查询详情
		userItvAccount.setId(null);
		userItvAccount.setStatus(UserStatusCode.ItvAccountStatus.ENABLE.getValue());
		Map<String, Object> validResultMap = userItvAccountService.getOne(userItvAccount);
		if (MapUtils.isNotEmpty(validResultMap)) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
}
