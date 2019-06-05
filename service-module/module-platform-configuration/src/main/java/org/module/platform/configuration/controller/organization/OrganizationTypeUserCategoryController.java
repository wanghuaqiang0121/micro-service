package org.module.platform.configuration.controller.organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.organization.OrganizationTypeUserCategory;
import org.module.platform.configuration.global.BaseGlobal;
import org.module.platform.configuration.service.organization.OrganizationTypeUserCategoryService;
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

@RestController
public class OrganizationTypeUserCategoryController {

	
	@Autowired
	private OrganizationTypeUserCategoryService organizationTypeUserCategoryService;
	
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param organizationPersonType
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年6月6日
	 * @version 1.0
	 * @description 获取机构类型用户类型关联列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:type:user:category:list" })
	@RequestMapping(value = {"/organization/type/user/category"}, method = RequestMethod.GET)
	public JsonApi getOrganizationTypeUserCategoryList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationTypeUserCategory organizationTypeUserCategory, BindingResult result){
		Page<?> page = PageHelper.startPage(organizationTypeUserCategory.getPage(), organizationTypeUserCategory.getPageSize());
		List<Map<String, Object>> organizationTypeUserCategoryList = organizationTypeUserCategoryService.getOrganizationTypeUserCategoryList(organizationTypeUserCategory);
		if (organizationTypeUserCategoryList!=null && !organizationTypeUserCategoryList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationTypeUserCategoryList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param organizationPersonType
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年6月6日
	 * @version 1.0
	 * @description 新增机构类型用户类型关联
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:type:user:category:insert" })
	@RequestMapping(value = {"/organization/type/user/category"}, method = RequestMethod.POST)
	public JsonApi insertOrganizationTypeUserCategory(
			@RequestBody @Validated({ BaseEntity.Insert.class }) OrganizationTypeUserCategory organizationTypeUserCategory, BindingResult result){
		Map<String, Object> categoryMap = organizationTypeUserCategoryService.getRepeat(organizationTypeUserCategory);
		if(MapUtils.isNotEmpty(categoryMap)){
			return new JsonApi(ApiCode.CONFLICT);
		}
		if (organizationTypeUserCategoryService.insertOrganizationTypeUserCategory(organizationTypeUserCategory) > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", organizationTypeUserCategory.getId());
			return new JsonApi(ApiCode.OK, map);	
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param id
	 * @param organizationPersonType
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年6月6日
	 * @version 1.0
	 * @description 删除机构类型用户类型关联
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:type:user:category:delete" })
	@RequestMapping(value = {"/organization/type/user/category/{id}"}, method = RequestMethod.DELETE)
	public JsonApi deleteOrganizationTypeUserCategory(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Delete.class }) OrganizationTypeUserCategory organizationTypeUserCategory, BindingResult result, 
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId){
		organizationTypeUserCategory.setId(id);
		if (organizationTypeUserCategoryService.deleteOrganizationTypeUserCategory(organizationTypeUserCategory) > 0) {
			return new JsonApi(ApiCode.OK);	
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
