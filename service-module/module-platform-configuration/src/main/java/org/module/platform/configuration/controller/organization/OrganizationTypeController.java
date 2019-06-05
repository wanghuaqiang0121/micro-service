package org.module.platform.configuration.controller.organization;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.OrganizationType;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.organization.OrganizationTypeService;
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
 * @Date 2018年3月21日
 * @Version 
 * @Description 机构类型
 */
@RestController
public class OrganizationTypeController {
	
	@Autowired
	private OrganizationTypeService organizationTypeService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationScale
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 新增机构类型
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationType:insert" })
	@RequestMapping(value = { "/organization/type" }, method = RequestMethod.POST)
	public JsonApi organizationTypeInsert(
			@Validated({ BaseEntity.Insert.class })@RequestBody OrganizationType organizationType, BindingResult result) {
		organizationType.setCreateDate(new Date());
		//判断是否重复
		Map<String, Object> organizationScaleRepeatMap = organizationTypeService.getRepeat(organizationType);
		if (MapUtils.isNotEmpty(organizationScaleRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.type.name.is.conflict"));
		}
		//新增
		if (organizationTypeService.insert(organizationType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param organizationType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 修改机构类型
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationType:update" })
	@RequestMapping(value = { "/organization/type/{id}" }, method = RequestMethod.PUT)
	public JsonApi organizationTypeUpdate(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OrganizationType organizationType, BindingResult result) {
		organizationType.setId(id);
		//数据是否存在
		Map<String, Object> organizationTypeOneMap = organizationTypeService.getOne(organizationType);
		if (MapUtils.isEmpty(organizationTypeOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		//修改的数据是否重复
		Map<String, Object> organizationTypeRepeatMap = organizationTypeService.getRepeat(organizationType);
		if (MapUtils.isNotEmpty(organizationTypeRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.type.name.is.conflict"));
		}
		//修改
		if (organizationTypeService.update(organizationType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param organizationType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 删除机构类型
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationType:delete" })
	@RequestMapping(value = { "/organization/type/{id}" }, method = RequestMethod.DELETE)
	public JsonApi organizationTypeDelete(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Delete.class }) OrganizationType organizationType, BindingResult result) {
		organizationType.setId(id);
		//删除
		if (organizationTypeService.delete(organizationType) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param organizationType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 查询机构类型详情
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationType:detail" })
	@RequestMapping(value = { "/organization/type/{id}" }, method = RequestMethod.GET)
	public JsonApi organizationTypeDetail(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OrganizationType organizationType, BindingResult result) {
		organizationType.setId(id);
		Map<String, Object> organizationTypeOneMap = organizationTypeService.getOne(organizationType);
		if (MapUtils.isNotEmpty(organizationTypeOneMap)) {
			return new JsonApi(ApiCode.OK,organizationTypeOneMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationType
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 查询机构类型列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationType:list" })
	@RequestMapping(value = { "/organization/type" }, method = RequestMethod.GET)
	public JsonApi organizationTypeList(
			@Validated({ BaseEntity.SelectAll.class }) OrganizationType organizationType, BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationType.getPage(), organizationType.getPageSize());
		List<Map<String, Object>> organizationTypeList = organizationTypeService.getList(organizationType);
		if (organizationTypeList != null && !organizationTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(), organizationTypeList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
