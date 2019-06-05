package org.module.organization.team.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.team.domain.OrganizationSite;
import org.module.organization.team.service.OrganizationSiteService;
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


/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年4月2日
 * @Version 
 * @Description 机构站点
 */
@RestController
public class OrganizationSiteController {

	@Autowired
	private OrganizationSiteService organizationSiteService;

	

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationSite
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月2日
	* @version 1.0
	* @description 站点列表
	*/
	@RequiresAuthentication(authc = true, value = { "organization:team:organizationSite:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/sites" }, method = RequestMethod.GET)
	public JsonApi organizationServiceSiteList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationSite organizationSite, BindingResult result) {
		if (organizationSite.getLat() != null
				&& organizationSite.getLng() != null
				&& organizationSite.getMaxRaidus() != null) {
			organizationSite.setRectangle(organizationSite.getRectangle());
		}
		Page<?> page = PageHelper.startPage(organizationSite.getPage(), organizationSite.getPageSize());
		List<Map<String, Object>> organizationSiteList = organizationSiteService.getList(organizationSite);
		if (organizationSiteList !=null && !organizationSiteList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationSiteList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
}
