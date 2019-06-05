package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.DoctorLevel;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.DoctorLevelService;
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
 * @Date 2018年8月6日
 * @Version 
 * @Description 医生职称（医生级别）
 */
@RestController
public class DoctorLevelController {

	@Autowired
	private DoctorLevelService doctorLevelService;
	
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param doctorLevel
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年8月6日
	* @version 1.0
	* @description 新增医生职称（医生级别）
	*/
	@RequiresAuthentication(value = { "platform:configure:doctorLevel:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/doctor/level" }, method = RequestMethod.POST)
	public JsonApi insert(@RequestBody @Validated({ BaseEntity.Insert.class }) DoctorLevel  doctorLevel,BindingResult result){
		/*设置时间*/
		doctorLevel.setCreateDate(new Date());
		/*判断是否重复*/
		Map<String, Object> doctorLevelResultMap = doctorLevelService.getRepeat(doctorLevel);
		if (doctorLevelResultMap!=null && !doctorLevelResultMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("doctorLevel.name.is.conflict"));
		}
		if (doctorLevelService.insert(doctorLevel)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param id
	* @param doctorLevel
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年8月6日
	* @version 1.0
	* @description 修改医生职称（医生级别）
	*/
	@RequiresAuthentication(value = { "platform:configure:doctorLevel:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/doctor/level/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(@PathVariable("id") Integer id,@RequestBody @Validated({ BaseEntity.Update.class }) DoctorLevel  doctorLevel,BindingResult result){
		/*判断数据是否存在*/
		doctorLevel.setId(id);
		Map<String, Object> doctorLevelMap = doctorLevelService.getOne(doctorLevel);
		if (doctorLevelMap==null || doctorLevelMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 去重复
		Map<String, Object> doctorLevelRepeatMap = doctorLevelService.getRepeat(doctorLevel);
		if (null != doctorLevelRepeatMap && !doctorLevelRepeatMap.isEmpty()) {
			Integer doctorId=(Integer) doctorLevelRepeatMap.get("id");
			if (doctorId.intValue() != id.intValue()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("doctorLevel.name.is.conflict"));
			}
		}
		if (doctorLevelService.update(doctorLevel)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param doctorLevel
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月30日
	* @version 1.0
	* @description 医生职称（医生级别）列表
	*/
	@RequiresAuthentication(value = { "platform:configure:doctorLevel:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/doctor/levels" }, method = RequestMethod.GET)
	public JsonApi getDoctorLevelList(
			@Validated({ BaseEntity.SelectAll.class }) DoctorLevel  doctorLevel,BindingResult result){
		Page<?> page = PageHelper.startPage(doctorLevel.getPage(), doctorLevel.getPageSize());
		List<Map<String, Object>>  doctorLevelList=doctorLevelService.getList(doctorLevel);
		if (doctorLevelList!=null && !doctorLevelList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), doctorLevelList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
	
	
}
