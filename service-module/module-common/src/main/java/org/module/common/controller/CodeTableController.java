package org.module.common.controller;

import java.util.List;
import java.util.Map;

import org.module.common.domain.CodeTable;
import org.module.common.service.CodeTableService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@RestController
public class CodeTableController {

	@Autowired
	private CodeTableService codeTableService;
	
	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param id
	 * @param codeTable
	 * @param bindingResult
	 * @return
	 * @date 2018年3月28日
	 * @version 1.0
	 * @description 码表详情
	 */
	@RequiresAuthentication(authc = true, value = { "common:code:table:detail" })
	@RequestMapping(value = { "/code/table/{id}" }, method = RequestMethod.GET)
	public JsonApi getCodeTableDetail(@PathVariable("id") Integer id,
			@Validated(BaseEntity.SelectOne.class) CodeTable codeTable, BindingResult result) {
		codeTable.setId(id);
		Map<String, Object> codeTableMap = codeTableService.getOne(codeTable);
		if (codeTableMap!=null && !codeTableMap.isEmpty()) {
			return new JsonApi(ApiCode.OK, codeTableMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param codeTable
	* @param bindingResult
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 查询码表列表
	*/
	@RequiresAuthentication(authc = true, value = { "common:code:table:list" })
	@RequestMapping(value = { "/code/table" }, method = RequestMethod.GET)
	public JsonApi getCodeTableList(@Validated(BaseEntity.SelectAll.class) CodeTable codeTable,
			BindingResult result) {
		Page<?> page = PageHelper.startPage(codeTable.getPage(), codeTable.getPageSize());
		List<Map<String, Object>> codeTableList = codeTableService.getList(codeTable);
		if (codeTableList != null && !codeTableList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), codeTableList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
