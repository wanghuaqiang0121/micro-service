package org.module.platform.configuration.controller.organization;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.organization.DoctorOrganizationDepartmentDuty;
import org.module.platform.configuration.domain.organization.OrganizationPersonType;
import org.module.platform.configuration.domain.organization.OrganizationUser;
import org.module.platform.configuration.domain.organization.OrganizationUserCertificate;
import org.module.platform.configuration.global.BaseGlobal;
import org.module.platform.configuration.global.OrganizationStatusCode;
import org.module.platform.configuration.global.OrganizationUserCertificateCode;
import org.module.platform.configuration.global.OrganizationUserStatusCode;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.organization.DoctorOrganizationDepartmentDutyService;
import org.module.platform.configuration.service.organization.OrganizationPersonTypeService;
import org.module.platform.configuration.service.organization.OrganizationUserCertificateService;
import org.module.platform.configuration.service.organization.OrganizationUserService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.service.core.entity.BaseEntity.Update;
import org.service.tools.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationUserController {
	
	@Autowired
	private OrganizationUserService organizationUserService;
	
	@Autowired
	private OrganizationPersonTypeService organizationPersonTypeService;
	
	@Autowired
	private DoctorOrganizationDepartmentDutyService doctorOrganizationDepartmentDutyService;
	
	@Autowired
	private OrganizationUserCertificateService organizationUserCertificateService;
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 新增超级管理员（is_manager设置为true判断）
	 */
	@RequiresAuthentication(value = { "platform:configure:organizationUser:manage:insert" },level = Level.OPERATION)
	@RequestMapping(value = { "/organization/manage/user" }, method = RequestMethod.POST)
	@Transactional
	public JsonApi insert(@RequestBody @Validated({ OrganizationUser.InsertOrganizationUser.class }) OrganizationUser  organizationUser,BindingResult result){
		/*设置默认值*/
		String phone = organizationUser.getPhone();
		String password = phone.substring(phone.length() - 6);
		password = MD5Util.getInstance().getMD5Code(password);
		organizationUser.setPassword(password);
		organizationUser.setCreateDate(new Date());
		organizationUser.setInitPassword(true);
		organizationUser.setPhoneStatus(OrganizationUserStatusCode.OrganizationUser.PHONESTATUSUNAUTH.getValue());
		organizationUser.setStatus(OrganizationUserStatusCode.OrganizationUser.ENABLE.getValue());
		
		/*检查数据是否重复*/
		// 电话号码不重复
		OrganizationUser  organizationUserByPhone = new OrganizationUser();
		organizationUserByPhone.setPhone(organizationUser.getPhone());
		Map<String, Object> organizationUserMap=organizationUserService.getRepeat(organizationUserByPhone);
		if (organizationUserMap!=null && !organizationUserMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organizationUser.phone.is.exists"));
		}
		// 账号不重复
		OrganizationUser  organizationUserByAccount = new OrganizationUser();
		organizationUserByAccount.setAccount(organizationUser.getAccount());
		Map<String, Object> organizationUserByAccountMap=organizationUserService.getRepeat(organizationUserByAccount);
		if (organizationUserByAccountMap!=null && !organizationUserByAccountMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organizationUser.account.is.exists"));
		}
		
		// 获取传入的机构用户证件列表
		List<OrganizationUserCertificate> organizationUserCertificates = organizationUser.getOrganizationUserCertificates();
		// 指定机构用户id
		for (OrganizationUserCertificate organizationUserCertificate : organizationUserCertificates) {
			if (MapUtils.isNotEmpty(organizationUserCertificateService.getRepeat(organizationUserCertificate))) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organizationUser.certificates.is.exists"));
			}
			organizationUserCertificate.setCreateDate(new Date());
			if (organizationUserCertificate.getCertificateType().equals(String.valueOf(OrganizationUserCertificateCode.CertificateTypeCode.IDCARD.getValue()))) {
				
			}
		}
		/*设置机构用户类型:管理员*/
		OrganizationPersonType organizationPersonType = new OrganizationPersonType();
		organizationPersonType.setCode(BaseGlobal.MANAGER);
		Map<String, Object> organizationPersonTypeOneResultMap = organizationPersonTypeService.getOne(organizationPersonType);
		if (MapUtils.isNotEmpty(organizationPersonTypeOneResultMap)) {
			organizationUser.setOrganizationPersonTypeId(Integer.parseInt(organizationPersonTypeOneResultMap.get("id").toString()));
		}
		/*新增机构用户*/
		if (organizationUserService.insert(organizationUser)>0) {
			for (OrganizationUserCertificate organizationUserCertificate : organizationUserCertificates) {
				organizationUserCertificate.setOrganizationUserId(organizationUser.getId());
			}
			// 添加证件
			if (organizationUserCertificateService.batchInsert(organizationUserCertificates) == organizationUserCertificates.size()) {
				/*设置关联表信息*/
				DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty = organizationUser.getDoctorOrganizationDepartmentDuty();
				doctorOrganizationDepartmentDuty.setOrganizationUserId(organizationUser.getId());
				/*doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);*/
				doctorOrganizationDepartmentDuty.setIsManager(true);
				doctorOrganizationDepartmentDuty.setIsLocal(true);
				doctorOrganizationDepartmentDuty.setStatus(OrganizationStatusCode.Organization.ENABLE.getValue());
				// 判断关联表是否有数据
				Map<String, Object> doctorOrganizationDepartmentDutyMap = doctorOrganizationDepartmentDutyService.getRepeat(doctorOrganizationDepartmentDuty);
				if(MapUtils.isNotEmpty(doctorOrganizationDepartmentDutyMap)){
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.manager.is.exists"));
				}
				/*新增关联表信息*/
				if (doctorOrganizationDepartmentDutyService.insert(doctorOrganizationDepartmentDuty)>0) {
					Map<String, Integer> userMap = new HashMap<>(); 
					userMap.put("id", organizationUser.getId());
					return new JsonApi(ApiCode.OK,userMap);
				}
				throw new RuntimeException();
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param id
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年6月7日
	 * @version 1.0
	 * @description 修改用户信息
	 */
	@RequiresAuthentication(authc = true, value = { "platform:configure:organization:user:update" })
	@RequestMapping(value = { "/organizationUser/update/{id}" }, method = RequestMethod.PUT)
	@Transactional
	public JsonApi updateOrganizationUser(@PathVariable("id")Integer id,
			@Validated({ Update.class }) @RequestBody OrganizationUser organizationUser, BindingResult result) {
		// 查询机构用户是否存在
		organizationUser.setId(id);
		Map<String, Object> organizationUserMap = organizationUserService.getOne(organizationUser);
		if (organizationUserMap!=null && !organizationUserMap.isEmpty()) {
			if (organizationUserService.update(organizationUser) > 0) {
				// 修改机构用户关联表 （授权书）
				DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty = organizationUser.getDoctorOrganizationDepartmentDuty();
				if (doctorOrganizationDepartmentDuty!=null) {
					doctorOrganizationDepartmentDuty.setIsManager(true);
					doctorOrganizationDepartmentDuty.setOrganizationUserId(id);
					if (doctorOrganizationDepartmentDutyService.update(doctorOrganizationDepartmentDuty) > 0) {
						return new JsonApi(ApiCode.OK);
					}
					throw new RuntimeException();
				}
				return new JsonApi(ApiCode.OK);
			}
			return new JsonApi(ApiCode.FAIL);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年7月19日
	 * @version 1.0
	 * @description 机构用户个人信息详情
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationUser:detail" })
	@RequestMapping(value = { "/organization/user/{id}" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserDetail(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) OrganizationUser organizationUser, BindingResult result) {
		organizationUser.setId(id);
		Map<String, Object> organizationUserOne = organizationUserService.getOne(organizationUser);
		if (MapUtils.isNotEmpty(organizationUserOne)) {
			return new JsonApi(ApiCode.OK,organizationUserOne);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	

}
