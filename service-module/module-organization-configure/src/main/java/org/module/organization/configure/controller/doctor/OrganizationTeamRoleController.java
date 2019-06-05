package org.module.organization.configure.controller.doctor;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.doctor.OrganizationTeamRole;
import org.module.organization.configure.service.doctor.OrganizationTeamRoleService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@RestController
public class OrganizationTeamRoleController {

	@Autowired
	private OrganizationTeamRoleService organizationTeamRoleService;
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationTeamRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月16日
	* @version 1.0
	* @description 团队角色列表
	*/
	@RequiresAuthentication(value = { "organization:configure:organization:team:role:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/team/roles" }, method = RequestMethod.GET)
	public JsonApi getOrganizationTeamRole(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationTeamRole  organizationTeamRole,BindingResult result){
		Page<?> page = PageHelper.startPage(organizationTeamRole.getPage(), organizationTeamRole.getPageSize());
		List<Map<String, Object>>  organizationTeamRoleList=organizationTeamRoleService.getList(organizationTeamRole);
		if (organizationTeamRoleList!=null && !organizationTeamRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationTeamRoleList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
