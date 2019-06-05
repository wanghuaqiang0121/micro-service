package org.module.common.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.common.domain.NameRemark;
import org.module.common.service.NameRemarkService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@RestController
public class NameRemarkController {
	
	@Resource
	private NameRemarkService  nameRemarkService;

	/**
	 * @author: ChenYan
	 * @date: 2019年2月19日
	 * @param id
	 * @param nameRemark
	 * @param result
	 * @return
	 * @description: 查询详情
	 */
	@RequiresAuthentication(authc = true,value={})
	@GetMapping(value = { "/name/remark/{id}" })
	public JsonApi getDetail(@PathVariable("id") Integer id, @Validated({ BaseEntity.SelectOne.class }) NameRemark nameRemark, BindingResult result) {
		nameRemark.setId(id);
		Map<String, Object> nameRemarkMap = nameRemarkService.getOne(nameRemark);
		if (nameRemarkMap != null ) {
			return new JsonApi(ApiCode.OK, nameRemarkMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * @author: ChenYan
	 * @date: 2019年2月19日
	 * @param nameRemark
	 * @param result
	 * @return
	 * @description: 查询列表
	 */
	@RequiresAuthentication(authc = true,value={})
	@GetMapping(value = { "/name/remarks" })
	public JsonApi getList(@Validated({ BaseEntity.SelectAll.class })  NameRemark nameRemark, BindingResult result) {
		Page<?> page = PageHelper.startPage(nameRemark.getPage(), nameRemark.getPageSize());
		List<Map<String, Object>> nameRemarkList = nameRemarkService.getList(nameRemark);
		if (nameRemarkList != null && !nameRemarkList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), nameRemarkList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
