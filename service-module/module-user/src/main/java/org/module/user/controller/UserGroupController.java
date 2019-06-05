package org.module.user.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.user.domain.UserGroup;
import org.module.user.global.BaseGlobal;
import org.module.user.message.Prompt;
import org.module.user.service.UserGroupService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserGroupController {

	@Autowired
	private UserGroupService userGroupService;
	@Resource
	private RedisCacheManager cacheManager;

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param token
	 * @param userGroup
	 * @param result
	 * @return
	 * @date 2018年3月28日
	 * @version 1.0
	 * @description 新增用户组
	 */
	@RequiresAuthentication(authc = true, value = { "user:user:group:insert" })
	@RequestMapping(value = { "/user/group" }, method = RequestMethod.POST)
	public JsonApi insertBaseUserGroup(@RequestHeader(value = BaseGlobal.TOKEN_FLAG) String token,
			@Validated({ BaseEntity.Insert.class }) @RequestBody UserGroup userGroup, BindingResult result) {
		// 从缓存中获取用户
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		// 登录用户的id
		Integer userId = (Integer) session.get(Map.class).get("id");
		// 判断当前用户有没有用户组
		userGroup.setUserId(userId);
		Map<String, Object> userGroupRepeatMap = userGroupService.getRepeat(userGroup);
		if (MapUtils.isNotEmpty(userGroupRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.group.is.exist"));
		}
		userGroup.setCreateDate(new Date());
		if (userGroupService.insert(userGroup) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param token
	* @param userGroup
	* @param result
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 查询用户组是否存在
	*/
	@RequiresAuthentication(authc = true, value = { "user:user:group:detail" })
	@RequestMapping(value = { "/user/group" }, method = RequestMethod.GET)
	public JsonApi getUserGroupDetails(@RequestHeader(value = BaseGlobal.TOKEN_FLAG) String token,
			@Validated({ BaseEntity.SelectOne.class }) UserGroup userGroup, BindingResult result) {
		// 从缓存中获取用户
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		// 登录用户的id
		Integer userId = (Integer) session.get(Map.class).get("id");
		userGroup.setUserId(userId);
		Map<String, Object> userGroupRepeatMap = userGroupService.getRepeat(userGroup);
		if (MapUtils.isNotEmpty(userGroupRepeatMap)) {
			return new JsonApi(ApiCode.OK, userGroupRepeatMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
