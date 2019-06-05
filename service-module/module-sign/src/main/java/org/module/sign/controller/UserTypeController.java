package org.module.sign.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.sign.domain.UserType;
import org.module.sign.message.Prompt;
import org.module.sign.service.UserTypeService;
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
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月23日
 * @Version 
 * @Description 人群类型
 */
@RestController
public class UserTypeController {

	@Autowired
	private UserTypeService userTypeService;

	@RequiresAuthentication(authc = true, value = { "platform:configure:userType:list" })
	@RequestMapping(value = { "/user/types" }, method = RequestMethod.GET)
	public JsonApi userTypeList(@Validated({ BaseEntity.SelectAll.class })  UserType userType,BindingResult result) {
		Page<?> page = PageHelper.startPage(userType.getPage(), userType.getPageSize());
		List<Map<String, Object>> userTypeList = userTypeService.getList(userType);
		if (userTypeList != null && !userTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), userTypeList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param userType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 新增人群类型
	*/
	@RequiresAuthentication(authc = true, value = { "platform:configure:userType:insert" })
	@RequestMapping(value = { "/user/type" }, method = RequestMethod.POST)
	public JsonApi userTypeInsert(@Validated({ BaseEntity.Insert.class }) @RequestBody UserType userType,BindingResult result) {
		//数据是否存在
		Map<String, Object> userTypeRepeatMap = userTypeService.getRepeat(userType);
		if (MapUtils.isNotEmpty(userTypeRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.type.name.is.repeat"));
		}
		//新增
		userType.setCreateDate(new Date());
		//userType.setIsUsed((Boolean)UserTypeStatusCode.UserType.ENABLE.getValue());
		if (userTypeService.insert(userType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param userType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 修改人群类型
	*/
	@RequiresAuthentication(authc = true, value = { "platform:configure:userType:update" })
	@RequestMapping(value = { "/user/type/{id}" }, method = RequestMethod.PUT)
	public JsonApi userTypeUpdate(@PathVariable("id")Integer id,@Validated({ BaseEntity.Update.class }) @RequestBody UserType userType,	BindingResult result) {
		userType.setId(id);
		//数据是否存在
		Map<String, Object> userTypeOneMap = userTypeService.getOne(userType);
		if (MapUtils.isEmpty(userTypeOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		//数据是否重复
		Map<String, Object> userTypeRepeatMap = userTypeService.getRepeat(userType);
		if (MapUtils.isNotEmpty(userTypeRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.type.name.is.repeat"));
		}
		//修改
		if (userTypeService.update(userType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param userType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 是否启用禁用
	*/
	@RequiresAuthentication(authc = true, value = { "platform:configure:userType:isUsed" })
	@RequestMapping(value = { "/user/type/is/used/{id}" }, method = RequestMethod.PUT)
	public JsonApi userTypeIsUsed(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class }) @RequestBody UserType userType,BindingResult result) {
		userType.setId(id);
		//数据是否存在
		Map<String, Object> userTypeOneMap = userTypeService.getOne(userType);
		if (MapUtils.isEmpty(userTypeOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		UserType userTypeNew = new UserType();
		userTypeNew.setId(id);
		userTypeNew.setIsUsed(userType.getIsUsed());
		//修改
		if (userTypeService.update(userTypeNew) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param userType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 人群类型详情
	*/
	@RequiresAuthentication(authc = true, value = { "platform:configure:userType:detail" })
	@RequestMapping(value = { "/user/type/{id}" }, method = RequestMethod.GET)
	public JsonApi userTypeDetail(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class })  UserType userType, BindingResult result) {
		userType.setId(id);
		//数据是否存在
		Map<String, Object> userTypeOneMap = userTypeService.getOne(userType);
		if (MapUtils.isNotEmpty(userTypeOneMap)) {
			return new JsonApi(ApiCode.OK,userTypeOneMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param userType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 删除人群类型
	*/
	@RequiresAuthentication(authc = true, value = { "platform:configure:userType:delete" })
	@RequestMapping(value = { "/user/type/{id}" }, method = RequestMethod.DELETE)
	public JsonApi userTypeDelete(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Delete.class })  UserType userType, BindingResult result) {
		userType.setId(id);
		//数据是否存在
		if (userTypeService.delete(userType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
