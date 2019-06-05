package org.module.user.controller.itv;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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

@RestController
public class itvUserUserGroupController {
	
	@Autowired
	private UserUserGroupService userUserGroupService;
	@Autowired
	private UserGroupService userGroupService;
	@Resource
	private RedisCacheManager cacheManager;
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userUserGroup
	* @param result
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 管理者的成员列表
	*/
	@RequiresAuthentication(authc = true, value = { "itv:user:group:users" })
	@RequestMapping(value = { "/itv/user/group/users" }, method = RequestMethod.GET)
	public JsonApi getGroupUserList(@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@Validated(UserUserGroup.ItvUsersByGroup.class) UserUserGroup userUserGroup, BindingResult result) {
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		int userId=Integer.parseInt(session.get(Map.class).get("id").toString());
		// 用户ID
		userUserGroup.setUserId(userId);
		// 查询用户组是否存在
		UserGroup userGroup = new UserGroup();
		userGroup.setUserId(userUserGroup.getUserId());
		Map<String, Object> userGrouprepeatRestltMap = userGroupService.getRepeat(userGroup);
		if (MapUtils.isEmpty(userGrouprepeatRestltMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		Integer userGroupId = (Integer) userGrouprepeatRestltMap.get("id");
		userUserGroup.setUserId(null);
		userUserGroup.setUserGroupId(userGroupId);
		Integer[] trusts = { UserStatusCode.UserUserGroup.TRUSTEESHIP.getValue(),
				UserStatusCode.UserUserGroup.UNTRUSTEESHIP.getValue() };
		userUserGroup.setTrusts(trusts);
		Page<?> page = PageHelper.startPage(userUserGroup.getPage(), userUserGroup.getPageSize());
		// 用户组成员列表
		List<Map<String, Object>> usersByGroupResultList = userUserGroupService.getList(userUserGroup);
		if (CollectionUtils.isNotEmpty(usersByGroupResultList)) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), usersByGroupResultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
