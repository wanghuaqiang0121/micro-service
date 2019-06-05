package org.module.organization.permission.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalOperation;
import org.module.organization.permission.message.Prompt;
import org.module.organization.permission.service.OperationalOperationService;
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
public class OperationalOperationController {

	@Autowired
	private OperationalOperationService operationalOperationService;
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param operationalOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 新增机构操作
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/operation" }, method = RequestMethod.POST)
	public JsonApi insert(
			@Validated({ BaseEntity.Insert.class })@RequestBody OperationalOperation  operationalOperation,BindingResult result){
		/*设置时间*/
		operationalOperation.setCreateDate(new Date());
		/*判断是否重复*/
		Map<String, Object> operationalOperationMap = operationalOperationService.getRepeat(operationalOperation);
		if (operationalOperationMap!=null && !operationalOperationMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("operational.operation.code.is.conflict"));
		}
		if (operationalOperationService.insert(operationalOperation)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param operationalOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 修改机构操作
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/operation/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OperationalOperation  operationalOperation,BindingResult result){
		operationalOperation.setId(id);
		//判断是否存在
		Map<String, Object> operationalOperationOneMap = operationalOperationService.getOne(operationalOperation);
		if (operationalOperationOneMap==null || operationalOperationOneMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		/*判断是否重复*/
		if (null!=operationalOperation.getCode()) {
			
			Map<String, Object> operationalOperationMap = operationalOperationService.getRepeat(operationalOperation);
			if (operationalOperationMap!=null && !operationalOperationMap.isEmpty()){
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("operational.operation.code.is.conflict"));
			}
		}
		if (operationalOperationService.update(operationalOperation)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param operationalOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 机构操作详情
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/operation/{id}" }, method = RequestMethod.GET)
	public JsonApi getOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OperationalOperation  operationalOperation,BindingResult result){
		operationalOperation.setId(id);
		Map<String, Object> operationalOperationMap = operationalOperationService.getOne(operationalOperation);
		if (operationalOperationMap!=null && !operationalOperationMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,operationalOperationMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}


	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param operationalOperation
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年5月21日
	* @version 1.0
	* @description 机构操作列表
	*/
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/operations" }, method = RequestMethod.GET)
	public JsonApi getList(
			@Validated({ BaseEntity.SelectAll.class }) OperationalOperation  operationalOperation,BindingResult result){
		
		Page<?> page = PageHelper.startPage(operationalOperation.getPage(), operationalOperation.getPageSize());
		List<Map<String, Object>> operationalOperationList = operationalOperationService.getList(operationalOperation);
		if (operationalOperationList != null && !operationalOperationList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),operationalOperationList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
