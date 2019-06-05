package org.module.organization.permission.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalMenu;
import org.module.organization.permission.message.Prompt;
import org.module.organization.permission.service.OperationalMenuService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
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
public class OperationalMenuController {

	@Autowired
	private OperationalMenuService operationalMenuService;
	
	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param operationalMenu
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年5月21日
	 * @version 1.0
	 * @description 新增机构菜单
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/menu" }, method = RequestMethod.POST)
	public JsonApi insert(
			@Validated({ BaseEntity.Insert.class })@RequestBody OperationalMenu  operationalMenu,BindingResult result){
		/*设置时间*/
		operationalMenu.setCreateDate(new Date());
		/*判断是否重复*/
		Map<String, Object> operationalMenuMap = operationalMenuService.getRepeat(operationalMenu);
		if (operationalMenuMap!=null && !operationalMenuMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("operational.menu.code.is.conflict"));
		}
		if (operationalMenuService.insert(operationalMenu)>0) {
			Map<String, Object> MenuMap = new HashMap<>();
			MenuMap.put("id", operationalMenu.getId());
			return new JsonApi(ApiCode.OK,MenuMap);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param id
	 * @param operationalMenu
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年5月21日
	 * @version 1.0
	 * @description 修改机构菜单
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/menu/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })@RequestBody OperationalMenu  operationalMenu,BindingResult result){
		operationalMenu.setId(id);
		//判断是否存在
		Map<String, Object> operationalMenuOneMap = operationalMenuService.getOne(operationalMenu);
		if (operationalMenuOneMap==null || operationalMenuOneMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		/*判断是否重复*/
		if (null != operationalMenu.getCode()) {
			Map<String, Object> operationalMenuMap = operationalMenuService.getRepeat(operationalMenu);
			if (operationalMenuMap!=null && !operationalMenuMap.isEmpty()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("operational.menu.code.is.conflict"));
			}
		}
		if (operationalMenuService.update(operationalMenu)>0) {
			return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	
	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param id
	 * @param operationalMenu
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年5月21日
	 * @version 1.0
	 * @description 机构菜单详情
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/menu/{id}" }, method = RequestMethod.GET)
	public JsonApi getOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OperationalMenu  operationalMenu,BindingResult result){
		operationalMenu.setId(id);
		Map<String, Object> operationalOperationMap = operationalMenuService.getOne(operationalMenu);
		if (operationalOperationMap!=null && !operationalOperationMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,operationalOperationMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}


	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param operationalMenu
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年5月21日
	 * @version 1.0
	 * @description 机构菜单列表
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/menus" }, method = RequestMethod.GET)
	public JsonApi getList(
			@Validated({ BaseEntity.SelectAll.class }) OperationalMenu  operationalMenu,BindingResult result){
		
		Page<?> page = PageHelper.startPage(operationalMenu.getPage(), operationalMenu.getPageSize());
		List<Map<String, Object>> operationalMenuList = operationalMenuService.getList(operationalMenu);
		if (operationalMenuList != null && !operationalMenuList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),operationalMenuList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
}
