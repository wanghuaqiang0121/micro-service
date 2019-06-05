package org.module.user.controller.userservice;

import java.util.List;
import java.util.Map;

import org.module.user.domain.order.UserService;
import org.module.user.global.Service.BusinessType;
import org.module.user.service.order.UserServiceService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年10月12日
 * @Version 
 * @Description 用户服务
 */
@RestController
public class UserServiceController {
	@Autowired
	private UserServiceService userServiceService;
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param userService
	* @param result
	* @return 
	* @date 2018年10月12日
	* @version 1.0
	* @description 用户预约服务列表
	*/
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/user/appointment/services" }, method = RequestMethod.GET)
	public JsonApi getList(@Validated({ BaseEntity.SelectAll.class }) UserService userService,BindingResult result) {
		userService.setBusinessCode(BusinessType.APPOINTMENTTYPE.getValue());
		Page<?> page = PageHelper.startPage(userService.getPage(), userService.getPageSize());
		List<Map<String, Object>> userServiceServiceList = userServiceService.getAppointmentServiceList(userService);
		if (userServiceServiceList != null && userServiceServiceList.size() > 0) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), userServiceServiceList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param userService
	* @param result
	* @return 
	* @date 2018年11月9日
	* @version 1.0
	* @description 根据服务类型id查询服务列表
	*/
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/user/services/by/service/type" }, method = RequestMethod.GET)
	public JsonApi getServices(@Validated({ UserService.SelectServices.class }) UserService userService,BindingResult result) {
		Page<?> page = PageHelper.startPage(userService.getPage(), userService.getPageSize());
		List<Map<String, Object>> userServicesList = userServiceService.getServicesByServiceTypeId(userService);
		if (userServicesList != null && userServicesList.size() > 0) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), userServicesList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
