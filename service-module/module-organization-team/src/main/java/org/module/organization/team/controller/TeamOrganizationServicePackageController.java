package org.module.organization.team.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.team.domain.Organization;
import org.module.organization.team.domain.TeamOrganizationServicePackage;
import org.module.organization.team.global.IServicePackageTypeCode;
import org.module.organization.team.service.TeamOrganizationServicePackageService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月28日
 * @Version 
 * @Description 团队机构服务包
 */
@RestController
public class TeamOrganizationServicePackageController {

	@Autowired
	private TeamOrganizationServicePackageService teamOrganizationServicePackageService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param teamOrganizationServicePackage
	* @param result
	* @param token
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 团队服务包详情
	*/
	@RequiresAuthentication(authc = true, value = { "organization:team:teamOrganizationServicePackage:detail" },level=Level.OPERATION)
	@RequestMapping(value = { "/team/organization/service/package/{id}" }, method = RequestMethod.GET)
	public JsonApi getDetail(@PathVariable("id") Integer id,@Validated({ BaseEntity.SelectOne.class }) TeamOrganizationServicePackage  teamOrganizationServicePackage,BindingResult result){
		/*设置主键*/
		teamOrganizationServicePackage.setId(id);
		// 团队服务包详情
		Map<String, Object> teamPackageMap = teamOrganizationServicePackageService.getOne(teamOrganizationServicePackage);
		if (teamPackageMap!=null && !teamPackageMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,teamPackageMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 团队服务包列表
	*/
	@RequiresAuthentication(authc = true, value = { "organization:team:teamOrganizationServicePackage:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/team/organization/service/packages" }, method = RequestMethod.GET)
	public JsonApi teamOrganizationServicePackages(
			@Validated(BaseEntity.SelectAll.class) TeamOrganizationServicePackage teamOrganizationServicePackage,BindingResult result){
		// 机构经纬度 
		if (teamOrganizationServicePackage.getOrganization() != null) {
			if (teamOrganizationServicePackage.getOrganization().getLat() != null
					&& teamOrganizationServicePackage.getOrganization().getLng() != null
					&& teamOrganizationServicePackage.getOrganization().getMaxRaidus() != null) {
				// 设置经纬度 
				Organization organization = teamOrganizationServicePackage.getOrganization();
				organization.setRectangle(organization.getRectangle());
			}
		}
		// 分页设置
		Page<?> page = PageHelper.startPage(teamOrganizationServicePackage.getPage(), teamOrganizationServicePackage.getPageSize());
		// 查询团队机构服务包列表
		/* 设置查询非基本公共卫生服务包 */
		teamOrganizationServicePackage.setIsBasePublicHealth(false);
		teamOrganizationServicePackage.setServicePackageTypeCode(IServicePackageTypeCode.Type.JGW.getValue());
		List<Map<String, Object>> teamOrganizationServicePackagesList = teamOrganizationServicePackageService.getList(teamOrganizationServicePackage);
		if (teamOrganizationServicePackagesList!=null && !teamOrganizationServicePackagesList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(), teamOrganizationServicePackagesList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
