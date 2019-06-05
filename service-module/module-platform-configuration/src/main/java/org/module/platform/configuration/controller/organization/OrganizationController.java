package org.module.platform.configuration.controller.organization;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.Organization;
import org.module.platform.configuration.domain.OrganizationType;
import org.module.platform.configuration.global.OrganizationStatusCode;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.organization.OrganizationService;
import org.module.platform.configuration.service.organization.OrganizationTypeService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月15日
 * @Version
 * @Description 机构
 */
@RestController
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private OrganizationTypeService organizationTypeService;

	/**
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organization
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 添加机构
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:insert" })
	@RequestMapping(value = { "/organization" }, method = RequestMethod.POST)
	public JsonApi organizationInsert(@Validated({ BaseEntity.Insert.class }) @RequestBody Organization organization,BindingResult result) {
		// 判断机构类型是否存在
		OrganizationType organizationType = new OrganizationType();
		organizationType.setId(organization.getOrganizationTypeId());
		Map<String, Object> organizationTypeResultMap = organizationTypeService.getOne(organizationType);
		if (MapUtils.isEmpty(organizationTypeResultMap)) {
			return new JsonApi(ApiCode.FAIL, Prompt.bundle("organizationType.is.not.exists"));
		}
		// 判断要添加的数据是否存在
		Map<String, Object> organizationRepeatResultMap = organizationService.getRepeat(organization);
		if (!MapUtils.isEmpty(organizationRepeatResultMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.code.or.name.is.conflict"));
		}
		// 添加数据
		organization.setStatus(OrganizationStatusCode.Organization.ENABLE.getValue());
		organization.setCreateDate(new Date());
		if (organizationService.insert(organization) > 0) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", organization.getId());
			return new JsonApi(ApiCode.OK,map);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param id
	 * @param organization
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月23日
	 * @version 1.0
	 * @description 修改机构数据
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:update" })
	@RequestMapping(value = { "/organization/{id}" }, method = RequestMethod.PUT)
	public JsonApi organizationUpdate(@PathVariable("id") Integer id,
			@Validated({ BaseEntity.Update.class }) @RequestBody Organization organization, BindingResult result) {
		organization.setId(id);
		// 数据是否存在
		Map<String, Object> organizationOneMap = organizationService.getOne(organization);
		if (MapUtils.isEmpty(organizationOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 数据是否重复
		Map<String, Object> organizationRepeatMap = organizationService.getRepeat(organization);
		if (MapUtils.isNotEmpty(organizationRepeatMap)) {
			int organizationRepeatId = (Integer) organizationRepeatMap.get("id");
			if (id.intValue() != organizationRepeatId) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.code.or.name.is.conflict"));
			}
		}
		// 修改数据
		if (organizationService.update(organization) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param id
	 * @param organization
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月23日
	 * @version 1.0
	 * @description 查询机构详情
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:detail" })
	@RequestMapping(value = { "/organization/{id}" }, method = RequestMethod.GET)
	public JsonApi organizationDetail(@PathVariable("id") Integer id,
			@Validated({ BaseEntity.SelectOne.class }) Organization organization, BindingResult result) {
		organization.setId(id);
		Map<String, Object> organizationOne = organizationService.getOne(organization);
		if (MapUtils.isNotEmpty(organizationOne)) {
			return new JsonApi(ApiCode.OK, organizationOne);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param organization
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月23日
	 * @version 1.0
	 * @description 查询机构列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:list" })
	@RequestMapping(value = { "/organization" }, method = RequestMethod.GET)
	public JsonApi organizationList(@Validated({ BaseEntity.SelectAll.class }) Organization organization) {
		Page<?> page = PageHelper.startPage(organization.getPage(), organization.getPageSize());
		List<Map<String, Object>> organizationList = organizationService.getList(organization);
		if (organizationList != null && !organizationList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	* @author <font color="green"><b>Chen.Yan</b></font>
	* @param id
	* @param organization
	* @return 
	* @date 2019年4月9日
	* @version 1.0
	* @description 查询当前机构团队
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:get-teams" },authc=true)
	@RequestMapping(value = { "/organization/team/{id}" }, method = RequestMethod.GET)
	public JsonApi getTeams(@PathVariable("id") Integer id,@Validated({ BaseEntity.SelectAll.class }) Organization organization) {
		organization.setId(id);
		Page<?> page = PageHelper.startPage(organization.getPage(), organization.getPageSize());
		List<Map<String, Object>> organizationList = organizationService.getTeams(organization);
		if (organizationList != null && !organizationList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param organization
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月16日
	 * @version 1.0
	 * @description 新增机构默认新增管理员
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organization:manager:insert" })
	@RequestMapping(value = { "/organization/manager" }, method = RequestMethod.POST)
	@Transactional
	public JsonApi organizationManagerInsert(@RequestBody Organization organization,BindingResult result) {/*
		// 判断机构规模是否存在
		OrganizationScale organizationScale = new OrganizationScale();
		organizationScale.setId(organization.getOrganizationScaleId());
		Map<String, Object> organizationScaleResultMap = organizationScaleService.getOne(organizationScale);
		if (MapUtils.isEmpty(organizationScaleResultMap)) {
			return new JsonApi(ApiCode.FAIL).setMsg( Prompt.bundle("organizationScale.is.not.exists"));
		}
		// 判断机构类型是否存在
		OrganizationType organizationType = new OrganizationType();
		organizationType.setId(organization.getOrganizationTypeId());
		Map<String, Object> organizationTypeResultMap = organizationTypeService.getOne(organizationType);
		if (MapUtils.isEmpty(organizationTypeResultMap)) {
			return new JsonApi(ApiCode.FAIL).setMsg( Prompt.bundle("organizationType.is.not.exists"));
		}
		// 判断机构是否存在
		Map<String, Object> organizationRepeatResultMap = organizationService.getRepeat(organization);
		if (!MapUtils.isEmpty(organizationRepeatResultMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.code.is.conflict"));
		}
		 设置默认值 
		OrganizationUser organizationUser = organization.getOrganizationUser();
		String phone = organizationUser.getPhone();
		String password = phone.substring(phone.length() - 6);
		organizationUser.setPassword(MD5Util.getInstance().getMD5Code(password));
		organizationUser.setCreateDate(new Date());
		organizationUser.setInitPassword(true);
		organizationUser.setPhoneStatus(OrganizationUserStatusCode.OrganizationUser.PHONESTATUSUNAUTH.getValue());
		organizationUser.setStatus(OrganizationUserStatusCode.OrganizationUser.ENABLE.getValue());
		 检查机构用户数据是否重复 
		检查数据是否重复
		// 电话号码不重复
		OrganizationUser  organizationUserByPhone = new OrganizationUser();
		organizationUserByPhone.setPhone(organizationUser.getPhone());
		Map<String, Object> organizationUserMap=organizationUserService.getRepeat(organizationUserByPhone);
		if (organizationUserMap!=null &&organizationUserMap.size()>0) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organizationUser.phone.is.exists"));
		}
		// 账号不重复
		OrganizationUser  organizationUserByAccount = new OrganizationUser();
		organizationUserByAccount.setAccount(organizationUser.getAccount());
		Map<String, Object> organizationUserByAccountMap=organizationUserService.getRepeat(organizationUserByAccount);
		if (organizationUserByAccountMap!=null &&organizationUserByAccountMap.size()>0) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organizationUser.account.is.exists"));
		}
		// 添加数据
		organization.setStatus(OrganizationStatusCode.Organization.ENABLE.getValue());
		organization.setCreateDate(new Date());
		// 获取传入的机构用户证件列表
		List<OrganizationUserCertificate> organizationUserCertificates = organization.getOrganizationUserCertificates();
		// 添加证件
		// 指定机构用户id
		for (OrganizationUserCertificate organizationUserCertificate : organizationUserCertificates) {
			// 判断证件是否重复
			if (MapUtils.isNotEmpty(organizationUserCertificateService.getRepeat(organizationUserCertificate))) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organizationUser.certificates.is.exists"));
			}
			organizationUserCertificate.setCreateDate(new Date());
			if (String.valueOf(OrganizationUserCertificateCode.CertificateTypeCode.IDCARD.getValue()).equals(organizationUserCertificate.getCertificateType())) {
				organizationUser.setIdCard(organizationUserCertificate.getCertificateNumber());
			}
		}
		DoctorInfo doctorInfo = organization.getDoctorInfo();
		if (null != doctorInfo) {
			 检查医生信息是否重复 
			String certification = doctorInfo.getCertification();
			if (null != certification && !"".equals(certification)) {
				Map<String, Object> doctorInfoMap=doctorInfoService.getRepeat(doctorInfo);
				if (doctorInfoMap!=null &&doctorInfoMap.size()>0) {
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("doctorInfo.certification.is.not.exists"));
				}
			}
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("doctorInfo.certification.is.not.null"));
		}
		 新增机构 
		if (organizationService.insert(organization) > 0) {
			
			 新增机构用户 
			if (organizationUserService.insert(organizationUser) > 0) {
				for (OrganizationUserCertificate organizationUserCertificate : organizationUserCertificates) {
					organizationUserCertificate.setOrganizationUserId(organizationUser.getId());
				}
				// 批量添加机构用户证件
				if (organizationUserCertificateService.batchInsert(organizationUserCertificates) == organizationUserCertificates.size()) {
					 设置关联表信息 
					DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty = new DoctorOrganizationDepartmentDuty();
					doctorOrganizationDepartmentDuty.setOrganizationUserId(organizationUser.getId());
					doctorOrganizationDepartmentDuty.setOrganizationId(organization.getId());
					doctorOrganizationDepartmentDuty.setIsManager(true);
					 新增关联表信息 
					if (doctorOrganizationDepartmentDutyService.insert(doctorOrganizationDepartmentDuty) > 0) {
					 医生信息是否重复 
				  	if (null != doctorInfo) {
						doctorInfo.setCreateDate(new Date());
						 设置机构用户id 
						doctorInfo.setOrganizationUserId(organizationUser.getId());
						 新增医生表 
						if (doctorInfoService.insert(doctorInfo) > 0) {
								return new JsonApi(ApiCode.OK);
							}
							throw new RuntimeException();
						}
						return new JsonApi(ApiCode.OK);
						}
						throw new RuntimeException();
					}
					throw new RuntimeException();
				}
				throw new RuntimeException();
			}
		return new JsonApi(ApiCode.FAIL);
	*/
		return new JsonApi(ApiCode.FAIL);
		}
}
