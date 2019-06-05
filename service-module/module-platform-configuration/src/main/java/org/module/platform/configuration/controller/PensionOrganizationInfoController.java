package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.PensionOrganizationInfo;
import org.module.platform.configuration.service.PensionOrganizationInfoService;
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
 * @Date 2018年8月14日
 * @Version 
 * @Description 养老机构扩展信息
 */
@RestController
public class PensionOrganizationInfoController {

	@Autowired
	private PensionOrganizationInfoService pensionOrganizationInfoService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param pensionOrganizationInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月19日
	* @version 1.0
	* @description 查询养老机构扩展信息列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:pensionOrganizationInfo:list" })
	@RequestMapping(value = { "/pension/organization/info" }, method = RequestMethod.GET)
	public JsonApi pensionOrganizationInfoList(
			@Validated({ BaseEntity.SelectAll.class })  PensionOrganizationInfo pensionOrganizationInfo,BindingResult result) {
		Page<?> page = PageHelper.startPage(pensionOrganizationInfo.getPage(), pensionOrganizationInfo.getPageSize());
		List<Map<String, Object>> pensionOrganizationInfoList = pensionOrganizationInfoService.getList(pensionOrganizationInfo);
		if (pensionOrganizationInfoList != null && !pensionOrganizationInfoList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), pensionOrganizationInfoList));
		}

		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param pensionOrganizationInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月19日
	* @version 1.0
	* @description 新增养老机构扩展信息
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:pensionOrganizationInfo:insert" })
	@RequestMapping(value = { "/pension/organization/info" }, method = RequestMethod.POST)
	public JsonApi pensionOrganizationInfoInsert(
			@Validated({ BaseEntity.Insert.class })@RequestBody  PensionOrganizationInfo pensionOrganizationInfo,
			BindingResult result) {
		pensionOrganizationInfo.setCreateDate(new Date());
		if (pensionOrganizationInfoService.insert(pensionOrganizationInfo) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param pensionOrganizationInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月19日
	* @version 1.0
	* @description 修改养老机构扩展信息
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:pensionOrganizationInfo:update" })
	@RequestMapping(value = { "/pension/organization/info/{id}" }, method = RequestMethod.PUT)
	public JsonApi pensionOrganizationInfoUpdate(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody  PensionOrganizationInfo pensionOrganizationInfo,BindingResult result) {
		pensionOrganizationInfo.setId(id);
		Map<String, Object> pensionOrganizationInfoOne = pensionOrganizationInfoService.getOne(pensionOrganizationInfo);
		if (MapUtils.isEmpty(pensionOrganizationInfoOne)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if (pensionOrganizationInfoService.update(pensionOrganizationInfo) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param pensionOrganizationInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月19日
	* @version 1.0
	* @description 查询养老机构扩展信息详情
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:pensionOrganizationInfo:detail" })
	@RequestMapping(value = { "/pension/organization/info/{id}" }, method = RequestMethod.GET)
	public JsonApi pensionOrganizationInfoDetail(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class })  PensionOrganizationInfo pensionOrganizationInfo,
			BindingResult result) {
		pensionOrganizationInfo.setId(id);
		//数据是否存在
		Map<String, Object> pensionOrganizationInfoOne = pensionOrganizationInfoService.getOne(pensionOrganizationInfo);
		if (MapUtils.isNotEmpty(pensionOrganizationInfoOne)) {
			return new JsonApi(ApiCode.OK,pensionOrganizationInfoOne);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
