package org.module.bone.age.controller;

import java.util.List;
import java.util.Map;

import org.module.bone.age.domain.BoneStandardGrade;
import org.module.bone.age.service.BoneStandardGradeService;
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
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年6月15日
 * @Version 
 * @Description R骨标准评级表
 */
@RestController
public class BoneStandardGradeController {

	@Autowired
	private BoneStandardGradeService boneStandardGradeService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param rBoneStandardGrade
	* @param result
	* @return 
	* @date 2018年6月15日
	* @version 1.0
	* @description 骨龄标准评级图列表
	*/
	@RequiresAuthentication(value = { "bone:age:r:bone:standard:grade:list" },level = Level.OPERATION)
	@RequestMapping(value = { "r/bone/standard/grades" }, method = RequestMethod.GET)
	public JsonApi getRBoneStandardGradeList(@Validated({ BaseEntity.SelectAll.class }) BoneStandardGrade boneStandardGrade,
			BindingResult result){
		Page<?> page = PageHelper.startPage(boneStandardGrade.getPage(), boneStandardGrade.getPageSize());
		List<Map<String, Object>>  rBoneStandardGradeList=boneStandardGradeService.getList(boneStandardGrade);
		if (rBoneStandardGradeList!=null && !rBoneStandardGradeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), rBoneStandardGradeList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
