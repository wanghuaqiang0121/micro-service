package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationUserType;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationUserTypeService;
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
public class OrganizationUserTypeController {
	@Autowired
	private OrganizationUserTypeService organizationUserTypeService;

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationUserType
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 新增机构,用户类型关联表
	 */
	@RequiresAuthentication( value = { "organization:configure:OrganizationUserType:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/type" }, method = RequestMethod.POST)
	public JsonApi insert(@RequestBody @Validated({ BaseEntity.Insert.class })OrganizationUserType  organizationUserType,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId
			){
		organizationUserType.setOrganizationId(organizationId);
		Map<String, Object> OrganizationUserTypeMap=organizationUserTypeService.getRepeat(organizationUserType);
		if (OrganizationUserTypeMap!=null && !OrganizationUserTypeMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.user.type.is.exists"));
		}
		if (organizationUserTypeService.insert(organizationUserType)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationUserType
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 查询机构,用户类型列表
	 */
	@RequiresAuthentication( value = { "organization:configure:OrganizationUserType:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/types" }, method = RequestMethod.GET)
	public JsonApi getDepartmentList(@Validated({ BaseEntity.SelectAll.class }) OrganizationUserType  organizationUserType,
			BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		organizationUserType.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationUserType.getPage(), organizationUserType.getPageSize());
		List<Map<String, Object>>  rganizationUserTypeList=organizationUserTypeService.getList(organizationUserType);
		if (rganizationUserTypeList!=null && !rganizationUserTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), rganizationUserTypeList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationUserType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月16日
	* @version 1.0
	* @description 当前机构没有关联的用户类型
	*/
	@RequiresAuthentication( value = { "organization:configure:OrganizationUserType:isNull:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/is/null/user/types" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserTypeIsNullList(
			@Validated({ OrganizationUserType.organizationUserTypeIsNull.class }) OrganizationUserType  organizationUserType,
			BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId ){
		organizationUserType.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationUserType.getPage(), organizationUserType.getPageSize());
		List<Map<String, Object>>  rganizationUserTypeList=organizationUserTypeService.getOrganizationUserTypeIsNull(organizationUserType);
		if (rganizationUserTypeList!=null && !rganizationUserTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), rganizationUserTypeList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationUserType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年6月21日
	* @version 1.0
	* @description 机构拥有和未拥有的用户类型
	*/
	@RequiresAuthentication( value = { "organization:configure:OrganizationUserType:haveAndNotHave:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/is/have/nothave/user/types" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserTypehaveAndNotHaveList(
			@Validated({ OrganizationUserType.organizationUserTypehaveAndNotHave.class }) OrganizationUserType  organizationUserType,
			BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId ){
		organizationUserType.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationUserType.getPage(), organizationUserType.getPageSize());
		List<Map<String, Object>>  rganizationUserTypeList=organizationUserTypeService.getOrganizationUserTypehaveAndNotHaveList(organizationUserType);
		if (rganizationUserTypeList!=null && !rganizationUserTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), rganizationUserTypeList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationIdd
	 * @param userTypeId
	 * @param rganizationUserType
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 删除机构,用户类型关联表
	 */
	@RequiresAuthentication( value = { "organization:configure:OrganizationUserType:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/{organizationId}/user/type/{userTypeId}" }, method = RequestMethod.DELETE)
	public JsonApi delete(@PathVariable("organizationId") Integer organizationIdd,
			@PathVariable("userTypeId") Integer userTypeId,
			@Validated({ BaseEntity.Delete.class }) OrganizationUserType  rganizationUserType,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
	    /*设置数据*/
		rganizationUserType.setOrganizationId(organizationId);
		rganizationUserType.setUserTypeId(userTypeId);
		if (organizationUserTypeService.delete(rganizationUserType)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

}
