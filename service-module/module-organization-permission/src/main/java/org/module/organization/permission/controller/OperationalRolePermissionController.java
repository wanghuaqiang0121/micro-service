package org.module.organization.permission.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalRolePermission;
import org.module.organization.permission.service.OperationalRolePermissionService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
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

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年8月15日
 * @Version 
 * @Description 机构角色权限关联表
 */
@RestController
public class OperationalRolePermissionController {
	@Autowired
	private OperationalRolePermissionService operationalRolePermissionService;

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param operationalRolePermission
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年8月15日
	 * @version 1.0
	 * @description 新增机构角色权限关联表
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/role/permission" }, method = RequestMethod.POST)
	public JsonApi insert(
			@RequestBody @Validated({ BaseEntity.Insert.class }) OperationalRolePermission operationalRolePermission,
			BindingResult result) {
		Map<String, Object> operationalRolePermissionMap = operationalRolePermissionService.getRepeat(operationalRolePermission);
		if(operationalRolePermissionMap!=null && !operationalRolePermissionMap.isEmpty()){
			return new JsonApi(ApiCode.CONFLICT);
		}
		/* 新增 */
		if (operationalRolePermissionService.insert(operationalRolePermission) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param operationalRoleId
	 * @param operationalPermissionId
	 * @param operationalRolePermission
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年8月15日
	 * @version 1.0
	 * @description 删除机构角色权限关联表
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = {
			"/operational/role/{operationalRoleId}/permission/{operationalPermissionId}" }, method = RequestMethod.DELETE)
	public JsonApi delete(@PathVariable("operationalRoleId") Integer operationalRoleId,
			@PathVariable("operationalPermissionId") Integer operationalPermissionId,
			@Validated({ BaseEntity.Delete.class }) OperationalRolePermission operationalRolePermission,
			BindingResult result) {
		operationalRolePermission.setOperationalRoleId(operationalRoleId);
		operationalRolePermission.setOperationalPermissionId(operationalPermissionId);
		/* 删除 */
		if (operationalRolePermissionService.delete(operationalRolePermission) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param operationalRolePermission
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年8月15日
	 * @version 1.0
	 * @description 查询该角色拥有和未拥有的权限
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/role/permissions" }, method = RequestMethod.GET)
	public JsonApi getPermissionOperationIsChoose(
			@Validated({OperationalRolePermission.GetPermissionOperationIsChoose.class }) OperationalRolePermission operationalRolePermission,
			BindingResult result) {
		Page<?> page = PageHelper.startPage(operationalRolePermission.getPage(),
				operationalRolePermission.getPageSize());
		List<Map<String, Object>> operationalRolePermissionList = operationalRolePermissionService
				.getPermissionOperationIsChoose(operationalRolePermission);
		if (operationalRolePermissionList != null && !operationalRolePermissionList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), operationalRolePermissionList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);

	}

}
