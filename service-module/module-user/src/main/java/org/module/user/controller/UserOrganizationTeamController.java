package org.module.user.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.user.domain.UserOrganizationTeam;
import org.module.user.service.UserOrganizationTeamService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
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
 * @Date 2018年10月17日
 * @Version 
 * @Description 用户团队关系
 */
@RestController
public class UserOrganizationTeamController {
	@Resource
	private UserOrganizationTeamService userOrganizationTeamService;
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param userOrganizationTeam
	* @param result
	* @return 
	* @date 2018年10月17日
	* @version 1.0
	* @description 用户团队关系列表
	*/
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/user/organization/team/relations" }, method = RequestMethod.GET)
	public JsonApi getUserOrganizationTeamRelations(@Validated(BaseEntity.SelectAll.class) UserOrganizationTeam userOrganizationTeam, BindingResult result) {
		Page<?> page = PageHelper.startPage(userOrganizationTeam.getPage(), userOrganizationTeam.getPageSize());
		List<Map<String, Object>> usersTeamRelationsList = userOrganizationTeamService.getList(userOrganizationTeam);
		if (usersTeamRelationsList != null && !usersTeamRelationsList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), usersTeamRelationsList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
