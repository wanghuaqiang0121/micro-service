package org.module.user.controller.itv;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.user.domain.itv.Organization;
import org.module.user.service.OrganizationService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
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
 * @Date 2018年4月9日
 * @Version 
 * @Description 机构
 */
@RestController
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organization
	* @param result
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 机构详情
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:organization:detail" })
	@RequestMapping(value = { "/itv/organization/{id}" }, method = RequestMethod.GET)
	public JsonApi organizationDetail(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class })  Organization organization,BindingResult result) {
		organization.setId(id);
		Map<String, Object> organizationOneMap = organizationService.getOne(organization);
		if (MapUtils.isNotEmpty(organizationOneMap)) {
			return new JsonApi(ApiCode.OK,organizationOneMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organization
	* @param result
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 查询机构列表
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:organization:list" })
	@RequestMapping(value = { "/itv/organizations" }, method = RequestMethod.GET)
	public JsonApi organizationList(
			@Validated({ BaseEntity.SelectAll.class })  Organization organization,BindingResult result) {
		Page<?> page = PageHelper.startPage(organization.getPage(),organization.getPageSize());
		List<Map<String, Object> > organizationList = organizationService.getList(organization);
		if (organizationList != null && organizationList.size() >0) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
