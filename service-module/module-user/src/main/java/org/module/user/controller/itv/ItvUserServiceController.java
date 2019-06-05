package org.module.user.controller.itv;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.module.user.domain.order.UserService;
import org.module.user.global.BaseGlobal;
import org.module.user.global.Service;
import org.module.user.service.order.UserServiceService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年4月10日
 * @Version 
 * @Description 用户服务
 */
@RestController
public class ItvUserServiceController {

	@Autowired
	private UserServiceService userServiceService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userService
	* @param result
	* @return 
	* @date 2018年4月11日
	* @version 1.0
	* @description 用户视频服务列表
	*/
	@RequiresAuthentication(ignore = true, value = { "itv:userService:services" })
	@RequestMapping(value = { "/user/services" }, method = RequestMethod.GET)
	public JsonApi getUserServices(@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@Validated(BaseEntity.SelectAll.class) UserService userService, BindingResult result) {
		userService.setServiceTypeCode(Service.ServiceType.VIDEOCONSULTATION.getValue());
		Page<?> page = PageHelper.startPage(userService.getPage(), userService.getPageSize());
		// 用户服务列表
		List<Map<String, Object>> usersResult = userServiceService.getList(userService);
		if (CollectionUtils.isNotEmpty(usersResult)) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), usersResult));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
