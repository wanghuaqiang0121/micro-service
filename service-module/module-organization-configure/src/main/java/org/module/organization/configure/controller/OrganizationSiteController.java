package org.module.organization.configure.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.Organization;
import org.module.organization.configure.domain.OrganizationSite;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationService;
import org.module.organization.configure.service.OrganizationSiteService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月20日
 * @Version 
 * @Description 机构站点
 */
@RestController
public class OrganizationSiteController {

	@Autowired
	private OrganizationSiteService organizationSiteService;
	@Autowired
	private OrganizationService organizationService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationSite
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 新增机构站点
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationSite:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/site" }, method = RequestMethod.POST)
	public JsonApi organizationServiceSiteInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationSite organizationSite, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		organizationSite.setCreateDate(new Date());
		organizationSite.setOrganizationId(organizationId);
		//判断数据是否重复
		Map<String, Object> organizationSiteMap = organizationSiteService.getRepeat(organizationSite);
		if (organizationSiteMap!=null && !organizationSiteMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.site.name.is.exists"));
		}
		//判断机构是否存在
		Organization organization = new Organization();
		organization.setId(organizationId);
		Map<String, Object> organizationMap = organizationService.getOne(organization);
		if (organizationMap==null || organizationMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("organization.is.null"));
		}
		//新增
		if (organizationSiteService.insert(organizationSite) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param organizationSite
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 查询机构站点详情
	*/
	@RequiresAuthentication(value = { "organization:configure:organizationSite:detail" },level = Level.OPERATION)
	@RequestMapping(value = { "/organization/site/{id}" }, method = RequestMethod.GET)
	public JsonApi organizationServiceSiteOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OrganizationSite organizationSite, BindingResult result) {
		organizationSite.setId(id);
		Map<String, Object> organizationSiteMap = organizationSiteService.getOne(organizationSite);
		if (organizationSiteMap!=null && !organizationSiteMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,organizationSiteMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationSite
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 查询机构站点列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationSite:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/site" }, method = RequestMethod.GET)
	public JsonApi organizationServiceSiteList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationSite organizationSite, BindingResult result) {
		
		Page<?> page = PageHelper.startPage(organizationSite.getPage(), organizationSite.getPageSize());
		List<Map<String, Object>> organizationSiteList = organizationSiteService.getList(organizationSite);
		if (organizationSiteList !=null && !organizationSiteList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationSiteList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param organizationSite
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 修改站点
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationSite:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/site/{id}" }, method = RequestMethod.PUT)
	public JsonApi organizationServiceSiteUpdate(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OrganizationSite organizationSite, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		// 修改的数据是否存在
		organizationSite.setId(id);
		Map<String, Object> organizationSiteMap = organizationSiteService.getOne(organizationSite);
		if (organizationSiteMap ==null || organizationSiteMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		//名称是否重复
		if (null != organizationSite.getName()) {
			organizationSite.setOrganizationId(organizationId);
			Map<String, Object> organizationSiteRepeatMap = organizationSiteService.getRepeat(organizationSite);
			if (organizationSiteRepeatMap!=null && !organizationSiteRepeatMap.isEmpty()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.site.name.is.conflct"));
			}
		}
		//修改
		if (organizationSiteService.update(organizationSite) >0 ) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
