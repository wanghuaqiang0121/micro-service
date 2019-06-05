package org.module.organization.configure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationUserRole;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.global.OrganizationUserStatusCode;
import org.module.organization.configure.service.OrganizationUserRoleService;
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
public class OrganizationUserRoleController {

	@Autowired
	private OrganizationUserRoleService organizationUserRoleService;
	
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationUserRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 新增机构用户角色
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationUserRole:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/role" }, method = RequestMethod.POST)
	public JsonApi insert(
			@RequestBody @Validated({ BaseEntity.Insert.class }) OrganizationUserRole organizationUserRole,BindingResult result){
		/*判断是否重复*/
		Map<String, Object> organizationUserRoleMap = organizationUserRoleService.getRepeat(organizationUserRole);
		if (organizationUserRoleMap!=null && !organizationUserRoleMap.isEmpty()) {
			OrganizationUserRole organizationUserRoleNew = new OrganizationUserRole();
			organizationUserRoleNew.setId((Integer)organizationUserRoleMap.get("id"));
			organizationUserRoleNew.setStatus(OrganizationUserStatusCode.organizationUserRole.ENABLE.getValue());
			if (organizationUserRoleService.update(organizationUserRoleNew)>0) {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("id", organizationUserRoleNew.getId());
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}else {
			organizationUserRole.setStatus(OrganizationUserStatusCode.organizationUserRole.ENABLE.getValue());
			if (organizationUserRoleService.insert(organizationUserRole)>0) {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("id", organizationUserRole.getId());
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationUserRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 查询机构用户角色列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationUserRole:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/role" }, method = RequestMethod.GET)
	public JsonApi getList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationUserRole organizationUserRole,BindingResult result){
		Page<?> page = PageHelper.startPage(organizationUserRole.getPage(), organizationUserRole.getPageSize());
		List<Map<String, Object>>  organizationUserRoleList=organizationUserRoleService.getList(organizationUserRole);
		if (organizationUserRoleList!=null && !organizationUserRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserRoleList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationUserRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 机构用户在该机构下拥有和未拥有的角色列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationUserRole:haveAndNotHave:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/have/nothave/role" }, method = RequestMethod.GET)
	public JsonApi getHaveAndNotHave(
			@Validated({ OrganizationUserRole.haveAndNotHave.class }) OrganizationUserRole organizationUserRole,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		organizationUserRole.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationUserRole.getPage(), organizationUserRole.getPageSize());
		List<Map<String, Object>>  organizationUserRoleList=organizationUserRoleService.getHaveAndNotHave(organizationUserRole);
		if (organizationUserRoleList!=null && !organizationUserRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationUserRoleList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param organizationUserRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月18日
	* @version 1.0
	* @description 删除机构用户和角色关联
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationUserRole:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/role/{id}" }, method = RequestMethod.DELETE)
	public JsonApi delete(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Delete.class }) OrganizationUserRole organizationUserRole,BindingResult result){
		organizationUserRole.setId(id);
		organizationUserRole.setStatus(OrganizationUserStatusCode.organizationUserRole.DISABLE.getValue());
		if (organizationUserRoleService.update(organizationUserRole)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
