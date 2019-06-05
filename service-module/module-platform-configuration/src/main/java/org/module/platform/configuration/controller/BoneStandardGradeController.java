package org.module.platform.configuration.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.BoneStandardGrade;
import org.module.platform.configuration.service.BoneStandardGradeService;
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

@RestController
public class BoneStandardGradeController {

	@Autowired
	private BoneStandardGradeService boneStandardGradeService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param file
	* @param request
	* @param rBoneStandardGrade
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return
	* @throws IOException 
	* @date 2018年6月15日
	* @version 1.0
	* @description 骨标准评级表新增
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:boneStandardGrade:insert" })
	@RequestMapping(value = { "/r/bone/standard/grade" }, method = RequestMethod.POST)
	public JsonApi rBoneStandardGradeInsert(
			@Validated({ BaseEntity.Insert.class }) @RequestBody BoneStandardGrade boneStandardGrade, BindingResult result){
		boneStandardGrade.setCreateDate(new Date());
		if (boneStandardGradeService.insert(boneStandardGrade) >0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param rBoneStandardGrade
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年6月15日
	* @version 1.0
	* @description 骨标准评级表列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:boneStandardGrade:list" })
	@RequestMapping(value = { "/r/bone/standard/grades" }, method = RequestMethod.GET)
	public JsonApi rBoneStandardGradeList(
			@Validated({ BaseEntity.SelectAll.class })  BoneStandardGrade boneStandardGrade, BindingResult result)  {
		Page<?> page = PageHelper.startPage(boneStandardGrade.getPage(), boneStandardGrade.getPageSize());
		List<Map<String, Object>> rBoneStandardGradeList = boneStandardGradeService.getList(boneStandardGrade);
		if (rBoneStandardGradeList !=null && !rBoneStandardGradeList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),rBoneStandardGradeList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param rBoneStandardGrade
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年6月15日
	* @version 1.0
	* @description 骨标准评级表详情
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:boneStandardGrade:detail" })
	@RequestMapping(value = { "/r/bone/standard/grade/{id}" }, method = RequestMethod.GET)
	public JsonApi rBoneStandardGradeDetail(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class })  BoneStandardGrade boneStandardGrade, BindingResult result)  {
		boneStandardGrade.setId(id);
		Map<String, Object> rBoneStandardGradeOneMap = boneStandardGradeService.getOne(boneStandardGrade);
		if (rBoneStandardGradeOneMap !=null && !rBoneStandardGradeOneMap.isEmpty() ) {
			return new JsonApi(ApiCode.OK,rBoneStandardGradeOneMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param rBoneStandardGrade
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年6月15日
	* @version 1.0
	* @description 骨标准评级表修改
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:boneStandardGrade:update" })
	@RequestMapping(value = { "/r/bone/standard/grade/{id}" }, method = RequestMethod.PUT)
	public JsonApi rBoneStandardGradeUpdate(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class }) @RequestBody  BoneStandardGrade boneStandardGrade, BindingResult result)  {
		boneStandardGrade.setId(id);
		Map<String, Object> rBoneStandardGradeOneMap = boneStandardGradeService.getOne(boneStandardGrade);
		if (MapUtils.isEmpty(rBoneStandardGradeOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if (boneStandardGradeService.update(boneStandardGrade) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param rBoneStandardGrade
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年6月15日
	* @version 1.0
	* @description 骨标准评级表删除
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:boneStandardGrade:delete" })
	@RequestMapping(value = { "/r/bone/standard/grade/{id}" }, method = RequestMethod.DELETE)
	public JsonApi rBoneStandardGradeDelete(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Delete.class })  BoneStandardGrade boneStandardGrade, BindingResult result)  {
		boneStandardGrade.setId(id);
		Map<String, Object> rBoneStandardGradeOne = boneStandardGradeService.getOne(boneStandardGrade);
		if (MapUtils.isEmpty(rBoneStandardGradeOne)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if (boneStandardGradeService.delete(boneStandardGrade) >0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
