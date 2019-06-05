package org.module.organization.permission.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalPermissionOperation;
import org.module.organization.permission.service.OperationalPermissionOperationService;
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

@RestController
public class OperationalPermissionOperationController {
	@Autowired
	private OperationalPermissionOperationService operationalPermissionOperationService;
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param operationalPermissionOperation
	 * @param result
	 * @return
	 * @date 2018年5月2日
	 * @version 1.0
	 * @description 新增权限操作
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permission/operation" }, method = RequestMethod.POST)
	public JsonApi insert(
			@RequestBody @Validated({ BaseEntity.Insert.class }) OperationalPermissionOperation  operationalPermissionOperation,
			BindingResult result
			){
		Map<String, Object> operationalPermissionOperationMap = operationalPermissionOperationService.getRepeat(operationalPermissionOperation);
		if(operationalPermissionOperationMap!=null && !operationalPermissionOperationMap.isEmpty()){
			return new JsonApi(ApiCode.CONFLICT);
		}
		/*新增*/
		if (operationalPermissionOperationService.insert(operationalPermissionOperation)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param operationalPermissionId
	 * @param operationalOperationId
	 * @param operationalPermissionOperation
	 * @param result
	 * @return
	 * @date 2018年5月2日
	 * @version 1.0
	 * @description 删除权限操作
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permission/{operationalPermissionId}/operation/{operationalOperationId}" }, method = RequestMethod.DELETE)
	public JsonApi delete(
			@PathVariable("operationalPermissionId") Integer operationalPermissionId,
			@PathVariable("operationalOperationId") Integer operationalOperationId,
			@Validated({ BaseEntity.Delete.class }) OperationalPermissionOperation  operationalPermissionOperation,
			BindingResult result
			){
		operationalPermissionOperation.setOperationalPermissionId(operationalPermissionId);
		operationalPermissionOperation.setOperationalOperationId(operationalOperationId);
		/*删除*/
		if (operationalPermissionOperationService.delete(operationalPermissionOperation)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param operationalPermissionOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月17日
	* @version 1.0
	* @description 查询该权限拥有和未拥有的操作
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permission/operations" }, method = RequestMethod.GET)
	public JsonApi getPermissionOperationIsChoose(
			@Validated({ OperationalPermissionOperation.GetPermissionOperationIsChoose.class }) OperationalPermissionOperation  operationalPermissionOperation,
			BindingResult result
			){
		Page<?> page = PageHelper.startPage(operationalPermissionOperation.getPage(), operationalPermissionOperation.getPageSize());
		List<Map<String, Object>> operationalPermissionOperationList=operationalPermissionOperationService.getPermissionOperationIsChoose(operationalPermissionOperation);
		if (operationalPermissionOperationList!=null && !operationalPermissionOperationList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), operationalPermissionOperationList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	
	}


}
