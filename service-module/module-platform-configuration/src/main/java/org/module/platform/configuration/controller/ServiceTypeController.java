package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.ServiceType;
import org.module.platform.configuration.global.ServiceTypeStatusCode;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.ServiceTypeService;
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
 * @Description 服务类型
 */
@RestController
public class ServiceTypeController {
	
	@Autowired
	private ServiceTypeService serviceTypeService;

	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param serviceType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 服务类型列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:serviceType:list" })
	@RequestMapping(value = { "/service/type" }, method = RequestMethod.GET)
	public JsonApi serviceTypeList( @Validated({ BaseEntity.SelectAll.class })  ServiceType serviceType, BindingResult result) {
		Page<?> page = PageHelper.startPage(serviceType.getPage(), serviceType.getPageSize());
		List<Map<String, Object>> serviceTypeList = serviceTypeService.getList(serviceType);
		if (serviceTypeList != null && !serviceTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), serviceTypeList));
		}

		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param serviceType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 新增服务类型
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:serviceType:insert" })
	@RequestMapping(value = { "/service/type" }, method = RequestMethod.POST)
	public JsonApi serviceTypeInsert(@Validated({ BaseEntity.Insert.class }) @RequestBody ServiceType serviceType,BindingResult result) {
		//数据是否存在
		Map<String, Object> serviceTypeRepeatMap = serviceTypeService.getRepeat(serviceType);
		if (MapUtils.isNotEmpty(serviceTypeRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("service.type.code.is.repeat"));
		}
		// 新增服务类型
		serviceType.setCreateDate(new Date());
		serviceType.setStatus(ServiceTypeStatusCode.ServiceType.ENABLE.getValue());
		if (serviceTypeService.insert(serviceType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param serviceType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 修改服务类型
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:serviceType:update" })
	@RequestMapping(value = { "/service/type/{id}" }, method = RequestMethod.PUT)
	public JsonApi serviceTypeUpdate(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class }) @RequestBody ServiceType serviceType,
			BindingResult result) {
		serviceType.setId(id);
		//数据是否存在
		Map<String, Object> serviceTypeOneMap = serviceTypeService.getOne(serviceType);
		if (MapUtils.isEmpty(serviceTypeOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		//数据是否重复
		Map<String, Object> serviceTypeRepeatMap = serviceTypeService.getRepeat(serviceType);
		if (MapUtils.isNotEmpty(serviceTypeRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("service.type.code.is.repeat"));
		}
		// 修改服务类型
		if (serviceTypeService.update(serviceType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param serviceType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 服务类型详情
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:serviceType:detail" })
	@RequestMapping(value = { "/service/type/{id}" }, method = RequestMethod.GET)
	public JsonApi serviceTypeDetail(@PathVariable("id")Integer id, @Validated({ BaseEntity.SelectOne.class })  ServiceType serviceType, BindingResult result) {
		serviceType.setId(id);
		//数据是否存在
		Map<String, Object> serviceTypeOne = serviceTypeService.getOne(serviceType);
		if (MapUtils.isNotEmpty(serviceTypeOne)) {
			return new JsonApi(ApiCode.OK,serviceTypeOne);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
