package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.Service;
import org.module.platform.configuration.domain.ServiceType;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.ServiceService;
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
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月14日
 * @Version 
 * @Description 服务
 */
@RestController
public class ServiceController {

	@Autowired
	private ServiceService serviceService;
	@Autowired
	private ServiceTypeService serviceTypeService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param serviceTypeId
	* @param service
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月29日
	* @version 1.0
	* @description 服务列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:service:list" })
	@RequestMapping(value = { "/services" }, method = RequestMethod.GET)
	public JsonApi serviceList(
			@Validated({ BaseEntity.SelectAll.class })  Service service,
			BindingResult result) {
		Page<?> page = PageHelper.startPage(service.getPage(), service.getPageSize());
		List<Map<String, Object>> serviceList = serviceService.getList(service);
		if (serviceList != null && !serviceList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), serviceList));
		}

		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param service
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description 新增服务
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:service:insert" })
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST)
	public JsonApi serviceInsert(
			@Validated({ BaseEntity.Insert.class })@RequestBody  Service service,
			BindingResult result) {
		//查询服务是否存在
		ServiceType serviceType = new ServiceType();
		serviceType.setId(service.getServiceTypeId());
		Map<String, Object> serviceTypeOneMap = serviceTypeService.getOne(serviceType);
		if (MapUtils.isEmpty(serviceTypeOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("service.type.not.found"));
		}
		service.setCreateDate(new Date());
		if (serviceService.insert(service) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param service
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description 修改服务
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:service:update" })
	@RequestMapping(value = { "/service/{id}" }, method = RequestMethod.PUT)
	public JsonApi serviceUpdate(
			@PathVariable("id")Integer id, @Validated({ BaseEntity.Update.class })@RequestBody  Service service, BindingResult result) {
		//查询服务是否存在
		service.setId(id);
		Map<String, Object> serviceOneMap = serviceService.getOne(service);
		if (MapUtils.isEmpty(serviceOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if (serviceService.update(service) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
	}
}
