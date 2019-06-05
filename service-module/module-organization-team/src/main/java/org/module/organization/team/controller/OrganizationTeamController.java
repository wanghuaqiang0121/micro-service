package org.module.organization.team.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.team.domain.Organization;
import org.module.organization.team.domain.OrganizationTeam;
import org.module.organization.team.global.BaseGlobal;
import org.module.organization.team.service.OrganizationTeamService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
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

@RestController
public class OrganizationTeamController {

	@Autowired
	private OrganizationTeamService organizationTeamService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationTeam
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 机构团队详情
	*/
	@RequiresAuthentication(authc = true, value = { "organization:team:organizationTeam:detail" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/team/{id}" }, method = RequestMethod.GET)
	public JsonApi getDetail(@PathVariable("id") Integer id,@Validated({ BaseEntity.SelectOne.class }) OrganizationTeam  organizationTeam,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token){
		/*设置主键*/
		organizationTeam.setId(id);
		Map<String, Object> organizationTeamMap = organizationTeamService.getOne(organizationTeam);
		if (organizationTeamMap!=null && !organizationTeamMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,organizationTeamMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationTeam
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 查询机构团队列表（内嵌服务包列表）
	*/
	@RequiresAuthentication(authc = true, value = { "organization:team:organizationTeam:list" })
	@RequestMapping(value = { "/organization/teams" }, method = RequestMethod.GET)
	public JsonApi getList(@Validated({ BaseEntity.SelectAll.class }) OrganizationTeam  organizationTeam,BindingResult result){
		// 机构经纬度 
		if (organizationTeam.getOrganization() != null) {
			if (organizationTeam.getOrganization().getLat() != null
					&& organizationTeam.getOrganization().getLng() != null
					&& organizationTeam.getOrganization().getMaxRaidus() != null) {
				// 设置经纬度 
				Organization organization = organizationTeam.getOrganization();
				organization.setRectangle(organization.getRectangle());
			}
		}
		Page<?> page = PageHelper.startPage(organizationTeam.getPage(), organizationTeam.getPageSize());
		List<Map<String, Object>>  organizationTeamList=organizationTeamService.getList(organizationTeam);
		if (organizationTeamList!=null && !organizationTeamList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationTeamList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
