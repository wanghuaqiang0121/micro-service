package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.ServicePackageType;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.ServicePackageTypeService;
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

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月23日
 * @Version 
 * @Description 服务包类型
 */
@RestController
public class ServicePackageTypeController {
	@Autowired
	private ServicePackageTypeService servicePackageTypeService;

	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param servicePackageType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 服务包类型列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:servicePackageType:list" })
	@RequestMapping(value = { "/service/package/type" }, method = RequestMethod.GET)
	public JsonApi servicePackageTypeList( @Validated({ BaseEntity.SelectAll.class })  ServicePackageType servicePackageType, BindingResult result) {
		Page<?> page = PageHelper.startPage(servicePackageType.getPage(), servicePackageType.getPageSize());
		List<Map<String, Object>> servicePackageTypeList = servicePackageTypeService.getList(servicePackageType);
		if (servicePackageTypeList != null && !servicePackageTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), servicePackageTypeList));
		}

		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param servicePackageType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 新增服务包类型
	*/
	@RequiresAuthentication( level=Level.OPERATION,value = { "platform:configure:servicePackageType:insert" })
	@RequestMapping(value = { "/service/package/type" }, method = RequestMethod.POST)
	public JsonApi servicePackageTypeInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody ServicePackageType servicePackageType,
			BindingResult result) {
		//数据是否存在
		Map<String, Object> servicePackageTypeRepeatMap = servicePackageTypeService.getRepeat(servicePackageType);
		if (MapUtils.isNotEmpty(servicePackageTypeRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("service.package.type.code.is.exists"));
		}
		//新增
		servicePackageType.setCreateDate(new Date());
		if (servicePackageTypeService.insert(servicePackageType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param servicePackageType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 修改机构服务包
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:servicePackageType:update" })
	@RequestMapping(value = { "/service/package/type/{id}" }, method = RequestMethod.PUT)
	public JsonApi servicePackageTypeUpdate(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class }) @RequestBody ServicePackageType servicePackageType,
			BindingResult result) {
		servicePackageType.setId(id);
		// 数据是否存在
		Map<String, Object> servicePackageTypeOneMap = servicePackageTypeService.getOne(servicePackageType);
		if (MapUtils.isEmpty(servicePackageTypeOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 数据是否重复
		Map<String, Object> servicePackageTypeRepeatMap = servicePackageTypeService.getRepeat(servicePackageType);
		if (MapUtils.isNotEmpty(servicePackageTypeRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("service.package.type.code.is.exists"));
		}
		// 修改机构服务包
		if (servicePackageTypeService.update(servicePackageType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param servicePackageType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 服务包类型详情
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:servicePackageType:detail" })
	@RequestMapping(value = { "/service/package/type/{id}" }, method = RequestMethod.GET)
	public JsonApi servicePackageTypeDetail(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class })  ServicePackageType servicePackageType, BindingResult result) {
		servicePackageType.setId(id);
		//数据是否存在
		Map<String, Object> servicePackageTypeOne = servicePackageTypeService.getOne(servicePackageType);
		if (MapUtils.isNotEmpty(servicePackageTypeOne)) {
			return new JsonApi(ApiCode.OK,servicePackageTypeOne);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
