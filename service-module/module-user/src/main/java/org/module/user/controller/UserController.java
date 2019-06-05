package org.module.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.module.user.domain.User;
import org.module.user.domain.UserCertificate;
import org.module.user.domain.UserGroup;
import org.module.user.domain.UserSign;
import org.module.user.domain.UserUserGroup;
import org.module.user.global.BaseGlobal;
import org.module.user.global.UserStatusCode;
import org.module.user.message.Prompt;
import org.module.user.service.UserCertificateService;
import org.module.user.service.UserGroupService;
import org.module.user.service.UserService;
import org.module.user.service.UserSignService;
import org.module.user.service.UserUserGroupService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.service.tools.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2018年1月8日
 * @version 1.0
 * @description
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserGroupService userGroupService;
	@Autowired
	private UserUserGroupService userUserGroupService;
	@Autowired
	private UserSignService userSignService;
	@Autowired
	private UserCertificateService userCertificateService;
	@Resource
	private RedisCacheManager cacheManager;
	
	
	
	
	
	
	
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param cacheName
	 * @param key
	 * @return
	 * @date 2018年12月13日
	 * @version 1.0
	 * @description 通过token 获取用户id
	 */
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/user/info/by/token" }, method = RequestMethod.GET)
	public JsonApi getSession(String cacheName, String key,@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		if (cacheName == null || cacheName == "") {
			throw new RuntimeException("redis cache name is empty");
		}
		/* 缓存key */
		if (key == null || key.length() <= 32) {
			throw new RuntimeException("login token identify length lacks");
		}
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
			if (session != null) {
				return key.equals(session.getIdentify()) ? new JsonApi(ApiCode.OK,session.get(Map.class).get("id").toString()) : null;
			}
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	 * @param id
	 * @param user
	 * @param result
	 * @return
	 * @date 2018年10月24日
	 * @version 1.0
	 * @description 修改用户信息 // TODO 待添加到新项目
	 */
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/user/{id}" }, method = RequestMethod.PUT)
	@Transactional
	public JsonApi update(@PathVariable("id") Integer id,
			@Validated({ BaseEntity.Update.class }) @RequestBody User user, BindingResult result) {
		/* 判断手机号是重复 */
		user.setId(id);
		user.setIdCard(null);
		if (StringUtils.isNotBlank(user.getPhone())) {
			User userNew = new User();
			userNew.setPhone(user.getPhone());
			Map<String, Object> userRepeatMap = userService.getRepeat(userNew);
			if (MapUtils.isNotEmpty(userRepeatMap) && (Integer) userRepeatMap.get("id") != id.intValue()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.phone.repeat"));
			}
		}

		/* 是否修改证件 */
		if (user.getUserCertificate() != null && user.getUserCertificate().getCertificateNumber() != null
				&& !"".equals(user.getUserCertificate().getCertificateNumber())) {
			if (user.getUserCertificate().getCertificateType() == null || "".equals(user.getUserCertificate().getCertificateType())) {
				return new JsonApi(ApiCode.FAIL,Prompt.bundle("user.certificate.is.not.null"));
			}
			UserCertificate userCertificate = user.getUserCertificate();
			if (BaseGlobal.USER_ID_CARD_TYPE.equals(userCertificate.getCertificateType())) {
				user.setIdCard(userCertificate.getCertificateNumber());
			}
			userCertificate.setUserId(user.getId());
			/* 判断证件是否被使用 */
			Map<String, Object> userCertificateRepeatMap = userCertificateService.getRepeat(userCertificate);
			/* 未被使用 */
			if (MapUtils.isEmpty(userCertificateRepeatMap)) {
				// 拼接证件字符串
				List<String> imagesList = userCertificate.getImagesList();
				if (CollectionUtils.isNotEmpty(imagesList)) {
					String images = "";
					for (String string : imagesList) {
						images = images + string + ",";
					}
					userCertificate.setImages(images.substring(0, images.length() - 1));
				}
				/* 删除用户证件并添加新证件 */
				if (userCertificateService.deleteByUserId(userCertificate) < 0) {
					throw new RuntimeException();
				}
				/* 新增证件信息 */
				userCertificate.setCreateDate(new Date());
				if (userCertificateService.insert(userCertificate) <= 0) {
					throw new RuntimeException();
				}
			} else if ((Integer) userCertificateRepeatMap.get("userId") != user.getId().intValue()) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.certificate.is.used"));
			}
		}
		user.setUpdateDate(new Date());
		/* 修改基本信息 */
		if (userService.update(user) <= 0) {
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.OK);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月27日
	 * @version 1.0
	 * @description 添加家庭组成员
	 */
	@RequiresAuthentication(authc = true, value = { "user:family:member" })
	@RequestMapping(value = { "/user/user/family/member" }, method = RequestMethod.POST)
	@Transactional
	public JsonApi organizationUserTokenDetail(@Validated({ User.insertMember.class }) @RequestBody User user,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		// 从缓存中获取用户
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		// 登录用户的id
		Integer operationUserId = (Integer) session.get(Map.class).get("id");
		// 判断电话是否被使用
		user.setIdCard(null);
		String phone = user.getPhone();
		if (null != phone && !"".equals(phone)) {
			User userNew = new User();
			userNew.setPhone(phone);
			Map<String, Object> userRepeatMap = userService.getRepeat(userNew);
			if (MapUtils.isNotEmpty(userRepeatMap) && operationUserId.intValue() != (Integer)userRepeatMap.get("id") ) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.phone.repeat"));
			}
		}
		// 被添加联系人ID
		Integer userMemberId = null;
		// 判断当前用户有没有用户组
		UserGroup userGroup = new UserGroup();
		userGroup.setUserId(operationUserId);
		// 判断当前用户是否属于某用户组
		Map<String, Object> userGroupRepeatMap = userUserGroupService.getUserBelongToGroup(userGroup);
		Integer userGroupId = null;
		// 用户组不存在创建用户组
		if (MapUtils.isEmpty(userGroupRepeatMap)) {// 用户组不存在-创建用户组
			userGroup.setUserId(operationUserId);
			userGroup.setCreateDate(new Date());
			Integer groupId = userGroupService.insert(userGroup);
			if (groupId <= 0) {
				return new JsonApi(ApiCode.FAIL);
			}
			userGroupId = userGroup.getId();
		} else {// 用户组存在
			Integer groupId = (Integer) userGroupRepeatMap.get("userGroupId");
			// 判断用户组成员个数 不能超过10个
			UserUserGroup baseUserUserGroup = new UserUserGroup();
			baseUserUserGroup.setUserGroupId(groupId);
			List<Map<String, Object>> usersByGroupList = userUserGroupService.getList(baseUserUserGroup);
			if (usersByGroupList.size() < 10) {
				userGroupId = groupId;
			} else {// 用户组成员数是否大于10
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.group.user.greater.than"));
			}
		}
		UserCertificate userCertificate = user.getUserCertificate();
		if (BaseGlobal.USER_ID_CARD_TYPE.equals(userCertificate.getCertificateType())) {
			user.setIdCard(userCertificate.getCertificateNumber());
		}
		// 判断证件是否被使用
		Map<String, Object> userCertificateRepeatMap = userCertificateService.getRepeat(userCertificate);
		// 未被使用-用户不存在
		if (MapUtils.isEmpty(userCertificateRepeatMap)) {
			// 拼接证件字符串
			List<String> imagesList = userCertificate.getImagesList();
			if (CollectionUtils.isNotEmpty(imagesList)) {
				String images = "";
				for (String string : imagesList) {
					images = images + string + ",";
				}
				userCertificate.setImages(images.substring(0, images.length() - 1));
			}
			// 判断添加成员是否为本人
			String relation = user.getRelation();
			// 为本人
			if (BaseGlobal.USER_GROUP_RELATION.equals(relation)) {
				UserUserGroup userUserGroup = new UserUserGroup();
				userUserGroup.setUserId(operationUserId);
				userUserGroup.setUserGroupId(userGroupId);
				Map<String, Object> userUserGroupRepeat = userUserGroupService.getRepeat(userUserGroup);
				if (MapUtils.isNotEmpty(userUserGroupRepeat)) {
					return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.group.is.user.exist"));
				}
				// 判断本人是否输入重复的证件类型
				userCertificate.setUserId(operationUserId);
				Map<String, Object> repeatByType = userCertificateService.getRepeatByType(userCertificate);
				if (MapUtils.isNotEmpty(repeatByType)) {
					return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.certificate.type.is.used"));
				}
				// 修改基本信息
				user.setId(operationUserId);
				if (userService.update(user) <= 0) {
					return new JsonApi(ApiCode.FAIL);
				}
				// 新增证件信息
				userCertificate.setCreateDate(new Date());
				if (userCertificateService.insert(userCertificate) <= 0) {
					return new JsonApi(ApiCode.FAIL);
				}
				userMemberId = operationUserId;
			} else {// 不是本人
				// 新增用户
				user.setCreateDate(new Date());
				user.setUpdateDate(new Date());
				user.setIsBindWechat(false);
				/* 终端来源 1 微信 2现场 3 IPTV 4 App */
				user.setTerminalSource(UserStatusCode.TerminalSource.WECHAT.getValue());
				if (userService.insert(user) <= 0) {
					return new JsonApi(ApiCode.FAIL);
				}
				userCertificate.setUserId(user.getId());
				userCertificate.setCreateDate(new Date());
				// 新增证件信息
				if (userCertificateService.insert(userCertificate) <= 0) {
					return new JsonApi(ApiCode.FAIL);
				}
				userMemberId = user.getId();
			}

		} else {// 证件被使用-用户存在
			// 如果证件存在，并且关系为本人，提示用户证件号码已经被使用
			if (BaseGlobal.USER_GROUP_RELATION.equals(user.getRelation())) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.certificate.is.used"));
			}
			Integer userId = (Integer) userCertificateRepeatMap.get("userId");
			UserUserGroup userUserGroup = new UserUserGroup();
			userUserGroup.setUserId(userId);
			userUserGroup.setUserGroupId(userGroupId);
			Map<String, Object> userUserGroupRepeatMap = userUserGroupService.getRepeat(userUserGroup);
			if (MapUtils.isNotEmpty(userUserGroupRepeatMap)) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.group.is.user.exist"));
			}
			// 查询是否签约
			UserSign userSign = new UserSign();
			userSign.setUserId(userId);
			List<Map<String, Object>> userSignList = userSignService.getList(userSign);
			if (userSignList.size() > 0) {
				return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.sign.repeat.error"));
			}
			userMemberId = userId;
		}
		// 将成员添加到用户组
		UserUserGroup userUserGroup = new UserUserGroup();
		userUserGroup.setUserId(userMemberId);
		userUserGroup.setUserGroupId(userGroupId);
		userUserGroup.setRelation(user.getRelation());
		userUserGroup.setTrust(UserStatusCode.UserUserGroup.TRUSTEESHIP.getValue());
		userUserGroup.setCreateDatetime(new Date());
		if (userUserGroupService.insert(userUserGroup) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param user
	 * @param result
	 * @return
	 * @date 2018年3月27日
	 * @version 1.0
	 * @description 注册并绑定用户
	 */
	@RequiresAuthentication(ignore = true, value = { "user:user:register" })
	@RequestMapping(value = { "/user/register" }, method = RequestMethod.POST)
	@Transactional
	public JsonApi registerBaseUser(@Validated({ User.register.class }) @RequestBody User user, BindingResult result) {
		// 拿到验证码
		String validCode = user.getValidCode();
		ValueWrapper valueWrapper = cacheManager.getCache(BaseGlobal.CACHE_CODE).get(user.getPhone());
		if (null == valueWrapper) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("valid.code.error"));
		}
		String code = (String) valueWrapper.get();
		if (!validCode.equals(code)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("valid.code.error"));
		}
		// 清除缓存
		cacheManager.getCache(BaseGlobal.CACHE_CODE).evict(user.getPhone());
		// 查询微信是否已经存在
		Map<String, Object> userWechatRepeatMap = userService.getWechatRepeat(user);
		if (MapUtils.isNotEmpty(userWechatRepeatMap)) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.wechat.is.exist"));
		}
		Integer userId;
		user.setIsBindWechat(true);
		// 通过电话查询用户是否已经存在
		Map<String, Object> userByPhoneMap = userService.getRepeat(user);
		// 用户不存在
		if (MapUtils.isEmpty(userByPhoneMap)) {// 用户不存在，直接注册
			user.setPhoneStatus(UserStatusCode.User.PHONE_STATUS_IS_VALIDED.getValue());
			user.setCreateDate(new Date());
			user.setUpdateDate(new Date());
			user.setTerminalSource(UserStatusCode.TerminalSource.WECHAT.getValue());
			if (userService.insert(user) < 1) {
				throw new RuntimeException();
			}
			userId = user.getId();
			/* 添加家庭组并添加自己 */
			UserGroup userGroup = new UserGroup();
			userGroup.setUserId(user.getId());
			userGroup.setPhone(user.getPhone());
			userGroup.setCreateDate(new Date());
			if (userGroupService.insert(userGroup) < 1) {
				throw new RuntimeException();
			}
			// 将成员添加到用户组
			UserUserGroup userUserGroup = new UserUserGroup();
			userUserGroup.setUserId(user.getId());
			userUserGroup.setUserGroupId(userGroup.getId());
			userUserGroup.setRelation(Prompt.bundle("relation.oneself"));
			userUserGroup.setRelationName(Prompt.bundle("oneself"));
			userUserGroup.setTrust(UserStatusCode.UserUserGroup.TRUSTEESHIP.getValue());
			userUserGroup.setCreateDatetime(new Date());
			if (userUserGroupService.insert(userUserGroup) < 1) {
				throw new RuntimeException();
			}
		} else {// 用户存在，直接绑定微信
			userId = (Integer) userByPhoneMap.get("id");
		}
		user.getWechat().setUserId(userId);
		user.getWechat().setCreateDate(new Date());
		if (userService.bindWechat(user) > 0) {
			User userNew = new User();
			userNew.setId(userId);
			userNew.setIsBindWechat(true);
			if (userService.update(userNew) > 0) {
				return new JsonApi(ApiCode.OK);
			}
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.wechat.build.error"));
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.wechat.build.error"));

	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param id
	 * @param user
	 * @param result
	 * @return
	 * @date 2018年3月27日
	 * @version 1.0
	 * @description 用户详情
	 */
	@RequiresAuthentication(authc = true, value = { "user:user:detail" })
	@RequestMapping(value = { "/user/detail/{id}" }, method = RequestMethod.GET)
	public JsonApi getUserDetails(@PathVariable("id") Integer id, @Validated({ User.SelectOne.class }) User user,
			BindingResult result) {
		user.setId(id);
		Map<String, Object> userDetailsMap = userService.getOne(user);
		if (MapUtils.isNotEmpty(userDetailsMap)) {
			return new JsonApi(ApiCode.OK, userDetailsMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param user
	 * @param result
	 * @return {@link JsonApi}
	 * @date 2018年3月6日
	 * @version 1.0
	 * @description 注册用户
	 */
	@RequiresAuthentication(authc = true, value = { "user:register" })
	@RequestMapping(value = { "/user" }, method = RequestMethod.POST)
	public JsonApi addUser(@RequestBody @Validated({ BaseEntity.Insert.class }) User user, BindingResult result) {
		// 查询用户手机号是否被注册
		User userRepeat = new User();
		userRepeat.setPhone(user.getPhone());
		Map<String, Object> userRepeatMap = userService.getRepeat(userRepeat);
		if (userRepeatMap != null && !userRepeatMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.phone.repeat"));
		}
		// 设置用户的默认属性
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
		user.setPhoneStatus(UserStatusCode.User.PHONE_STATUS_IS_VALIDED.getValue());
		user.setStatus(UserStatusCode.UserStatus.USE.getValue());
		user.setTerminalSource(UserStatusCode.TerminalSource.WECHAT.getValue());
		user.setIsBindWechat(false);
		if (userService.insert(user) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param user
	 * @param result
	 * @return {@link JsonApi}
	 * @date 2018年3月6日
	 * @version 1.0
	 * @description 绑定用户微信
	 */
	@RequiresAuthentication(authc = true, value = { "user:build" })
	@RequestMapping(value = { "/user/wechat" }, method = RequestMethod.POST)
	public JsonApi bindUserWechat(@RequestBody @Validated({ User.BindWechat.class }) User user, BindingResult result) {
		// 查询用户微信号是否已被绑定
		Map<String, Object> wechatMap = userService.getWechatRepeat(user);
		if (wechatMap != null && !wechatMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.openid.repeat"));
		}
		user.setIsBindWechat(true);
		// 查询用户是否存在
		Map<String, Object> userMap = userService.getRepeat(user);
		if (userMap == null || userMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("user.phone.empty"));
		}
		// 设置用户ID
		user.getWechat().setUserId(Integer.parseInt(userMap.get("id").toString()));
		// 设置日期
		user.getWechat().setCreateDate(new Date());
		if (userService.bindWechat(user) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param user
	 * @param result
	 * @return {@link JsonApi}
	 * @date 2018年3月7日
	 * @version 1.0
	 * @description 微信登录
	 */
	@RequiresAuthentication(ignore = true, value = { "user:login" })
	@RequestMapping(value = { "/user/login/wechat" }, method = RequestMethod.GET)
	public JsonApi userLoginWechat(@Validated({ User.WechatLogin.class }) User user, BindingResult result) {
		// 根据微信标识查询用户信息
		Map<String, Object> userMap = userService.getUserByWechat(user);
		if (user.getEquipment().intValue() != 1 && user.getEquipment().intValue() != 2) {
			return new JsonApi(ApiCode.FAIL);
		}
		if (userMap != null && !userMap.isEmpty()  ) {
			// 登录成功
			// 存入缓存
			String token = user.getEquipment().toString() + MD5Util.getInstance().getSessionToken(userMap.get("id"));
			// 放入缓存 已实现自动踢出
			cacheManager.AuthenticationToken(new AuthenticationToken(BaseGlobal.CACHE_USER,
					user.getEquipment().toString()+userMap.get("id").toString(), new AuthenticationSession(token, userMap)));
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("info", userMap);
			resultMap.put("token", token);
			// 修改登录时间
			user.getWechat().setId(Integer.parseInt(userMap.get("wechatId").toString()));
			user.getWechat().setLoginDate(new Date());
			if (userService.updateUserWechat(user) > 0) {
				return new JsonApi(ApiCode.OK, resultMap);
			}
			// 返回用户信息
			return new JsonApi(ApiCode.OK, resultMap);
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.login.wechat.error"));
	}
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param user
	* @param result
	* @return 
	* @date 2019年1月10日
	* @version 1.0
	* @description 切换登录用户
	*/
	@RequiresAuthentication(ignore = true, value = { "user:change" })
	@RequestMapping(value = { "/user/login/change/{id}" }, method = RequestMethod.GET)
	public JsonApi userChange(@PathVariable("id") Integer id,@Validated({ User.Change.class }) User user, BindingResult result
			, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		/* 判断数据是否存在 */
		User u =new User();
		u.setId(id);
		Map<String, Object> userMap = userService.getLoginMsg(u);
		if (user.getEquipment().intValue() != 1 && user.getEquipment().intValue() != 2) {
			return new JsonApi(ApiCode.FAIL);
		}
		if (userMap == null) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.is.not.exists"));
		}
		/* 获取原来的token */
		Cache cache = cacheManager.getCache(token);
		if (cache != null) {
			AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
			
			if (session != null) {
				//移除token cacheManager.removeAuthenticationToken(new AuthenticationToken(BaseGlobal.CACHE_USER, session.get(Map.class).get("id").toString()));
				/* 生成新token*/
				String newToken = user.getEquipment().toString()+MD5Util.getInstance().getSessionToken(id);
				// 放入缓存 已实现自动踢出
				cacheManager.AuthenticationToken(new AuthenticationToken(BaseGlobal.CACHE_USER,
						user.getEquipment().toString()+userMap.get("id").toString(), new AuthenticationSession(newToken, userMap)));
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("info", userMap);
				resultMap.put("token", newToken);
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.is.not.exists"));
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param token
	 * @return {@link JsonApi}
	 * @date 2018年3月7日
	 * @version 1.0
	 * @description 获取用户登录信息
	 */
	@RequiresAuthentication(authc = true, value = { "user:login:detail" })
	@RequestMapping(value = { "/user/{token}" }, method = RequestMethod.GET)
	public JsonApi getUserCache(@PathVariable("token") String token) {
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		if (session != null) {
			return new JsonApi(ApiCode.OK, session);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
