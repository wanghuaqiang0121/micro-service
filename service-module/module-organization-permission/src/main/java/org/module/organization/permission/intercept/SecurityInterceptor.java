package org.module.organization.permission.intercept;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.module.organization.permission.global.BaseGlobal;
import org.module.organization.permission.intercept.permission.IOrganizationUserRoleService;
import org.module.organization.permission.message.Prompt;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年7月4日
 * @version 1.0
 * @description 安全拦截器，用于校验该请求携带的令牌是否能访问系统某方法
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
	@Resource
	private RedisCacheManager cacheManager;
	@Autowired
	private IOrganizationUserRoleService organizationUserRoleService;
	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param list
	 * @param auths
	 * @return {@link Boolean}
	 * @date 2017年7月4日
	 * @version 1.0
	 * @description 权限校验方法
	 */
	private boolean validate(List<Map<String, Object>> list, String[] auths) {
		if (list != null && list.size() > 0) {
			for (String auth : auths) {
				for (Map<String, Object> map : list) {
					if (map != null && map.size() > 0) {
						if (auth.equals(map.get("code"))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			/* 设置响应头为Json */
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			/* 获取token */
			String token = request.getHeader(BaseGlobal.TOKEN_FLAG);
			/*获取机构ID*/
			String organizationId = request.getHeader(BaseGlobal.ORGANIZATION_ID);
			/*获取模块ID*/
			String moduleId = request.getHeader(BaseGlobal.MODULE_ID);
			/* 获取方法 */
			HandlerMethod method = (HandlerMethod) handler;
			/* 获取权限注解 */
			RequiresAuthentication authentication = method.getMethodAnnotation(RequiresAuthentication.class);
			if (authentication == null) {
				/* 如果权限注解为空不允许访问 */
				if (logger.isInfoEnabled()) {
					logger.info("the target method [{}] is not defined",
							((HandlerMethod) handler).getMethod().getName());
				}
				response.getWriter().write(JSON.toJSONString(new JsonApi(ApiCode.UNIMPLEMENTED)));
				return false;
			}
			
			/* 权限注解不为空 则进行鉴权判断 */
			if (authentication.ignore()) {
				/* 如果设置忽略校验直接放行 */
				return true;
			} else if (authentication.authc()) {
				if (token == null) {
					response.getWriter().write(JSON.toJSONString(new JsonApi(ApiCode.BAD_REQUEST)));
					return false;
				}
				AuthenticationSession session = cacheManager
						.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
				/* 如果设置只做登录鉴权则判断是否登录，登录则放行 */
				if (session != null) {
					return true;
				}
				if (logger.isInfoEnabled()) {
					logger.info("the access target method [{}] is not logged in",
							((HandlerMethod) handler).getMethod().getName());
				}
				response.getWriter().write(JSON.toJSONString(new JsonApi(ApiCode.UNAUTHORIZED)));
				return false;
			}else {
				AuthenticationSession session = cacheManager
						.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
				if (session == null) {
					response.getWriter().write(JSON.toJSONString(new JsonApi(ApiCode.UNAUTHORIZED)));
					return false;
				}
				/* 这里进行具体的业务权限判断 */
				switch (authentication.level()) {
				/* 角色判断 */
				case ROLE:
					/* 获取角色列表 */
					JsonApi organizationUserRoleRolesList = organizationUserRoleService.getOrganizationUserRoleRolesList(1,1000, token, Integer.valueOf(organizationId),Integer.valueOf(moduleId) );
					if (organizationUserRoleRolesList.getCode() != ApiCode.OK.getValue()) {
						/* 发生错误返回错误消息 */
						response.getWriter().write(JSON.toJSONString(new JsonApi(ApiCode.FORBIDDEN).setMsg(Prompt.bundle("organization.user.forbidden"))));
						return false;
					}
					Map<String, Object> roleData = (Map<String, Object>) organizationUserRoleRolesList.getData();
					List<Map<String, Object>> roleList =(List<Map<String, Object>>) roleData.get("rows");
					if (validate(roleList, authentication.value())) {
						return true;
					}
					break;
				case PERMISSION:
					/* 获取权限列表 */
					JsonApi organizationUserRolePermissionList = organizationUserRoleService.getOrganizationUserRolepermissionsList(1,1000, token, Integer.valueOf(organizationId),Integer.valueOf(moduleId) );
					if (organizationUserRolePermissionList.getCode() != ApiCode.OK.getValue()) {
						/* 发生错误返回错误消息 */
						response.getWriter().write(JSON.toJSONString(new JsonApi(ApiCode.FORBIDDEN).setMsg(Prompt.bundle("organization.user.forbidden"))));
						return false;
					}
					Map<String, Object> permissionMap = (Map<String, Object>) organizationUserRolePermissionList.getData();
					List<Map<String, Object>> permissionList = (List<Map<String, Object>>)permissionMap.get("rows");
					if (validate(permissionList, authentication.value())) {
						return true;
					}
					break;
				case OPERATION:
					/* 获取操作列表 */
					JsonApi organizationUserRoleOperationList = organizationUserRoleService.getOrganizationUserRoleOperationsList(1,1000, token, Integer.valueOf(organizationId),Integer.valueOf(moduleId) );
					if (organizationUserRoleOperationList.getCode() != ApiCode.OK.getValue()) {
						/* 发生错误返回错误消息 */
						response.getWriter().write(JSON.toJSONString(new JsonApi(ApiCode.FORBIDDEN).setMsg(Prompt.bundle("organization.user.forbidden"))));
						return false;
					}
					Map<String, Object> operationData = (Map<String, Object>) organizationUserRoleOperationList.getData();
					List<Map<String, Object>> operationList = (List<Map<String, Object>>) operationData.get("rows");
					if (validate(operationList, authentication.value())) {
						return true;
					}
					break;
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("access target method [{}] has no permissions",
						((HandlerMethod) handler).getMethod().getName());
			}
			response.getWriter().write(JSON.toJSONString(new JsonApi(ApiCode.FORBIDDEN).setMsg(Prompt.bundle("organization.user.forbidden"))));
			return false;
		}
		return true;
	}
}