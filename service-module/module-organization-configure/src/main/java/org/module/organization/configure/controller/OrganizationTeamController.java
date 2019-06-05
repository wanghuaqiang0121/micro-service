package org.module.organization.configure.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationTeam;
import org.module.organization.configure.domain.OrganizationUserTeam;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationTeamService;
import org.module.organization.configure.service.doctor.OrganizationUserTeamService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月20日
 * @Version 
 * @Description 机构团队表
 */
@RestController
public class OrganizationTeamController {
	@Autowired
	private OrganizationTeamService organizationTeamService;
	
	@Autowired
	private OrganizationUserTeamService organizationUserTeamService;
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationTeam
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 验证团队名称
	 * // TODO 待添加到新项目
	 */
	@RequiresAuthentication(value = { "organization:configure:organizationTeam:validTeamNameRepeat" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/team/valid/team/name" }, method = RequestMethod.GET)
	public JsonApi validTeamNameRepeat(
			@Validated({ OrganizationTeam.validTeamNameRepeat.class }) OrganizationTeam  organizationTeam,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId
			){
		Map<String, Object> resultMap = new HashMap<>();
		/*设置机构*/
		organizationTeam.setOrganizationId(organizationId);
		/*判断机构团队是否重复*/
		Map<String, Object> organizationTeamMap = organizationTeamService.getRepeat(organizationTeam);
		/* 修改时传入团队id*/
		if (organizationTeam.getId() != null) {
			int organizationTeamId = organizationTeam.getId();
			if (organizationTeamMap!=null && (Integer)organizationTeamMap.get("id") != organizationTeamId) {
				resultMap.put("isRepeat",true);
				return new JsonApi(ApiCode.OK,resultMap).setMsg(Prompt.bundle("organization.team.organization.name.is.exists"));
			}
		}else{/* 添加时不传团队id */
			if (organizationTeamMap != null) {
				resultMap.put("isRepeat",false);
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}
		resultMap.put("isRepeat",false);
		return new JsonApi(ApiCode.OK,resultMap);
	}
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationTeam
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 新建机构团队
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationTeam:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/team" }, method = RequestMethod.POST)
	@Transactional
	public JsonApi insert(
			@RequestBody @Validated({ BaseEntity.Insert.class }) OrganizationTeam  organizationTeam,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId
			){
		/*设置时间*/
		organizationTeam.setCreateDate(new Date());
		/*设置机构*/
		organizationTeam.setOrganizationId(organizationId);
		
		/*判断机构团队是否重复*/
		Map<String, Object> organizationTeamMap = organizationTeamService.getRepeat(organizationTeam);
		if (organizationTeamMap!=null && !organizationTeamMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.team.organization.name.is.exists"));
		}
		if (organizationTeamService.insert(organizationTeam)>0) {
			OrganizationUserTeam doctorDoctorTeam = new OrganizationUserTeam();
			doctorDoctorTeam.setOrganizationTeamId(organizationTeam.getId());
			doctorDoctorTeam.setOrganizationUserId(organizationTeam.getOrganizationUserId());
			doctorDoctorTeam.setIsManager(true);
			doctorDoctorTeam.setCreateDate(new Date());
			if (organizationUserTeamService.insert(doctorDoctorTeam)>0) {
				return new JsonApi(ApiCode.OK);
			}
		
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param organizationTeam
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 修改机构团队
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationTeam:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/team/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(@PathVariable("id") Integer id,@RequestBody @Validated({ BaseEntity.Update.class })  OrganizationTeam  organizationTeam,BindingResult result
			){
		/*判断数据是否存在*/
		organizationTeam.setId(id);
		Map<String, Object> organizationTeamMap = organizationTeamService.getOne(organizationTeam);
		if (organizationTeamMap==null || organizationTeamMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if (organizationTeamService.update(organizationTeam)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param organizationTeam
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 查询机构团队详情
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationTeam:detail" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/team/{id}" }, method = RequestMethod.GET)
	public JsonApi getDepartmentDetail(@PathVariable("id") Integer id,@Validated({ BaseEntity.SelectOne.class }) OrganizationTeam  organizationTeam,BindingResult result){
		/*设置主键*/
		organizationTeam.setId(id);
		Map<String, Object> organizationTeamMap = organizationTeamService.getOne(organizationTeam);
		if (organizationTeamMap!=null && !organizationTeamMap.isEmpty()) {
			// 根据机构id 查询机构微信的verType
			Integer organizationId = (Integer) organizationTeamMap.get("organizationId");
			organizationTeam.setOrganizationId(organizationId);
			Map<String, Object> organizationVerTypeMap = organizationTeamService.getOrganizationVerType(organizationTeam);
			String verType = (organizationVerTypeMap != null && organizationVerTypeMap.get("verType") != null) ? organizationVerTypeMap.get("verType").toString() : "";
			// 查询 微信二维码配置信息
			List<Map<String, Object>> list = organizationTeamService.getwechatQrCode(organizationTeam);
			for (Map<String, Object> map : list) {
				map.put("url", map.get("url").toString()+verType);
			}
			organizationTeamMap.put("wechatUrls", list);
			return new JsonApi(ApiCode.OK,organizationTeamMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationTeam
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @param result
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 查询机构团队列表
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationTeam:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/teams" }, method = RequestMethod.GET)
	public JsonApi getDepartmentList(@Validated({ BaseEntity.SelectAll.class }) OrganizationTeam  organizationTeam,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId ){
		organizationTeam.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(organizationTeam.getPage(), organizationTeam.getPageSize());
		List<Map<String, Object>>  organizationTeamList=organizationTeamService.getList(organizationTeam);
		if (organizationTeamList!=null && !organizationTeamList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationTeamList));	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}

}
