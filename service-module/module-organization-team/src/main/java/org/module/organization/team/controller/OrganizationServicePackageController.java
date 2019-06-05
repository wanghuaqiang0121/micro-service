package org.module.organization.team.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.team.domain.OrganizationServicePackage;
import org.module.organization.team.service.OrganizationServicePackageService;
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
 * @Date 2018年3月19日
 * @Version
 * @Description 机构服务包
 */
@RestController
public class OrganizationServicePackageController {

	@Autowired
	private OrganizationServicePackageService organizationServicePackageService;
	
	/**
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param id
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 查询机构的服务包详情
	 */
	@RequiresAuthentication(authc = true,value = { "organization:team:organizationServicePackage:detail" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/{id}" }, method = RequestMethod.GET)
	public JsonApi organizationServicePackageDetail(@PathVariable("id") Integer id,
			@Validated(BaseEntity.SelectOne.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result) {
		// 设置id
		organizationServicePackage.setId(id);
		// 查询机构的服务包详情
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getOne(organizationServicePackage);
		if (organizationServicePackageMap!=null && !organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.OK, organizationServicePackageMap);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年3月23日
	 * @version 1.0
	 * @description 机构服务包列表
	 */
	@RequiresAuthentication(authc = true, value = { "organization:team:organizationServicePackage:packages" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/packages" }, method = RequestMethod.GET)
	public JsonApi organizationServicePackages(
			@Validated(OrganizationServicePackage.SelectAll.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result) {
		// 分页设置
		Page<?> page = PageHelper.startPage(organizationServicePackage.getPage(),organizationServicePackage.getPageSize());
		// 查询包列表
		List<Map<String, Object>> organizationServicePackageList = organizationServicePackageService.getList(organizationServicePackage);
		if (organizationServicePackageList!=null && !organizationServicePackageList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationServicePackageList));
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
