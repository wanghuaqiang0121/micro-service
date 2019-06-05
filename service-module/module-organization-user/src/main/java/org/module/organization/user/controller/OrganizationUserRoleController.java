package org.module.organization.user.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.organization.user.domain.OrganizationUserRole;
import org.module.organization.user.global.BaseGlobal;
import org.module.organization.user.service.OrganizationUserRoleService;
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
 * @Date 2018年4月2日
 * @Version
 * @Description 机构角色
 */
@RestController
public class OrganizationUserRoleController {

	@Autowired
	private OrganizationUserRoleService organizationUserRoleService;
	@Resource
	private RedisCacheManager cacheManager;

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param organizationUserRole
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年4月2日
	 * @version 1.0
	 * @description 查询用户机构列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:organization:user:role:organiztions" })
	@RequestMapping(value = { "/organizationUser/role/organizations" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserRoleOrganiztionsList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationUserRole organizationUserRole, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		// 获取缓存信息
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		organizationUserRole.setOrganizationUserId(Integer.parseInt(session.get(Map.class).get("id").toString()));
		Page<?> page = PageHelper.startPage(organizationUserRole.getPage(), organizationUserRole.getPageSize());
		List<Map<String, Object>> organizationUserRoleOrganiztionsList = organizationUserRoleService.getList(organizationUserRole);
		if (organizationUserRoleOrganiztionsList != null && !organizationUserRoleOrganiztionsList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserRoleOrganiztionsList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

}
