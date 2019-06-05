package org.module.organization.configure.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.organization.configure.domain.TeamOrganizationServicePackage;
import org.module.organization.configure.global.ServiceStatusCode;
import org.module.organization.configure.global.TeamStatusCode;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.TeamOrganizationServicePackageService;
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
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月19日
 * @Version 
 * @Description 团队机构服务包关联表
 */
@RestController
public class TeamOrganizationServicePackageController {

	@Autowired
	private TeamOrganizationServicePackageService teamOrganizationServicePackageService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 添加团队机构服务包关联表
	*/
	@RequiresAuthentication( value = { "organization:configure:teamOrganizationServicePackage:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/team/organization/service/package" }, method = RequestMethod.POST)
	public JsonApi addTeamOrganizationServicePackage(
			@Validated(BaseEntity.Insert.class) @RequestBody TeamOrganizationServicePackage teamOrganizationServicePackage,BindingResult result){
		// 查询数据是否存在重复
		Map<String, Object> teamOrganizationServicePackageMap = teamOrganizationServicePackageService.getRepeat(teamOrganizationServicePackage);
		if (teamOrganizationServicePackageMap!=null  && !teamOrganizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("team.organization.service.package.is.exists"));
		}
		// 设置状态 启用 ：0
		teamOrganizationServicePackage.setStatus(TeamStatusCode.OrganizationTeam.ENABLE.getValue());
		// 设置创建时间
		teamOrganizationServicePackage.setCreateDate(new Date());
		// 添加机构服务包和服务关联
		if (teamOrganizationServicePackageService.insert(teamOrganizationServicePackage) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param teamOrganizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 修改团队机构服务包关联
	*/
	@RequiresAuthentication( value = { "organization:configure:teamOrganizationServicePackage:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/team/organization/service/package/{id}" }, method = RequestMethod.PUT)
	public JsonApi updateTeamOrganizationServicePackage(@PathVariable("id") Integer id,
			@Validated(BaseEntity.Update.class) @RequestBody TeamOrganizationServicePackage teamOrganizationServicePackage,BindingResult result){
		// 设置id
		teamOrganizationServicePackage.setId(id);
		// 设置状态为空
		teamOrganizationServicePackage.setStatus(null);
		// 查询数据是否存在
		Map<String, Object> teamOrganizationServicePackageMap = teamOrganizationServicePackageService.getOne(teamOrganizationServicePackage);
		if (teamOrganizationServicePackageMap==null || teamOrganizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 修改团队机构服务包关联
		if (teamOrganizationServicePackageService.update(teamOrganizationServicePackage) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param teamOrganizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 团队服务包上架
	*/
	@RequiresAuthentication( value = { "organization:configure:teamOrganizationServicePackage:onshelves" },level=Level.OPERATION)
	@RequestMapping(value = { "/team/organization/service/package/onshelves/{id}" }, method = RequestMethod.PUT)
	public JsonApi onshelvesTeamOrganizationServicePackage(@PathVariable("id") Integer id,
			@Validated(TeamOrganizationServicePackage.Onshelves.class) TeamOrganizationServicePackage teamOrganizationServicePackage,BindingResult result){
		TeamOrganizationServicePackage onshelvesPackage = new TeamOrganizationServicePackage();
		// 设置id
		onshelvesPackage.setId(id);
		// 查询数据是否存在
		Map<String, Object> teamOrganizationServicePackageMap = teamOrganizationServicePackageService.getOne(teamOrganizationServicePackage);
		if (teamOrganizationServicePackageMap==null || teamOrganizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 如果已上架
		if ((Integer) teamOrganizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.THESHELVES.getValue()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("team.organization.service.package.shelves"));
		}
		// 设置状态
		onshelvesPackage.setStatus(ServiceStatusCode.OrganizationServicePackage.THESHELVES.getValue());
		// 修改团队机构服务包关联（状态   上架）
		if (teamOrganizationServicePackageService.update(onshelvesPackage) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param teamOrganizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 团队服务包下架
	*/
	@RequiresAuthentication( value = { "organization:configure:teamOrganizationServicePackage:offshelves" },level=Level.OPERATION)
	@RequestMapping(value = { "/team/organization/service/package/offshelves/{id}" }, method = RequestMethod.PUT)
	public JsonApi offshelvesTeamOrganizationServicePackage(@PathVariable("id") Integer id,
			@Validated(TeamOrganizationServicePackage.Offshelves.class) TeamOrganizationServicePackage teamOrganizationServicePackage,BindingResult result){
		TeamOrganizationServicePackage offshelvesPackage = new TeamOrganizationServicePackage();
		// 设置id
		offshelvesPackage.setId(id);
		// 查询数据是否存在
		Map<String, Object> teamOrganizationServicePackageOneResult = teamOrganizationServicePackageService.getOne(offshelvesPackage);
		if (MapUtils.isEmpty(teamOrganizationServicePackageOneResult)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 如果已下架
		if ((Integer) teamOrganizationServicePackageOneResult.get("status") == ServiceStatusCode.OrganizationServicePackage.OFFTHESHELF.getValue()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("team.organization.service.package.offshelves"));
		}
		// 设置状态 下架
		offshelvesPackage.setStatus(ServiceStatusCode.OrganizationServicePackage.OFFTHESHELF.getValue());
		// 修改团队机构服务包关联（状态   下架）
		if (teamOrganizationServicePackageService.update(offshelvesPackage) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param teamOrganizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除团队机构服务包关联
	*/
	@RequiresAuthentication( value = { "organization:configure:teamOrganizationServicePackage:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/team/{doctorTeamId}/organization/service/package/{organizationServicePackageId}" }, method = RequestMethod.DELETE)
	public JsonApi deleteTeamOrganizationServicePackage(
			@PathVariable("doctorTeamId") Integer doctorTeamId,@PathVariable("organizationServicePackageId") Integer organizationServicePackageId,
			@Validated(BaseEntity.Delete.class) TeamOrganizationServicePackage teamOrganizationServicePackage,BindingResult result){
		// 设置团队id
		teamOrganizationServicePackage.setDoctorTeamId(doctorTeamId);
		// 设置机构服务包id
		teamOrganizationServicePackage.setOrganizationServicePackageId(organizationServicePackageId);
		// 删除团队机构服务包关联
		if (teamOrganizationServicePackageService.delete(teamOrganizationServicePackage) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorTeamId
	* @param teamOrganizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 查询团队机构服务包列表
	*/
	@RequiresAuthentication( value = { "organization:configure:teamOrganizationServicePackage:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/team/organization/service/packages" }, method = RequestMethod.GET)
	public JsonApi teamOrganizationServicePackages(
			@Validated(BaseEntity.SelectAll.class) TeamOrganizationServicePackage teamOrganizationServicePackage,BindingResult result){
		// 分页设置
		Page<?> page = PageHelper.startPage(teamOrganizationServicePackage.getPage(), teamOrganizationServicePackage.getPageSize());
		// 查询团队机构服务包列表
		List<Map<String, Object>> teamOrganizationServicePackagesList = teamOrganizationServicePackageService.getList(teamOrganizationServicePackage);
		if (teamOrganizationServicePackagesList!=null && !teamOrganizationServicePackagesList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(), teamOrganizationServicePackagesList));
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 查询本机构服务包对应团队授权操作
	*/
	@RequiresAuthentication( value = { "organization:configure:teamOrganizationServicePackage:teamsAuthorize" },level=Level.OPERATION)
	@RequestMapping(value = { "/team/organization/service/packages/team/authorize" }, method = RequestMethod.GET)
	public JsonApi organizationServicePackageTeamAuthorize(
			@Validated(TeamOrganizationServicePackage.OrganizationServicePackageTeamAuthorize.class) TeamOrganizationServicePackage teamOrganizationServicePackage,BindingResult result){
		// 分页设置
		Page<?> page = PageHelper.startPage(teamOrganizationServicePackage.getPage(), teamOrganizationServicePackage.getPageSize());
		// 查询机构服务包对应团队授权操作
		List<Map<String, Object>> teamAuthorizeList = teamOrganizationServicePackageService.getTeamAuthorizeList(teamOrganizationServicePackage);
		if (teamAuthorizeList!=null && !teamAuthorizeList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(), teamAuthorizeList));
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	
}
