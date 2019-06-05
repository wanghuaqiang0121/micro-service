package org.module.organization.configure.controller.doctor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.module.organization.configure.domain.OrganizationUserCertificate;
import org.module.organization.configure.domain.doctor.DoctorOrganizationDepartmentDuty;
import org.module.organization.configure.domain.doctor.OrganizationUser;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.global.OrganizationUserCertificateCode;
import org.module.organization.configure.global.OrganizationUserStatusCode;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationUserCertificateService;
import org.module.organization.configure.service.doctor.DoctorOrganizationDepartmentDutyService;
import org.module.organization.configure.service.doctor.OrganizationUserService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.service.tools.md5.MD5Util;
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

@RestController
public class OrganizationUserController {
	@Autowired
	private OrganizationUserService organizationUserService;
	/*@Autowired
	private DoctorInfoService doctorInfoService;*/
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
	 * @description 机构用户电话号码判断重复 
	 * // TODO 待添加到新项目
	 */ 
	
	@RequiresAuthentication( value = { "organization:configure:organizationUser:validPhoneRepeat" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/valid/phone" }, method = RequestMethod.GET)
	public JsonApi validPhoneRepeat(@Validated({ OrganizationUser.PhoneRepeat.class }) OrganizationUser  organizationUser,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId ){
		Map<String, Object> resultMap = new HashMap<>(); 
		/*检查电话号码是否重复*/
		OrganizationUser organizationUserByPhone = new OrganizationUser();
		organizationUserByPhone.setPhone(organizationUser.getPhone());
		Map<String, Object> organizationUserMap=organizationUserService.getRepeat(organizationUserByPhone);
		/* 添加时没有机构用户id 修改时有 修改时允许提交的电话号码等于自己的电话号码 */
		if (organizationUser.getId() != null) {
			int organizationUserId = organizationUser.getId();
			if (organizationUserMap!=null && (Integer)organizationUserMap.get("id") != organizationUserId) {
				resultMap.put("isRepeat",true);
				return new JsonApi(ApiCode.OK,resultMap).setMsg(Prompt.bundle("organizationUser.phone.is.exists"));
			}
		}else{
			if (organizationUserMap!=null) {
				resultMap.put("isRepeat",true);
				return new JsonApi(ApiCode.OK,resultMap).setMsg(Prompt.bundle("organizationUser.phone.is.exists"));
			}
		}
		resultMap.put("isRepeat",false);
		return new JsonApi(ApiCode.OK,resultMap);
	}
	
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
	 * @description 验证账号是否重复 
	 * // TODO 待添加到新项目
	 */ 
	@RequiresAuthentication( value = { "organization:configure:organizationUser:validAccountRepeat" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/valid/account" }, method = RequestMethod.GET)
	public JsonApi validAccountRepeat(@Validated({ OrganizationUser.AccountRepeat.class }) OrganizationUser  organizationUser,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId ){
		/*账号是否重复*/
		OrganizationUser  organizationUserByAccount = new OrganizationUser();
		organizationUserByAccount.setAccount(organizationUser.getAccount());
		Map<String, Object> resultMap = new HashMap<>(); 
		Map<String, Object> organizationUserByAccountMap=organizationUserService.getRepeat(organizationUserByAccount);
		/* 添加时没有机构用户id 修改时有 修改时允许提交的账号等于自己的账号 */
		if (organizationUser.getId() != null) {
			int organizationUserId = organizationUser.getId();
			if (organizationUserByAccountMap!=null && organizationUserId != (Integer)organizationUserByAccountMap.get("id")) {
				resultMap.put("isRepeat",true);
				return new JsonApi(ApiCode.OK,resultMap).setMsg(Prompt.bundle("organizationUser.account.is.exists"));
			}
		}else{
			if (organizationUserByAccountMap!=null) {
				resultMap.put("isRepeat",true);
				return new JsonApi(ApiCode.OK,resultMap).setMsg(Prompt.bundle("organizationUser.account.is.exists"));
			}
		}
		resultMap.put("isRepeat",false);
		return new JsonApi(ApiCode.OK,resultMap);
	}
	
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
	 * @description 验证身份证是否重复 
	 * // TODO 待添加到新项目
	 */ 
	@RequiresAuthentication( value = { "organization:configure:organizationUser:validCertificateRepeat" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/valid/certificate" }, method = RequestMethod.GET)
	public JsonApi validCertificateRepeat(@Validated({ OrganizationUser.validCertificateRepeat.class }) OrganizationUser  organizationUser,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId ){
		// 获取传入的机构用户证件列表
		List<OrganizationUserCertificate> organizationUserCertificates = organizationUser.getOrganizationUserCertificates();
		Map<String, Object> resultMap = new HashMap<>(); 
		// 指定机构用户id
		for (OrganizationUserCertificate organizationUserCertificate : organizationUserCertificates) {
			if (organizationUser.getId() != null) {
				int organizationUserId = organizationUser.getId();
				Map<String, Object> certificateMap = organizationUserCertificateService.getRepeat(organizationUserCertificate);
				if (MapUtils.isNotEmpty(certificateMap) && organizationUserId != (Integer)certificateMap.get("organizationUserId")) {
					resultMap.put("isRepeat",true);
					return new JsonApi(ApiCode.OK,resultMap).setMsg(Prompt.bundle("organizationUser.certificates.is.exists"));
				}
			}
		}
		resultMap.put("isRepeat",false);
		return new JsonApi(ApiCode.OK,resultMap);
	}
	
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
	 * @description 新增机构用户
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationUser:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user" }, method = RequestMethod.POST)
	@Transactional
	public JsonApi insert(@RequestBody @Validated({ OrganizationUser.InsertOrganizationUser.class }) OrganizationUser  organizationUser,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId
			){
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
		if (organizationUserMap!=null &&!organizationUserMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organizationUser.phone.is.exists"));
		}
		// 账号不重复
		OrganizationUser  organizationUserByAccount = new OrganizationUser();
		organizationUserByAccount.setAccount(organizationUser.getAccount());
		Map<String, Object> organizationUserByAccountMap=organizationUserService.getRepeat(organizationUserByAccount);
		if (organizationUserByAccountMap!=null &&!organizationUserByAccountMap.isEmpty()) {
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
		/*新增机构用户*/
		if (organizationUserService.insert(organizationUser)>0) {
			/*设置关联表信息*/
			DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty = new DoctorOrganizationDepartmentDuty();
			doctorOrganizationDepartmentDuty.setOrganizationUserId(organizationUser.getId());
			doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
			doctorOrganizationDepartmentDuty.setIsManager(false);
			doctorOrganizationDepartmentDuty.setIsLocal(true);
			doctorOrganizationDepartmentDuty.setStatus(OrganizationUserStatusCode.OrganizationUser.ENABLE.getValue());
			if (null !=organizationUser.getWorkNumber()) {
				doctorOrganizationDepartmentDuty.setWorkNumber(organizationUser.getWorkNumber());
			}
			/*新增关联表信息*/
			if (doctorOrganizationDepartmentDutyService.insert(doctorOrganizationDepartmentDuty)>0){
				for (OrganizationUserCertificate organizationUserCertificate : organizationUserCertificates) {
					organizationUserCertificate.setOrganizationUserId(organizationUser.getId());
				}
				// 添加证件
				if (organizationUserCertificateService.batchInsert(organizationUserCertificates) == organizationUserCertificates.size()) {
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
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 重置密码
	 */
	@RequiresAuthentication(value = { "organization:configure:organizationUser:password:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/password/{id}" }, method = RequestMethod.PUT)
	public JsonApi updatePassword(
			@PathVariable("id") Integer id,
			@RequestBody @Validated({ BaseEntity.Update.class }) OrganizationUser  organizationUser,BindingResult result){
		/*判断数据是否存在*/
		organizationUser.setId(id);
		Map<String, Object> dorganizationUserMap = organizationUserService.getOne(organizationUser);
		if (dorganizationUserMap==null || dorganizationUserMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		String phone=dorganizationUserMap.get("phone").toString();
		String newPassword=phone.substring(phone.length()-6);
		organizationUser.setPassword(MD5Util.getInstance().getMD5Code(newPassword));
		if (organizationUserService.update(organizationUser)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
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
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 修改机构用户
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationUser:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/{id}" }, method = RequestMethod.PUT)
	@Transactional
	public JsonApi update(@PathVariable("id") Integer id,@RequestBody @Validated({ BaseEntity.Update.class }) OrganizationUser  organizationUser,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty = new DoctorOrganizationDepartmentDuty();
		doctorOrganizationDepartmentDuty.setOrganizationUserId(id);
		doctorOrganizationDepartmentDuty.setOrganizationId(organizationId);
		Map<String, Object> doctorOrganizationDepartmentDutyMap = doctorOrganizationDepartmentDutyService.getRepeat(doctorOrganizationDepartmentDuty);
		if (doctorOrganizationDepartmentDutyMap==null || doctorOrganizationDepartmentDutyMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("doctor.organization.department.duty.not.found"));
		}
		doctorOrganizationDepartmentDuty.setId((Integer)doctorOrganizationDepartmentDutyMap.get("id"));
		doctorOrganizationDepartmentDuty.setDepartmentId(organizationUser.getDepartmentId());
		doctorOrganizationDepartmentDuty.setPost(organizationUser.getPost());
		if (null !=organizationUser.getWorkNumber()) {
			doctorOrganizationDepartmentDuty.setWorkNumber(organizationUser.getWorkNumber());	
		}
		/*判断数据是否存在*/
		organizationUser.setId(id);
		/*organizationUser.setIdCard(null);
		organizationUser.setAccount(null);*/
		Map<String, Object> organizationUserMap = organizationUserService.getOne(organizationUser);
		if (organizationUserMap==null || organizationUserMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("organization.user.one.not.found"));
		}
		// 如果修改电话号码 需要判断重复
		if(StringUtils.isNotBlank(organizationUser.getPhone())){
			// 电话号码不重复
			OrganizationUser  organizationUserByPhone = new OrganizationUser();
			organizationUserByPhone.setPhone(organizationUser.getPhone());
			Map<String, Object> organizationUserByPhoneMap=organizationUserService.getRepeat(organizationUserByPhone);
			if (organizationUserByPhoneMap!=null &&!organizationUserByPhoneMap.isEmpty()) {
				if(!organizationUserMap.get("id").toString().equals(organizationUserByPhoneMap.get("id").toString())){
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organizationUser.phone.is.exists"));
				}
			}
		}
		// 如果修改账号 需要判断重复
		if(StringUtils.isNotBlank(organizationUser.getAccount())){
			// 账号不重复
			OrganizationUser  organizationUserByAccount = new OrganizationUser();
			organizationUserByAccount.setAccount(organizationUser.getAccount());
			Map<String, Object> organizationUserByAccountMap=organizationUserService.getRepeat(organizationUserByAccount);
			if (organizationUserByAccountMap!=null &&!organizationUserByAccountMap.isEmpty()) {
				if(!organizationUserMap.get("id").toString().equals(organizationUserByAccountMap.get("id").toString())){
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organizationUser.account.is.exists"));
				}
			}
		}
		//修改数据
		if (doctorOrganizationDepartmentDutyService.update(doctorOrganizationDepartmentDuty)>0) {
			if (organizationUserService.update(organizationUser) >0) {
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
	 * @param organizationUser
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月23日
	 * @version 1.0
	 * @description 获取机构用户详信息
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationUser:detail" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/user/{id}" }, method = RequestMethod.GET)
	public JsonApi getOrganizationUserDetail(@PathVariable("id") Integer id,@Validated({ BaseEntity.SelectOne.class }) OrganizationUser  organizationUser,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId){
		/*设置主键*/
		organizationUser.setId(id);
		organizationUser.setOrganizationId(organizationId);
		Map<String, Object> organizationUserMap = organizationUserService.getOne(organizationUser);
		if (organizationUserMap!=null && !organizationUserMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,organizationUserMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
		
	}
	public static void main(String[] args) {
		System.out.println(MD5Util.getInstance().getMD5Code("218719"));
	}

}
