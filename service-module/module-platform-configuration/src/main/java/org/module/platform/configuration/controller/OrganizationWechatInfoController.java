package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.OrganizationWechatInfo;
import org.module.platform.configuration.service.OrganizationWechatInfoService;
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
 * @Description 机构微信公众号
 */
@RestController
public class OrganizationWechatInfoController {

	@Autowired
	private OrganizationWechatInfoService organizationWechatInfoService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationWechatInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月19日
	* @version 1.0
	* @description 查询机构微信公众号列表
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationWechatInfo:list" })
	@RequestMapping(value = { "/organization/wechat/info" }, method = RequestMethod.GET)
	public JsonApi organizationWechatInfoList(
			@Validated({ BaseEntity.SelectAll.class })  OrganizationWechatInfo organizationWechatInfo,BindingResult result) {
		Page<?> page = PageHelper.startPage(organizationWechatInfo.getPage(), organizationWechatInfo.getPageSize());
		List<Map<String, Object>> organizationWechatInfoList = organizationWechatInfoService.getList(organizationWechatInfo);
		if (organizationWechatInfoList != null && !organizationWechatInfoList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationWechatInfoList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationWechatInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月19日
	* @version 1.0
	* @description 新增机构微信公众号
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationWechatInfo:insert" })
	@RequestMapping(value = { "/organization/wechat/info" }, method = RequestMethod.POST)
	public JsonApi organizationWechatInfoInsert(
			@Validated({ BaseEntity.Insert.class })@RequestBody  OrganizationWechatInfo organizationWechatInfo,
			BindingResult result) {
		/* 判断重复 */
		Map<String, Object> wechatMap = organizationWechatInfoService.getRepeat(organizationWechatInfo);
		if (wechatMap != null && !wechatMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT);
		}
		organizationWechatInfo.setCreateDate(new Date());
		if (organizationWechatInfoService.insert(organizationWechatInfo) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param organizationWechatInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月19日
	* @version 1.0
	* @description 修改机构微信公众号
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationWechatInfo:update" })
	@RequestMapping(value = { "/organization/wechat/info/{id}" }, method = RequestMethod.PUT)
	public JsonApi organizationWechatInfoUpdate(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class }) @RequestBody OrganizationWechatInfo organizationWechatInfo,BindingResult result) {
		organizationWechatInfo.setId(id);
		//数据是否存在
		Map<String, Object> organizationWechatInfoOneMap = organizationWechatInfoService.getOne(organizationWechatInfo);
		if (MapUtils.isEmpty(organizationWechatInfoOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		//修改机构微信公众号
		if (organizationWechatInfoService.update(organizationWechatInfo) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param organizationWechatInfo
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年7月19日
	* @version 1.0
	* @description 机构微信公众号详情
	*/
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationWechatInfo:detail" })
	@RequestMapping(value = { "/organization/wechat/info/{id}" }, method = RequestMethod.GET)
	public JsonApi organizationWechatInfoDetail(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class })  OrganizationWechatInfo organizationWechatInfo,
			BindingResult resultd) {
		organizationWechatInfo.setId(id);
		//数据是否存在
		Map<String, Object> organizationWechatInfoOneMap = organizationWechatInfoService.getOne(organizationWechatInfo);
		if (MapUtils.isNotEmpty(organizationWechatInfoOneMap)) {
			return new JsonApi(ApiCode.OK,organizationWechatInfoOneMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
