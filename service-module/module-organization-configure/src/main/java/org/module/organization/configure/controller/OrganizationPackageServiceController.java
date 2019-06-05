package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationPackageService;
import org.module.organization.configure.domain.ServiceType;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationPackageServiceService;
import org.module.organization.configure.service.ServiceTypeService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月19日
 * @Version 
 * @Description 机构服务包和服务关联表
 */
@RestController
public class OrganizationPackageServiceController {

	@Autowired
	private OrganizationPackageServiceService organizationPackageServiceService;
	@Autowired
	private ServiceTypeService serviceTypeService;
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageService
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 添加机构服务包和服务关联
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationPackageService:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/package/service" }, method = RequestMethod.POST)
	public JsonApi addOrganizationPackageService(
			@Validated(BaseEntity.Insert.class) @RequestBody OrganizationPackageService organizationPackageService,BindingResult result){
		// 查询数据是否存在重复
		/*Map<String, Object> organizationPackageServiceMap = organizationPackageServiceService.getRepeat(organizationPackageService);
		if (organizationPackageServiceMap!=null && !organizationPackageServiceMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.package.service.is.exists"));
		}*/
		ServiceType serviceType =new ServiceType();
		serviceType.setId(organizationPackageService.getServiceTypeId());
		Map<String, Object> serviceTypeMap = serviceTypeService.getOne(serviceType );
		if (serviceTypeMap == null || serviceTypeMap.isEmpty()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("service.type.is.null"));
		}
		if ("".equals(organizationPackageService.getName()) && serviceTypeMap.get("name") != null) {
			organizationPackageService.setName(serviceTypeMap.get("name").toString());
		}
		// 添加机构服务包和服务关联
		if (organizationPackageServiceService.insert(organizationPackageService) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageService
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月19日
	* @version 1.0
	* @description 批量添加机构服务包和服务关联
	*/
	@Transactional
	@RequiresAuthentication( value = { "organization:configure:organizationPackageService:batchInsert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/package/service/batchInsert" }, method = RequestMethod.POST)
	public JsonApi batchInsert(
			@Validated(OrganizationPackageService.BatchInsert.class) @RequestBody OrganizationPackageService organizationPackageService,BindingResult result){
		// 批量新增服务包内服务项
		int rows=organizationPackageServiceService.batchInsert(organizationPackageService.getOrganizationPackageServices());
		if (rows == organizationPackageService.getOrganizationPackageServices().size()) {
			return new JsonApi(ApiCode.OK);
		}else if (rows != organizationPackageService.getOrganizationPackageServices().size()) {
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	
	}
	
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationPackageService
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月16日
	* @version 1.0
	* @description 机构服务包没有添加的服务
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationPackageService:isNull:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/package/is/null/service" }, method = RequestMethod.GET)
	public JsonApi getOrganizationPackageIsNullService(
			@Validated(BaseEntity.SelectAll.class)  OrganizationPackageService organizationPackageService,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		organizationPackageService.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationPackageService.getPage(), organizationPackageService.getPageSize());
		List<Map<String, Object>>  organizationPackageServiceList=organizationPackageServiceService.getOrganizationPackageIsNullService(organizationPackageService);
		if (organizationPackageServiceList!=null && !organizationPackageServiceList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationPackageServiceList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationPackageService
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除服务包服务关联(单条)
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationPackageService:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/package/Service/{id}" }, method = RequestMethod.DELETE)
	public JsonApi deleteOrganizationPackageService(@PathVariable("id") Integer id,
			@Validated(BaseEntity.Delete.class) OrganizationPackageService organizationPackageService,BindingResult result){
		// 设置id
		organizationPackageService.setId(id);
		// 删除服务包服务关联(单条)
		if (organizationPackageServiceService.delete(organizationPackageService) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationPackageService
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 修改服务包服务关联
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationPackageService:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/package/service/{id}" }, method = RequestMethod.PUT)
	public JsonApi updateOrganizationPackageService(@PathVariable("id") Integer id,
			@Validated(BaseEntity.Update.class) @RequestBody OrganizationPackageService organizationPackageService,BindingResult result){
		// 设置id
		organizationPackageService.setId(id);
		// 查看是否存在
		Map<String, Object> organizationPackageServiceMap = organizationPackageServiceService.getOne(organizationPackageService);
		if (organizationPackageServiceMap==null || organizationPackageServiceMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if ("".equals(organizationPackageService.getName()) && organizationPackageServiceMap.get("name") != null) {
			organizationPackageService.setName(organizationPackageServiceMap.get("name").toString());
		}
		// 修改服务包服务关联
		if (organizationPackageServiceService.update(organizationPackageService) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
