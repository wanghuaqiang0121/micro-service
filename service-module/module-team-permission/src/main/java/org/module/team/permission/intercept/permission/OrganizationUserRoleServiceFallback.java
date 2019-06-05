package org.module.team.permission.intercept.permission;

import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.springframework.stereotype.Component;

@Component
public class OrganizationUserRoleServiceFallback implements IOrganizationUserRoleService {

	@Override
	public JsonApi getOrganizationUserRoleRolesList(Integer page, Integer pageSize, String token,
			Integer organizationId, Integer moduleId) {
		return new JsonApi(ApiCode.TIMEOUT);
	}

	@Override
	public JsonApi getOrganizationUserRolepermissionsList(Integer page, Integer pageSize, String token,
			Integer organizationId, Integer moduleId) {
		return new JsonApi(ApiCode.TIMEOUT);
	}

	@Override
	public JsonApi getOrganizationUserRoleOperationsList(Integer page, Integer pageSize, String token,
			Integer organizationId, Integer moduleId) {
		return new JsonApi(ApiCode.TIMEOUT);
	}

	@Override
	public JsonApi getOrganizationUserRoleMenusList(Integer page, Integer pageSize, String token,
			Integer organizationId, Integer moduleId) {
		return new JsonApi(ApiCode.TIMEOUT);
	}

}
