package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.DoctorTitle;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.DoctorTitleService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
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
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年4月13日
 * @Version 
 * @Description 医生职称表
 */
@RestController
public class DoctorTitleController {

	@Resource
	private DoctorTitleService doctorTitleService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorTitle
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月13日
	* @version 1.0
	* @description  新增医生职称
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:doctorTitle:insert" })
	@RequestMapping(value = { "/doctor/title" }, method = RequestMethod.POST)
	public JsonApi doctorTitleInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody DoctorTitle doctorTitle, BindingResult result) {
		doctorTitle.setCreateDate(new Date());
		//判断数据是否重复
		Map<String, Object> doctorTitleRepeatMap = doctorTitleService.getRepeat(doctorTitle);
		if (MapUtils.isNotEmpty(doctorTitleRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("doctorTitle.title.name.is.exists"));
		}
		//新增医生职称
		if (doctorTitleService.insert(doctorTitle) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param doctorTitle
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月13日
	* @version 1.0
	* @description 查看医生职称详情
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:doctorTitle:detail" })
	@RequestMapping(value = { "/doctor/title/{id}" }, method = RequestMethod.GET)
	public JsonApi doctorTitleOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) DoctorTitle doctorTitle, BindingResult result) {
		doctorTitle.setId(id);
		// 查看医生职称详情
		Map<String, Object> organizationSiteOne = doctorTitleService.getOne(doctorTitle);
		if (MapUtils.isNotEmpty(organizationSiteOne)) {
			return new JsonApi(ApiCode.OK,organizationSiteOne);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorTitle
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月13日
	* @version 1.0
	* @description 查看医生职称列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:doctorTitle:list" })
	@RequestMapping(value = { "/doctor/titles" }, method = RequestMethod.GET)
	public JsonApi doctorTitleList(
			@Validated({ BaseEntity.SelectAll.class }) DoctorTitle doctorTitle, BindingResult result) {
		Page<?> page = PageHelper.startPage(doctorTitle.getPage(), doctorTitle.getPageSize());
		// 查看医生职称列表
		List<Map<String, Object>> organizationSiteList = doctorTitleService.getList(doctorTitle);
		if (organizationSiteList !=null && !organizationSiteList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationSiteList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param doctorTitle
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月13日
	* @version 1.0
	* @description 修改医生职称
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:doctorTitle:update" })
	@RequestMapping(value = { "/doctor/title/{id}" }, method = RequestMethod.PUT)
	public JsonApi doctorTitleUpdate(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody DoctorTitle doctorTitle, BindingResult result) {
		// 修改的数据是否存在
		doctorTitle.setId(id);
		Map<String, Object> doctorTitleOneMap = doctorTitleService.getOne(doctorTitle);
		if (MapUtils.isEmpty(doctorTitleOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 名称是否重复
		if (null != doctorTitle.getName()) {
			Map<String, Object> doctorTitleRepeatMap = doctorTitleService.getRepeat(doctorTitle);
			if (MapUtils.isNotEmpty(doctorTitleRepeatMap)) {
				if (!doctorTitleOneMap.get("id").equals(doctorTitleRepeatMap.get("id"))) {
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("doctorTitle.title.name.is.exists"));
				}
			}
		}
		//修改医生职称列表
		if (doctorTitleService.update(doctorTitle) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
