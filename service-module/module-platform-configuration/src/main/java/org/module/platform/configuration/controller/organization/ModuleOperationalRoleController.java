package org.module.platform.configuration.controller.organization;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.organization.ModuleOperationalRole;
import org.module.platform.configuration.service.organization.ModuleOperationalRoleService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年4月18日
 * @Version 
 * @Description 模块角色关联表
 */
@RestController
public class ModuleOperationalRoleController {
	@Autowired
	private ModuleOperationalRoleService moduleOperationalRoleService;
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param moduleOperationalRole
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 查询权限和模块关联列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:moduleOperationalRoles:list" })
	@RequestMapping(value = { "/module/operation/roles" }, method = RequestMethod.GET)
	public JsonApi getModuleOperationalRole(@Validated({ BaseEntity.SelectAll.class })ModuleOperationalRole moduleOperationalRole,BindingResult result) {
		Page<?> page = PageHelper.startPage(moduleOperationalRole.getPage(), moduleOperationalRole.getPageSize());
		List<Map<String, Object>> moduleOperationalRoleList = moduleOperationalRoleService.getList(moduleOperationalRole);
		if (moduleOperationalRoleList != null && !moduleOperationalRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), moduleOperationalRoleList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

}
