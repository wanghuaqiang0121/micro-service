package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationPersonType;
import org.module.organization.configure.service.OrganizationPersonTypeService;
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
public class OrganizationPersonTypeController {

	@Autowired
	private OrganizationPersonTypeService organizationPersonTypeService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationPersonType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月8日
	* @version 1.0
	* @description 机构用户类型列表
	*/
	@RequiresAuthentication(ignore = true, value = { "organization:configure:organizationPersonType:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/person/types" }, method = RequestMethod.GET)
	public JsonApi getOrganizationPersonTypeList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationPersonType organizationPersonType,BindingResult result ){
		Page<?> page = PageHelper.startPage(organizationPersonType.getPage(), organizationPersonType.getPageSize());
		List<Map<String, Object>>  organizationPersonTypeList=organizationPersonTypeService.getList(organizationPersonType);
		if (organizationPersonTypeList!=null && !organizationPersonTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationPersonTypeList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
