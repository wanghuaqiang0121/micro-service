package org.module.user.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.user.domain.UserGroup;
import org.module.user.domain.UserUserGroup;
import org.module.user.global.BaseGlobal;
import org.module.user.global.UserStatusCode;
import org.module.user.service.UserGroupService;
import org.module.user.service.UserUserGroupService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月15日
 * @Version 
 * @Description 用户用户组关联
 */
@RestController
public class UserUserGroupController {

	@Autowired
	private UserUserGroupService userUserGroupService;
	@Autowired
	private UserGroupService userGroupService;
	@Resource
	private RedisCacheManager cacheManager;

	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param token
	* @param userUserGroup
	* @param result
	* @return 
	* @date 2018年3月27日
	* @version 1.0
	* @description 用户组管理者的成员列表
	*/
	@RequiresAuthentication(authc = true, value = { "user:group:users" })
	@RequestMapping(value = { "/user/group/users" }, method = RequestMethod.GET)
	public JsonApi getGroupUserList(@RequestHeader(value = BaseGlobal.TOKEN_FLAG) String token,
			@Validated(UserUserGroup.UsersByGroup.class) UserUserGroup userUserGroup, BindingResult result) {
		// 从缓存中获取用户
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		// 登录用户的id
		Integer operationUserIdId = (Integer) session.get(Map.class).get("id");
		// 查询用户组是否存在
		// 查询用户组是否存在
		UserUserGroup userGroup = new UserUserGroup();
		userGroup.setUserId(operationUserIdId);
		Map<String, Object> userGrouprepeatMap = userUserGroupService.getUsers(userGroup);
		if (MapUtils.isEmpty(userGrouprepeatMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		Integer userGroupId = (Integer) userGrouprepeatMap.get("id");
		userUserGroup.setUserId(null);
		userUserGroup.setUserGroupId(userGroupId);
		Integer[] trusts = { UserStatusCode.UserUserGroup.TRUSTEESHIP.getValue(),
				UserStatusCode.UserUserGroup.UNTRUSTEESHIP.getValue() };
		userUserGroup.setTrusts(trusts);
		Page<?> page = PageHelper.startPage(userUserGroup.getPage(), userUserGroup.getPageSize());
		List<Map<String, Object>> usersByGroupList = userUserGroupService.getList(userUserGroup);
		if (usersByGroupList != null && !usersByGroupList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), usersByGroupList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param userUserGroup
	* @param result
	* @return 
	* @date 2018年3月27日
	* @version 1.0
	* @description 移除成员
	*/
	@RequiresAuthentication(authc = true,value={"user:user:user:group:delete"})
	@RequestMapping(value = { "/user/user/group/{id}" }, method = RequestMethod.DELETE)
	public JsonApi deleteGroupMember(@PathVariable("id") Integer id,
			@Validated(BaseEntity.Delete.class) UserUserGroup userUserGroup, 
			BindingResult result) {
		userUserGroup.setId(id);
		if (userUserGroupService.delete(userUserGroup) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param token
	 * @param userUserGroup
	 * @param result
	 * @return
	 * @date 2018年12月26日
	 * @version 1.0
	 * @description 用户成员小于18岁的
	 */
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/user/group/member" }, method = RequestMethod.GET)
	public JsonApi getUserMember(@RequestHeader(value = BaseGlobal.TOKEN_FLAG) String token,
			@Validated(UserUserGroup.UsersByGroup.class) UserUserGroup userUserGroup, BindingResult result) 
		 {
			// 从缓存中获取用户
			AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
				// 登录用户的id
				Integer operationUserIdId = (Integer) session.get(Map.class).get("id");
				// 查询用户组是否存在
				UserUserGroup userGroup = new UserUserGroup();
				userGroup.setUserId(operationUserIdId);
				Map<String, Object> userGrouprepeatMap = userUserGroupService.getUsers(userGroup);
				if (MapUtils.isEmpty(userGrouprepeatMap)) {
					return new JsonApi(ApiCode.NOT_FOUND);
				}
				Integer userGroupId = (Integer) userGrouprepeatMap.get("id");
				userUserGroup.setUserId(null);
				userUserGroup.setUserGroupId(userGroupId);
				Integer[] trusts = { UserStatusCode.UserUserGroup.TRUSTEESHIP.getValue(),
						UserStatusCode.UserUserGroup.UNTRUSTEESHIP.getValue() };
				userUserGroup.setTrusts(trusts);
				Page<?> page = PageHelper.startPage(userUserGroup.getPage(), userUserGroup.getPageSize());
				List<Map<String, Object>> usersByGroupList = userUserGroupService.getUserMember(userUserGroup);
				if (usersByGroupList != null && !usersByGroupList.isEmpty()) {
					return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), usersByGroupList));
				}
				return new JsonApi(ApiCode.NOT_FOUND);
	}
}
