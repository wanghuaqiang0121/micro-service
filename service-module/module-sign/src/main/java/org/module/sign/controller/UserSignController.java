package org.module.sign.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.sign.domain.User;
import org.module.sign.domain.UserSign;
import org.module.sign.domain.UserSignLog;
import org.module.sign.domain.UserUserType;
import org.module.sign.global.Sign;
import org.module.sign.message.Prompt;
import org.module.sign.service.UserService;
import org.module.sign.service.UserSignLogService;
import org.module.sign.service.UserSignService;
import org.module.sign.service.UserUserTypeService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.redis.cache.RedisCacheManager;
import org.service.tools.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
public class UserSignController {

	@Autowired
	private UserSignService userSignService;
	@Resource
	private RedisCacheManager cacheManager;
	@Autowired
	private UserService userService;
	@Autowired
	private UserSignLogService userSignLogService;
	@Autowired
	private UserUserTypeService userUserTypeService;

	/**
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param userSign
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月27日
	 * @version 1.0
	 * @description 线上用户签约和申请包
	 */
	@Transactional
	@RequiresAuthentication(authc = true, value = { "sign:sign:insert" })
	@RequestMapping(value = { "/sign/online" }, method = RequestMethod.POST)
	public JsonApi insert(@RequestBody @Validated({ UserSign.onlineSign.class }) UserSign userSign,
			BindingResult result) {
		// 设置发起方式\n1：线下\n2：线上
		userSign.setLaunchType(Sign.LaunchTypeEnum.ONLINE.getValue());
		// 判断用户是否存在
		User user = new User();
		user.setId(userSign.getUserId());
		Map<String, Object> userOneResult = userService.getOne(user);
		if (MapUtils.isEmpty(userOneResult)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.is.not.exists"));
		}
		// 判断用户是否签约
		Map<String, Object> userSignOneResult = userSignService.getOne(userSign);
		if (userSignOneResult != null && userOneResult.size() > 0) {
			// 判断状态
			if (userSignOneResult.get("status").equals(Sign.StatusEnum.EXECUTIONED)) {
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.user.sign.is.exists"));
			} else {
				userSign.setId((Integer)userSignOneResult.get("id"));
				userSign.setUpdateDate(new Date());
				// TODO 数据权限控制 判断被操作者是否是操作者的组成员
				userSign.setStatus(Sign.StatusEnum.WAITRESPONSE.getValue());
				userSign.setSignNo(MD5Util.getInstance().getOrderNo());
				userSign.setUpdateDate(new Date());
				if (userSignService.update(userSign) > 0) {
					// 修改签约 删除人群分类 新增人群分类 新增日志
					/*添加日志*/ 
					UserSignLog userSignLog = new UserSignLog();
					userSignLog.setType(Sign.LogTypeEnum.OPERATIONLOG.getValue());
					userSignLog.setUserSignId(userSign.getId());
					userSignLog.setStatus(userSign.getStatus());
					userSignLog.setExplain("线上签约");
					userSignLog.setContent(JSON.toJSONString(userSign));
					userSignLog.setCreateDate(new Date());
					/* 新增签约日志 */
					if (userSignLogService.insert(userSignLog) > 0) {
						/*删除人群类型关联重新添加*/
						UserUserType userUserTypes = new UserUserType();
						userUserTypes.setUserId(userSign.getUserId());
						userUserTypeService.delete(userUserTypes);
						/*批量添加人群分类*/ 
						if (userSign.getUserUserType()!=null) {
							List<UserUserType> userUserType=userSign.getUserUserType();
								if (userUserTypeService.batchInsert(userUserType)==userSign.getUserUserType().size()) {
									return new JsonApi(ApiCode.OK);
								}
								throw new RuntimeException();
							}					
						return new JsonApi(ApiCode.OK);
					}
					throw new RuntimeException();
				}return new JsonApi(ApiCode.FAIL);
			}
		} else {
			userSign.setUpdateDate(new Date());
			// TODO 数据权限控制 判断被操作者是否是操作者的组成员
			userSign.setStatus(Sign.StatusEnum.WAITRESPONSE.getValue());
			userSign.setSignNo(MD5Util.getInstance().getOrderNo());
			userSign.setUpdateDate(new Date());
			userSign.setCreateDate(new Date());
			// 添加签约
			if (userSignService.insert(userSign) > 0) {
				// 新增签约 新增人群分类 新增日志
				/*添加日志*/ 
				UserSignLog userSignLog = new UserSignLog();
				userSignLog.setType(Sign.LogTypeEnum.OPERATIONLOG.getValue());
				userSignLog.setUserSignId(userSign.getId());
				userSignLog.setStatus(userSign.getStatus());
				userSignLog.setExplain("线上签约");
				userSignLog.setContent(JSON.toJSONString(userSign));
				userSignLog.setCreateDate(new Date());
				/* 新增签约日志 */
				if (userSignLogService.insert(userSignLog) > 0) {
					/*批量添加人群分类*/ 
					if (userSign.getUserUserType()!=null) {
						List<UserUserType> userUserType=userSign.getUserUserType();
							if (userUserTypeService.batchInsert(userUserType)==userSign.getUserUserType().size()) {
								return new JsonApi(ApiCode.OK);
							}
							throw new RuntimeException();
						}					
					return new JsonApi(ApiCode.OK);
				}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.FAIL);
		}
	}
}
