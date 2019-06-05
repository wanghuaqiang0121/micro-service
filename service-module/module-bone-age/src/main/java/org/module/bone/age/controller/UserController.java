package org.module.bone.age.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.bone.age.domain.User;
import org.module.bone.age.domain.UserCertificate;
import org.module.bone.age.domain.UserGroup;
import org.module.bone.age.domain.UserUserGroup;
import org.module.bone.age.global.BaseGlobal;
import org.module.bone.age.global.UserStatusCode;
import org.module.bone.age.message.Prompt;
import org.module.bone.age.rabbit.IUserTeamRealationService;
import org.module.bone.age.service.UserCertificateService;
import org.module.bone.age.service.UserGroupService;
import org.module.bone.age.service.UserService;
import org.module.bone.age.service.UserUserGroupService;
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

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private UserUserGroupService userUserGroupService;
	@Autowired
	private UserCertificateService userCertificateService;
	@Autowired
	private IUserTeamRealationService userTeamRealationService;

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param phone
	 * @param user
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年5月29日
	 * @version 1.0
	 * @description 骨龄搜索用户成员列表
	 */
	@RequiresAuthentication(value = { "bone:age:user:searchMember" }, level = Level.OPERATION)
	@RequestMapping(value = { "/search/member" }, method = RequestMethod.GET)
	public JsonApi getSearchMember(
			@Validated({ BaseEntity.SelectAll.class }) User user, BindingResult result) {
		Page<?> page = PageHelper.startPage(user.getPage(), user.getPageSize());
		List<Map<String, Object>> searchMemberList = userService.getSearchMember(user);
		if (searchMemberList != null && !searchMemberList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), searchMemberList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param id
	* @param user
	* @param result
	* @param token
	* @param organizationTeamId
	* @return 
	* @date 2018年6月6日
	* @version 1.0
	* @description 骨龄用户详情
	*/
	@RequiresAuthentication(value = { "bone:age:user:detail" }, level = Level.OPERATION)
	@RequestMapping(value = { "/user/{id}" }, method = RequestMethod.GET)
	public JsonApi getOne(
			@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) User user, BindingResult result) {
		user.setId(id);
		Map<String, Object> userMap = userService.getOne(user);
		if (userMap!=null && !userMap.isEmpty()) {
			return new JsonApi(ApiCode.OK, userMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param user
	* @param result
	* @param token
	* @param organizationTeamId
	* @return 
	* @date 2018年6月6日
	* @version 1.0
	* @description 骨龄用户组直接添加成员
	*/
	@RequiresAuthentication(value = { "bone:age:user:addGroup" }, level = Level.OPERATION)
	@RequestMapping(value = { "/add/group" }, method = RequestMethod.POST)
	public JsonApi addGroup(
			@Validated({ User.addGroup.class }) @RequestBody User user, BindingResult result) {
		UserUserGroup userUserGroup = new UserUserGroup();
		userUserGroup.setUserId(user.getId());
		userUserGroup.setUserGroupId(user.getUserGroupId());
		userUserGroup.setRelation(user.getRelation());
		userUserGroup.setTrust(UserStatusCode.UserUserGroup.USER_USRE_GROUP_TRUST_TRUSTEESHIP.getValue());
		userUserGroup.setCreateDatetime(new Date());
		Map<String, Object> userUserGroupMap = userUserGroupService.getRepeat(userUserGroup);
		if (userUserGroupMap!=null && !userUserGroupMap.isEmpty()) {
		return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.user.group.is.conflict"));	
		}
		
		List<UserCertificate> userCertificateList = user.getUserCertificates();
		if (userCertificateList != null && !userCertificateList.isEmpty()) {
			HashSet<UserCertificate> hashSet = new HashSet<>(userCertificateList);
			if (hashSet.size() != userCertificateList.size()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.certificate.is.conflict"));
			}
				for (UserCertificate userCertificate : userCertificateList) {
					Map<String, Object> userCertificateMap = userCertificateService.getRepeat(userCertificate);
					if (MapUtils.isNotEmpty(userCertificateMap)) {
						return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.certificate.is.conflict"));
					}
					String certificateType = userCertificate.getCertificateType();
					if (BaseGlobal.USER_ID_CARD_TYPE.equals(certificateType)) {
						user.setIdCard(userCertificate.getCertificateNumber());
					}
					userCertificate.setUserId(user.getId());
					userCertificate.setCreateDate(new Date());
				}
		}
		if (userUserGroupService.insert(userUserGroup) >0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年5月30日
	 * @version 1.0
	 * @description 骨龄添加成员
	 */
	@Transactional
	@RequiresAuthentication(value = { "bone:age:user:addMember" }, level = Level.OPERATION)
	@RequestMapping(value = { "/add/member" }, method = RequestMethod.POST)
	public JsonApi addMember(@Validated({ User.addMember.class }) @RequestBody User user, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId) {
		String phone = user.getPhone();
		// 判断手机号码是否重复
		if (null != phone && !"".equals(phone)) {
			user.setPhoneStatus(UserStatusCode.User.PHONE_STATUS_NOT_VALIDED.getValue());
			Map<String, Object> userByPhoneMap = userService.getUserByPhone(user);
			if (userByPhoneMap!=null && !userByPhoneMap.isEmpty()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.phone.is.conflict"));
			}
		}
		// 判断证件是否重复

		List<UserCertificate> userCertificateList = user.getUserCertificates();
		for (UserCertificate userCertificate : userCertificateList) {
			Map<String, Object> userCertificateMap = userCertificateService.getRepeat(userCertificate);
			if (MapUtils.isNotEmpty(userCertificateMap)) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.certificate.is.conflict"));
			}
			String certificateType = userCertificate.getCertificateType();
			if (BaseGlobal.USER_ID_CARD_TYPE.equals(certificateType)) {
				user.setIdCard(userCertificate.getCertificateNumber());
			}
		}
		
		user.setStatus(UserStatusCode.User.ENABLE.getValue());
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
		user.setSource(UserStatusCode.Source.SINGLEDIAGNOSISANDTREATMENT.getValue());
		// 新增用户
		if (userService.insert(user) > 0) {
			Integer userId = user.getId();
			for (UserCertificate userCertificate : userCertificateList) {
				userCertificate.setUserId(userId);
				userCertificate.setCreateDate(new Date());
			}
			/*新增证件*/
			if (userCertificateService.batchInsert(userCertificateList) == userCertificateList.size()) {
				UserUserGroup userUserGroup = new UserUserGroup();
				userUserGroup.setUserId(userId);
				userUserGroup.setUserGroupId(user.getUserGroupId());
				userUserGroup.setRelation(user.getRelation());
				userUserGroup.setTrust(UserStatusCode.UserUserGroup.USER_USRE_GROUP_TRUST_TRUSTEESHIP.getValue());
				userUserGroup.setCreateDatetime(new Date());
				if (userUserGroupService.insert(userUserGroup) > 0) {
					//新增用户和团队关联
					Map<String, Object> parm = new HashMap<>();
					parm.put("userId",userId);
					parm.put("organizationTeamId", organizationTeamId);
					parm.put("isSingleService", true);
					parm.put("time", 0);
					userTeamRealationService.userRabbit(parm);
					return new JsonApi(ApiCode.OK);
				}
				throw new RuntimeException();
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}

	
	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年5月31日
	 * @version 1.0
	 * @description 骨龄注册新用户
	 */
	@Transactional
	@RequiresAuthentication(value = { "bone:age:user:registerUser" }, level = Level.OPERATION)
	@RequestMapping(value = { "/register/user" }, method = RequestMethod.POST)
	public JsonApi registerUser(
			@Validated({ User.registerUser.class }) @RequestBody User user, BindingResult result,			
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId) {
		User parentUser = new User();
		parentUser.setPhone(user.getParentPhone());
		//手机是否重复
		Map<String, Object> userByPhoneMap = userService.getUserByPhone(parentUser);
		if (userByPhoneMap!=null && !userByPhoneMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.phone.is.conflict"));
		}
		String phone = user.getPhone();
		if (null != phone && !"".equals(phone)) {
			if (!BaseGlobal.USER_GROUP_RELATION.equals(user.getRelation())) {
				if (phone.equals(user.getParentPhone())) {
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.phone.is.conflict"));
				}
			}
			user.setPhoneStatus(UserStatusCode.User.PHONE_STATUS_NOT_VALIDED.getValue());
			Map<String, Object> userPhone = userService.getUserByPhone(user);
			if (MapUtils.isNotEmpty(userPhone)) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.phone.is.conflict"));
			}
		}
		
		List<UserCertificate> userCertificateList = user.getUserCertificates();
		for (UserCertificate userCertificate : userCertificateList) {
			Map<String, Object> userCertificateMap = userCertificateService.getRepeat(userCertificate);
			if (userCertificateMap!=null && !userCertificateMap.isEmpty()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.certificate.is.conflict"));
			}
			String certificateType = userCertificate.getCertificateType();
			if (BaseGlobal.USER_ID_CARD_TYPE.equals(certificateType)) {
				user.setIdCard(userCertificate.getCertificateNumber());
			}
		}
		
		Integer userGroupId;
		Integer userId;
		//判断是否是本人
		String relation = user.getRelation();
		user.setSource(UserStatusCode.Source.SINGLEDIAGNOSISANDTREATMENT.getValue());
		//是本人
		if (BaseGlobal.USER_GROUP_RELATION.equals(relation)) {
			user.setPhone(user.getParentPhone());
			user.setStatus(UserStatusCode.User.ENABLE.getValue());
			user.setPhoneStatus(UserStatusCode.User.PHONE_STATUS_NOT_VALIDED.getValue());
			user.setCreateDate(new Date());
			user.setStatus(UserStatusCode.User.ENABLE.getValue());
			user.setPhoneStatus(UserStatusCode.User.PHONE_STATUS_NOT_VALIDED.getValue());
			user.setUpdateDate(new Date());
			if (userService.insert(user) > 0) {
				userId = user.getId();
				UserGroup userGroup = new UserGroup();
				userGroup.setUserId(userId);
				userGroup.setCreateDate(new Date());
				userGroup.setPhone(user.getPhone());
				for (UserCertificate userCertificate : userCertificateList) {
					userCertificate.setUserId(userId);
					userCertificate.setCreateDate(new Date());
				}
				if (userCertificateService.batchInsert(userCertificateList) == userCertificateList.size()) {
				if (userGroupService.insert(userGroup) >0) {
					userGroupId = userGroup.getId();
					UserUserGroup userUserGroup = new UserUserGroup();
					userUserGroup.setRelation(user.getRelation());
					userUserGroup.setTrust(UserStatusCode.UserUserGroup.USER_USRE_GROUP_TRUST_TRUSTEESHIP.getValue());
					userUserGroup.setUserGroupId(userGroupId);
					userUserGroup.setUserId(userId);
					userUserGroup.setCreateDatetime(new Date());
					if (userUserGroupService.insert(userUserGroup) >0) {
						//新增用户和团队关联
						Map<String, Object> parm = new HashMap<>();
						parm.put("userId",userId);
						parm.put("organizationTeamId", organizationTeamId);
						parm.put("isSingleService", true);
						userTeamRealationService.userRabbit(parm);
						return new JsonApi(ApiCode.OK);
					}
					throw new RuntimeException();
				}
				throw new RuntimeException();
			}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.FAIL);
			//不是本人
		}else {
			//新增家长信息
			parentUser.setName(user.getParentName());
			parentUser.setBirthday(user.getParentBirthday());
			parentUser.setSex(user.getParentSex());
			parentUser.setStatus(UserStatusCode.User.ENABLE.getValue());
			parentUser.setPhoneStatus(UserStatusCode.User.PHONE_STATUS_NOT_VALIDED.getValue());
			parentUser.setCreateDate(new Date());
			parentUser.setUpdateDate(new Date());
			if (userService.insert(parentUser) > 0) {
				UserGroup userGroup = new UserGroup();
				userGroup.setUserId(parentUser.getId());
				userGroup.setCreateDate(new Date());
				userGroup.setPhone(parentUser.getPhone());
				if (userGroupService.insert(userGroup) >0) {
					userGroupId = userGroup.getId();
					user.setStatus(UserStatusCode.User.ENABLE.getValue());
					//user.setPhoneStatus(UserStatusCode.User.PHONE_STATUS_NOT_VALIDED.getValue());
					/*UserCertificate userCertificate = user.getUserCertificate();
					String certificateType = userCertificate.getCertificateType();
					if (BaseGlobal.USER_ID_CARD_TYPE.equals(certificateType)) {
						user.setIdCard(userCertificate.getCertificateNumber());
					}*/
					user.setCreateDate(new Date());
					user.setUpdateDate(new Date());
					if (userService.insert(user) >0) {
						userId = user.getId();
						for (UserCertificate userCertificate : userCertificateList) {
							userCertificate.setUserId(userId);
							userCertificate.setCreateDate(new Date());
						}
						if (userCertificateService.batchInsert(userCertificateList) == userCertificateList.size()) {
						UserUserGroup userUserGroup = new UserUserGroup();
						userUserGroup.setRelation(user.getRelation());
						userUserGroup.setTrust(UserStatusCode.UserUserGroup.USER_USRE_GROUP_TRUST_TRUSTEESHIP.getValue());
						userUserGroup.setUserGroupId(userGroupId);
						userUserGroup.setUserId(userId);
						userUserGroup.setCreateDatetime(new Date());
						if (userUserGroupService.insert(userUserGroup) >0) {
							//将自己添加到用户组，关系为本人
							UserUserGroup userUserGroupNew = new UserUserGroup();
							userUserGroupNew.setRelation(BaseGlobal.USER_GROUP_RELATION);
							userUserGroupNew.setTrust(UserStatusCode.UserUserGroup.USER_USRE_GROUP_TRUST_TRUSTEESHIP.getValue());
							userUserGroupNew.setUserGroupId(userGroupId);
							userUserGroupNew.setUserId(parentUser.getId());
							userUserGroupNew.setCreateDatetime(new Date());
							if (userUserGroupService.insert(userUserGroupNew) >0) {
								//新增用户和团队关联
								Map<String, Object> parm = new HashMap<>();
								parm.put("userId",userId);
								parm.put("organizationTeamId", organizationTeamId);
								parm.put("isSingleService", true);
								userTeamRealationService.userRabbit(parm);
								return new JsonApi(ApiCode.OK);
							}
							throw new RuntimeException();
						}
						throw new RuntimeException();
					}
						throw new RuntimeException();
					}
					throw new RuntimeException();
				}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.FAIL);
		}
	}
}
