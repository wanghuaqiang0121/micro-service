package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationOrganizationTeamRole;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.service.OrganizationOrganizationTeamRoleService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
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
 * @Date 2018年7月2日
 * @Version 
 * @Description 机构团队角色
 */
@RestController
public class OrganizationOrganizationTeamRoleController {

	@Autowired
	private OrganizationOrganizationTeamRoleService organizationOrganizationTeamRoleService ;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationOrganizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月2日
	* @version 1.0
	* @description 机构和团队角色关联列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationOrganizationTeamRole:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/organization/team/role" }, method = RequestMethod.GET)
	public JsonApi getOrganizationOrganizationTeamRoleList(@Validated({ BaseEntity.SelectAll.class }) OrganizationOrganizationTeamRole  organizationOrganizationTeamRole,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId ){
		organizationOrganizationTeamRole.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationOrganizationTeamRole.getPage(), organizationOrganizationTeamRole.getPageSize());
		List<Map<String, Object>>  organizationOrganizationTeamRoleList=organizationOrganizationTeamRoleService.getList(organizationOrganizationTeamRole);
		if (organizationOrganizationTeamRoleList!=null && !organizationOrganizationTeamRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationOrganizationTeamRoleList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
	
}
