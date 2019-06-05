package org.module.platform.configuration.controller;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.DoctorCategory;
import org.module.platform.configuration.service.DoctorCategoryService;
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
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月14日
 * @Version 
 * @Description 医生类别
 */
@RestController
public class DoctorCategoryController {

	@Autowired
	private DoctorCategoryService doctorCategoryService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param doctorCategory
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月30日
	* @version 1.0
	* @description 查询医生类别列表
	*/
	@RequiresAuthentication( value = { "platform:configure:doctorCategory:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/doctor/categorys" }, method = RequestMethod.GET)
	public JsonApi getDoctorCategoryList(@Validated({ BaseEntity.SelectAll.class }) DoctorCategory  doctorCategory,BindingResult result ){
		Page<?> page = PageHelper.startPage(doctorCategory.getPage(), doctorCategory.getPageSize());
		List<Map<String, Object>>  doctorCategoryList=doctorCategoryService.getList(doctorCategory);
		if (doctorCategoryList!=null && !doctorCategoryList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), doctorCategoryList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
