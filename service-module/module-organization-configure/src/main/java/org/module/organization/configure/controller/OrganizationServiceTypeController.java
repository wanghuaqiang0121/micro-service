package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.Organization;
import org.module.organization.configure.domain.OrganizationServiceType;
import org.module.organization.configure.domain.ServiceType;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationService;
import org.module.organization.configure.service.OrganizationServiceTypeService;
import org.module.organization.configure.service.ServiceTypeService;
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
 * @Date 2018年3月16日
 * @Version 
 * @Description 机构和服务类型关联
 */
@RestController
public class OrganizationServiceTypeController {

	@Autowired
	private OrganizationServiceTypeService organizationServiceTypeService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ServiceTypeService serviceTypeService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationServiceType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月16日
	* @version 1.0
	* @description 机构服务类型列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationServiceType:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/type" }, method = RequestMethod.GET)
	public JsonApi organizationServiceTypeList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationServiceType organizationServiceType, BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationServiceType.getPage(), organizationServiceType.getPageSize());
		List<Map<String, Object>> organizationServiceTypeList = organizationServiceTypeService.getList(organizationServiceType);
		if (organizationServiceTypeList != null && !organizationServiceTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationServiceTypeList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationServiceType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月17日
	* @version 1.0
	* @description 本机构没有的服务类型
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationServiceType:isNull:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/is/null/service/type/{organizationid}" }, method = RequestMethod.GET)
	public JsonApi getOrganizationIsNullServiceTypeList(
			@PathVariable("organizationid")Integer organizationid,
			@Validated({ BaseEntity.SelectAll.class }) OrganizationServiceType organizationServiceType, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		organizationServiceType.setOrganizationId(organizationid);
		Page<?> page = PageHelper.startPage(organizationServiceType.getPage(), organizationServiceType.getPageSize());
		List<Map<String, Object>> organizationServiceTypeList = organizationServiceTypeService.getOrganizationIsNullServiceTypeList(organizationServiceType);
		if (organizationServiceTypeList != null && !organizationServiceTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationServiceTypeList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationid
	* @param serviceTypeId
	* @param organizationServiceType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除机构服务类型关联
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationServiceType:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/{organizationid}/service/type/{serviceTypeId}" }, method = RequestMethod.DELETE)
	public JsonApi organizationServiceTypeDelete(
			@PathVariable("organizationid")Integer organizationid,
			@PathVariable("serviceTypeId")Integer serviceTypeId,
			@Validated({ BaseEntity.Delete.class }) OrganizationServiceType organizationServiceType, BindingResult result) {
		organizationServiceType.setOrganizationId(organizationid);
		organizationServiceType.setServiceTypeId(serviceTypeId);
		if (organizationServiceTypeService.delete(organizationServiceType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationServiceType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 新增机构服务类型
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationServiceType:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/type" }, method = RequestMethod.POST)
	public JsonApi organizationServiceTypeInsert(
			@Validated({ BaseEntity.Insert.class })@RequestBody OrganizationServiceType organizationServiceType, BindingResult result) {
		//判断是否存在
		Map<String, Object> organizationServiceTypeMap = organizationServiceTypeService.getRepeat(organizationServiceType);
		if (organizationServiceTypeMap!=null && !organizationServiceTypeMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.service.type.repeat"));
		}
		//机构是否存在
		Organization organization = new Organization();
		organization.setId(organizationServiceType.getOrganizationId());
		Map<String, Object> organizatioMap = organizationService.getOne(organization);
		if (organizatioMap==null || organizatioMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("organization.is.null"));
		}
		//服务类型是否存在
		ServiceType serviceType = new ServiceType();
		serviceType.setId(organizationServiceType.getServiceTypeId());
		Map<String, Object> serviceTypeMap = serviceTypeService.getOne(serviceType);
		if (serviceTypeMap==null || serviceTypeMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("service.type.is.null"));
		}
		//新增数据
		if (organizationServiceTypeService.insert(organizationServiceType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationid
	 * @param organizationServiceType
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年6月6日
	 * @version 1.0
	 * @description 查询机构拥有的服务类型和没有的服务类型
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationServiceType:IsChoose:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/type/is/choose/service/type/{organizationid}" }, method = RequestMethod.GET)
	public JsonApi getOrganizationPackageIsNullService(
			@PathVariable("organizationid")Integer organizationid,
			@Validated(BaseEntity.SelectAll.class)  OrganizationServiceType organizationServiceType,BindingResult result){
		organizationServiceType.setOrganizationId(organizationid);
		Page<?> page = PageHelper.startPage(organizationServiceType.getPage(), organizationServiceType.getPageSize());
		List<Map<String, Object>>  organizationServiceTypeList=organizationServiceTypeService.getOrganizationServiceTypeIsChoose(organizationServiceType);
		if (organizationServiceTypeList!=null && !organizationServiceTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationServiceTypeList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
