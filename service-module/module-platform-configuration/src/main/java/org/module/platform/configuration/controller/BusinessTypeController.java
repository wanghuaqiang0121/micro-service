package org.module.platform.configuration.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.platform.configuration.domain.BusinessType;
import org.module.platform.configuration.service.BusinessTypeService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年10月18日
 * @Version 
 * @Description 业务类型
 */
@RestController
public class BusinessTypeController {

	@Resource
	private BusinessTypeService  businessTypeService;
	
	@RequiresAuthentication(authc = true,value = { "" },level=Level.OPERATION)
	@RequestMapping(value = { "/business/types" }, method = RequestMethod.GET)
	public JsonApi getList( @Validated({ BaseEntity.SelectAll.class }) BusinessType  businessType,BindingResult result){
		Page<?> page = PageHelper.startPage(businessType.getPage(), businessType.getPageSize());
		List<Map<String, Object>>  businessTypeList=businessTypeService.getList(businessType);
		if (businessTypeList!=null && !businessTypeList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), businessTypeList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
