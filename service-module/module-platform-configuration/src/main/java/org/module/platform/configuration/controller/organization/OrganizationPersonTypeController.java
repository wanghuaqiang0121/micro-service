package org.module.platform.configuration.controller.organization;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.module.platform.configuration.domain.organization.OrganizationPersonType;
import org.module.platform.configuration.service.organization.OrganizationPersonTypeService;
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
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param organizationPersonType
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年6月6日
	 * @version 1.0
	 * @description 查询机构人员类型列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:person:type:list" })
	@RequestMapping(value = {"/organization/person/type"}, method = RequestMethod.GET)
	public JsonApi getOrganizationPersonTypeList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationPersonType organizationPersonType, BindingResult result){
		Page<?> page = PageHelper.startPage(organizationPersonType.getPage(), organizationPersonType.getPageSize());
		List<Map<String, Object>> organizationPersonTypeList = organizationPersonTypeService.getOrganizationPersonTypeList(organizationPersonType);
		if (organizationPersonTypeList!=null && !organizationPersonTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationPersonTypeList));	
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
	 * @description 新增机构人员类型
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:person:type:insert" })
	@RequestMapping(value = {"/organization/person/type"}, method = RequestMethod.POST)
	public JsonApi insertOrganizationPersonType(
			@RequestBody @Validated({ BaseEntity.Insert.class })OrganizationPersonType organizationPersonType, BindingResult result){
		organizationPersonType.setCreateDate(new Date());
		Map<String, Object> personTypeMap = organizationPersonTypeService.getRepeat(organizationPersonType);
		if(MapUtils.isNotEmpty(personTypeMap)){
			return new JsonApi(ApiCode.CONFLICT);
		}
		if (organizationPersonTypeService.insertOrganizationPersonType(organizationPersonType) > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", organizationPersonType.getId());
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
	 * @description 修改机构人员类型
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:person:type:update" })
	@RequestMapping(value = {"/organization/person/type/{id}"}, method = RequestMethod.PUT)
	public JsonApi updateOrganizationPersonType(@PathVariable("id")Integer id,
			@RequestBody @Validated({ BaseEntity.Update.class }) OrganizationPersonType organizationPersonType, BindingResult result){
		if(StringUtils.isNotBlank(organizationPersonType.getRole())){
			Map<String, Object> personTypeMap = organizationPersonTypeService.getRepeat(organizationPersonType);
			if(MapUtils.isNotEmpty(personTypeMap)){
				return new JsonApi(ApiCode.CONFLICT);
			}
		}
		organizationPersonType.setId(id);
		if (organizationPersonTypeService.updateOrganizationPersonType(organizationPersonType) > 0) {
			return new JsonApi(ApiCode.OK);	
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
	 * @description 删除机构人员类型
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:person:type:delete" })
	@RequestMapping(value = {"/organization/person/type/{id}"}, method = RequestMethod.DELETE)
	public JsonApi deleteOrganizationPersonType(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Delete.class }) OrganizationPersonType organizationPersonType, BindingResult result){
		organizationPersonType.setId(id);
		if (organizationPersonTypeService.deleteOrganizationPersonType(organizationPersonType) > 0) {
			return new JsonApi(ApiCode.OK);	
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
