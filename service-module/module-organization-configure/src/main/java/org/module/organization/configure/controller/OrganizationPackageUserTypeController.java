package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.module.organization.configure.domain.OrganizationPackageUserType;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationPackageUserTypeService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月19日
 * @Version 
 * @Description 服务包使用人群关联表
 */
@RestController
public class OrganizationPackageUserTypeController {

	@Autowired
	private OrganizationPackageUserTypeService organizationPackageUserTypeService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageUserType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 添加服务包使用人群关联
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationPackageUserType:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/package/user/type" }, method = RequestMethod.POST)
	public JsonApi addOrganizationPackageUserType(
			@Validated(BaseEntity.Insert.class) @RequestBody OrganizationPackageUserType organizationPackageUserType,BindingResult result){
		// 查询数据是否存在重复
		Map<String, Object> OrganizationPackageUserTypeMap = organizationPackageUserTypeService.getRepeat(organizationPackageUserType);
		if (OrganizationPackageUserTypeMap!=null && !OrganizationPackageUserTypeMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.package.user.type.is.exists"));
		}
		// 添加服务包使用人群关联
		if (organizationPackageUserTypeService.insert(organizationPackageUserType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organizationPackageUserType
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 批量添加服务包使用人群关联
	 */
	@Transactional
	@RequiresAuthentication( value = { "organization:configure:organizationPackageUserType:batchInsert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/package/user/type/batchInsert" }, method = RequestMethod.POST)
	public JsonApi batchInsertOrganizationPackageUserType(
			@Validated(OrganizationPackageUserType.BatchInsert.class) @RequestBody OrganizationPackageUserType organizationPackageUserType,BindingResult result){
		// 删除服务包所有使用人群关联
		OrganizationPackageUserType oPackageUserType = new OrganizationPackageUserType();
		oPackageUserType.setOrganizationServicePackageId(organizationPackageUserType.getOrganizationPackageUserTypes().get(0).getOrganizationServicePackageId());
		organizationPackageUserTypeService.deleteByOrganizationPackageId(oPackageUserType);
		// 批量添加服务包使用人群关联
		int rows=organizationPackageUserTypeService.batchInsert(organizationPackageUserType.getOrganizationPackageUserTypes());
		if (rows == organizationPackageUserType.getOrganizationPackageUserTypes().size()) {
			return new JsonApi(ApiCode.OK);
		}
		throw new RuntimeException();
	}
	
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationPackageUserType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除服务包使用人群关联
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationPackageUserType:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/package/user/type/{id}" }, method = RequestMethod.DELETE)
	public JsonApi deleteOrganizationPackageUserType(@PathVariable("id") Integer id,
			@Validated(BaseEntity.Delete.class) OrganizationPackageUserType organizationPackageUserType,BindingResult result){
		// 设置id
		organizationPackageUserType.setId(id);
		// 删除服务包使用人群关联
		if (organizationPackageUserTypeService.delete(organizationPackageUserType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageUserType
	* @param result
	* @return 
	* @date 2018年3月26日
	* @version 1.0
	* @description 查询服务包的人群类型列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationPackageUserType:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/package/user/types" }, method = RequestMethod.GET)
	public JsonApi getList(@RequestBody @Validated({BaseEntity.SelectAll.class }) OrganizationPackageUserType organizationPackageUserType,
			BindingResult result) {
		/* 设置分页条件 */
		Page<?> page = PageHelper.startPage(organizationPackageUserType.getPage(),organizationPackageUserType.getPageSize());
		/* 查询列表 */
		List<Map<String, Object>> resultList = organizationPackageUserTypeService.getList(organizationPackageUserType);
		if (CollectionUtils.isNotEmpty(resultList)) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(), resultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
}
