package org.module.organization.permission.controller;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalPermissionMenu;
import org.module.organization.permission.service.OperationalPermissionMenuService;
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
public class OperationalPermissionMenuController {
	@Autowired
	private OperationalPermissionMenuService operationalPermissionMenuService;
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param operationalPermissionMenu
	 * @param result
	 * @return
	 * @date 2018年5月2日
	 * @version 1.0
	 * @description 新增权限菜单关联
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permission/menu" }, method = RequestMethod.POST)
	public JsonApi insert(
			@RequestBody @Validated({ BaseEntity.Insert.class }) OperationalPermissionMenu  operationalPermissionMenu,
			BindingResult result
			){
		Map<String, Object> operationalPermissionMenuMap = operationalPermissionMenuService.getRepeat(operationalPermissionMenu);
		if(operationalPermissionMenuMap!=null && !operationalPermissionMenuMap.isEmpty()){
			return new JsonApi(ApiCode.CONFLICT);
		}
		/*新增*/
		if (operationalPermissionMenuService.insert(operationalPermissionMenu)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param operationalPermissionId
	 * @param operationalMenuId
	 * @param operationalPermissionMenu
	 * @param result
	 * @return
	 * @date 2018年5月2日
	 * @version 1.0
	 * @description 删除权限菜单关联
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permission/{operationalPermissionId}/menu/{operationalMenuId}" }, method = RequestMethod.DELETE)
	public JsonApi delete(
			@PathVariable("operationalPermissionId") Integer operationalPermissionId,
			@PathVariable("operationalMenuId") Integer operationalMenuId,@Validated({ BaseEntity.Delete.class }) OperationalPermissionMenu  operationalPermissionMenu,
			BindingResult result
			){
		operationalPermissionMenu.setOperationalPermissionId(operationalPermissionId);
		operationalPermissionMenu.setOperationalMenuId(operationalMenuId);
		/*删除*/
		if (operationalPermissionMenuService.delete(operationalPermissionMenu)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	* @author <font color="green"><b>Chen.Yan</b></font>
	* @param operationalPermissionMenu
	* @param result
	* @return 
	* @date 2018年5月2日
	* @version 1.0
	* @description 查询该权限拥有和未拥有的菜单
	 */
	@RequiresAuthentication( value = { "role:permission:manager" })
	@RequestMapping(value = { "/operational/permission/menus" }, method = RequestMethod.GET)
	public JsonApi getPermissionMenuIsChoose(
			@Validated({ OperationalPermissionMenu.GetPermissionMenuIsChoose.class }) OperationalPermissionMenu  operationalPermissionMenu,
			BindingResult result){
		Page<?> page = PageHelper.startPage(operationalPermissionMenu.getPage(), operationalPermissionMenu.getPageSize());
		List<Map<String, Object>> operationalPermissionMenuList=operationalPermissionMenuService.getPermissionMenuIsChoose(operationalPermissionMenu);
		if (operationalPermissionMenuList!=null && !operationalPermissionMenuList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), operationalPermissionMenuList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	
	}

}
