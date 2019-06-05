package org.module.platform.configuration.controller;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.BoneScoreTable;
import org.module.platform.configuration.service.BoneScoreTableService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
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
 * @Date 2018年7月3日
 * @Version 
 * @Description 骨龄分值表
 */
@RestController
public class BoneScoreTableController {

	@Autowired
	private BoneScoreTableService boneScoreTableService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param rBoneScoreTable
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月3日
	* @version 1.0
	* @description 获取骨头类型列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:boneScoreTable:BoneType" })
	@RequestMapping(value = { "/r/bone/score/table/bone/types" }, method = RequestMethod.GET)
	public JsonApi rBoneScoreTableBoneTypeList(
			@Validated({ BoneScoreTable.getBoneType.class })  BoneScoreTable boneScoreTable, BindingResult result)  {
		Page<?> page = PageHelper.startPage(boneScoreTable.getPage(), boneScoreTable.getPageSize());
		List<Map<String, Object>> rBoneScoreTableList = boneScoreTableService.getBoneType(boneScoreTable);
		if (rBoneScoreTableList !=null && !rBoneScoreTableList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),rBoneScoreTableList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param rBoneScoreTable
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月3日
	* @version 1.0
	* @description 骨龄等级列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:boneScoreTable:BoneGrade" })
	@RequestMapping(value = { "/r/bone/score/table/bone/grades" }, method = RequestMethod.GET)
	public JsonApi rBoneScoreTableBoneGradeList(
			@Validated({ BoneScoreTable.getBoneGrade.class })  BoneScoreTable boneScoreTable, BindingResult result)  {
		Page<?> page = PageHelper.startPage(boneScoreTable.getPage(), boneScoreTable.getPageSize());
		List<Map<String, Object>> rBoneScoreTableList = boneScoreTableService.getBoneGrade(boneScoreTable);
		if (rBoneScoreTableList !=null && !rBoneScoreTableList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),rBoneScoreTableList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
