package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.OrganizationGrade;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.OrganizationGradeService;
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
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月14日
 * @Version 
 * @Description 机构等级
 */
@RestController
public class OrganizationGradeController {
	
	@Resource
	private OrganizationGradeService organizationGradeService;
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationGrade
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年7月19日
	 * @version 1.0
	 * @description 新增机构等级
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationGrade:insert" })
	@RequestMapping(value = { "/organization/grade" }, method = RequestMethod.POST)
	public JsonApi insertOrganizationGrade(
			@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationGrade organizationGrade, BindingResult result){
		organizationGrade.setCreateDate(new Date());
		Map<String,Object>  organizationGradeRepeatMap=organizationGradeService.getRepeat(organizationGrade);
		if (MapUtils.isNotEmpty(organizationGradeRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.grade.name.is.exists"));
		}
		if (organizationGradeService.insert(organizationGrade)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param organizationGrade
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年7月19日
	 * @version 1.0
	 * @description 修改机构等级
	 */
	@RequiresAuthentication(level=Level.OPERATION,value={"platform:configure:organizationGrade:update" })
	@RequestMapping(value = { "/organization/grade/{id}" }, method = RequestMethod.PUT)
	public JsonApi updateOrganizationGrad(
			@PathVariable("id")Integer id,
			@Validated({BaseEntity.Update.class})@RequestBody OrganizationGrade organizationGrade,BindingResult result){
		// 判断数据是否存在
		organizationGrade.setId(id);
		Map<String, Object> organizationGradeDetailMap=organizationGradeService.getDetail(organizationGrade);
		if (MapUtils.isEmpty(organizationGradeDetailMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 数据存在
		if (null!=organizationGrade.getName()) {
			// 判断重复
			Map<String, Object> organizationGradeRepeatMap = organizationGradeService.getRepeat(organizationGrade);
			if (MapUtils.isNotEmpty(organizationGradeRepeatMap)) {
				if (!organizationGradeDetailMap.get("id").equals(organizationGradeRepeatMap.get("id"))) {
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.grade.name.is.exists"));
				}
			}
		}
		if (organizationGradeService.update(organizationGrade)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationGrade
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年7月19日
	 * @version 1.0
	 * @description 查询机构等级列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationGrade:list" })
	@RequestMapping(value = { "/organization/grades" }, method = RequestMethod.GET)
	public JsonApi getOrganizationGradeList(
			@Validated({ BaseEntity.SelectAll.class })  OrganizationGrade organizationGrade, BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationGrade.getPage(), organizationGrade.getPageSize());		
		List<Map<String, Object>> organizationGradeList = organizationGradeService.getList(organizationGrade);
		if (organizationGradeList !=null && !organizationGradeList.isEmpty() ) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),organizationGradeList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

}
