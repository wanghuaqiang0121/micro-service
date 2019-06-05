package org.module.organization.team.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.team.domain.UserType;
import org.module.organization.team.service.UserTypeService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
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
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月23日
 * @Version 
 * @Description 人群类型
 */
@RestController
public class UserTypeController {

	@Autowired
	private UserTypeService userTypeService;

	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userType
	* @param result
	* @param token
	* @return 
	* @date 2018年4月2日
	* @version 1.0
	* @description 人群类型列表
	*/
	@RequiresAuthentication(authc = true, value = { "organization:team:userType:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/user/types" }, method = RequestMethod.GET)
	public JsonApi userTypeList(@Validated({ BaseEntity.SelectAll.class })  UserType userType,
			BindingResult result) {
		/* 查询通用标签 */
		userType.setApplyType(0);
		/* 查询团队所属机构对应的人群类型 */
		Page<?> page = PageHelper.startPage(userType.getPage(), userType.getPageSize());
		List<Map<String, Object>> userTypeList = userTypeService.getList(userType);
		if (userTypeList != null && !userTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), userTypeList));
		}

		return new JsonApi(ApiCode.NOT_FOUND);
	}

}
