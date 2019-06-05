package org.module.organization.team.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.team.domain.Organization;
import org.module.organization.team.service.OrganizationService;
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
 * @Date 2018年3月15日
 * @Version 
 * @Description 机构
 */
@RestController
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organization
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 查询机构列表
	*/
	@RequiresAuthentication(authc = true,value = { "organization:team:organization:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organizations" }, method = RequestMethod.GET)
	public JsonApi organizationList(
			@Validated({ BaseEntity.SelectAll.class })  Organization organization,BindingResult result) {
		if (organization.getLat() != null
				&& organization.getLng() != null
				&& organization.getMaxRaidus() != null) {
			organization.setRectangle(organization.getRectangle());
		}
		Page<?> page = PageHelper.startPage(organization.getPage(),organization.getPageSize());
		List<Map<String, Object> > organizationList = organizationService.getList(organization);
		if (organizationList != null && !organizationList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
