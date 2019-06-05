package org.module.user.controller.order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.module.user.domain.Recipient;
import org.module.user.domain.Sms;
import org.module.user.domain.User;
import org.module.user.domain.WechatApi;
import org.module.user.domain.itv.Organization;
import org.module.user.domain.order.OrganizationPackageService;
import org.module.user.domain.order.TeamOrganizationServicePackage;
import org.module.user.domain.order.UserService;
import org.module.user.domain.order.UserServicePackageOrder;
import org.module.user.global.BaseGlobal;
import org.module.user.global.IWechatApi;
import org.module.user.global.RecipientCode;
import org.module.user.global.UserStatusCode;
import org.module.user.message.Prompt;
import org.module.user.service.OrganizationService;
import org.module.user.service.order.OrganizationPackageServiceService;
import org.module.user.service.order.TeamOrganizationServicePackageService;
import org.module.user.service.order.UserServicePackageOrderService;
import org.module.user.service.order.UserServiceService;
import org.module.user.task.ISmsService;
import org.module.user.task.IUserRabbitService;
import org.module.user.task.IWeChatService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity.SelectAll;
import org.service.core.entity.BaseEntity.SelectOne;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.service.tools.date.DateUtils;
import org.service.tools.md5.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class UserServicePackageOrderController {
	Logger log = LoggerFactory.getLogger(UserServicePackageOrderController.class);
	@Autowired
	private UserServicePackageOrderService userServicePackageOrderService;
	@Autowired
	private TeamOrganizationServicePackageService teamOrganizationServicePackageService;
	@Autowired
	private OrganizationPackageServiceService organizationPackageServiceService;
	@Autowired
	private UserServiceService userServiceService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private IUserRabbitService iUserRabbitService;
	@Autowired
	private IWeChatService iWeChatService;
	@Autowired
	private ISmsService iSmsService;
	@Autowired
	private org.module.user.service.UserService userServiceSer;
	@Resource
	private RedisCacheManager cacheManager;
	@Value("${pay.orderquery}")
	private String postUrl;
	private final static Logger logger = LoggerFactory.getLogger(UserServicePackageOrderController.class);
	/* 定义微信 交易状态 */
	private final static String tradeState = "SUCCESS";
	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param userServicePackageOrder
	 * @param result
	 * @return
	 * @date 2018年3月28日
	 * @version 1.0
	 * @description 查询用户组订单列表
	 */
	@RequiresAuthentication(authc = true, value = { " " })
	@RequestMapping(value = { "/user/group/service/package/orders" }, method = RequestMethod.GET)
	public JsonApi getListByGroup(@Validated({ SelectAll.class }) UserServicePackageOrder userServicePackageOrder, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		if (null == userServicePackageOrder.getUserId()) {
			// 从缓存中获取用户
			AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
			// 登录用户的id
			Integer operationUserId = (Integer) session.get(Map.class).get("id");
			userServicePackageOrder.setUserId(operationUserId);
		}
		Page<?> page = PageHelper.startPage(userServicePackageOrder.getPage(), userServicePackageOrder.getPageSize());
		List<Map<String, Object>> userServicePackageOrderList = userServicePackageOrderService.getList(userServicePackageOrder);
		if (userServicePackageOrderList != null && userServicePackageOrderList.size() > 0) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), userServicePackageOrderList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	 * @param userServicePackageOrder
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年11月15日
	 * @version 1.0
	 * @description 查询用户订单列表
	 */
	@RequiresAuthentication(authc = true, value = { " " })
	@RequestMapping(value = { "/user/service/package/orders" }, method = RequestMethod.GET)
	public JsonApi getListByUser(@Validated({ SelectAll.class }) UserServicePackageOrder userServicePackageOrder, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		if (null == userServicePackageOrder.getUserId()) {
			// 从缓存中获取用户
			AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
			// 登录用户的id
			Integer operationUserId = (Integer) session.get(Map.class).get("id");
			userServicePackageOrder.setUserId(operationUserId);
		}
		Page<?> page = PageHelper.startPage(userServicePackageOrder.getPage(), userServicePackageOrder.getPageSize());
		List<Map<String, Object>> userServicePackageOrderList = userServicePackageOrderService.getListByUserId(userServicePackageOrder);
		if (userServicePackageOrderList != null && userServicePackageOrderList.size() > 0) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), userServicePackageOrderList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param id
	 * @param userServicePackageOrder
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年4月3日
	 * @version 1.0
	 * @description 订单详情
	 */
	@RequiresAuthentication(authc = true, value = { " " })
	@RequestMapping(value = { "/user/service/package/order/{id}" }, method = RequestMethod.GET)
	public JsonApi getUseOrderDetail(@PathVariable("id") Integer id, @Validated({ SelectOne.class }) UserServicePackageOrder userServicePackageOrder, BindingResult result) {
		userServicePackageOrder.setId(id);
		Map<String, Object> userServicePackageOrderOneMap = userServicePackageOrderService.getOne(userServicePackageOrder);
		if (MapUtils.isNotEmpty(userServicePackageOrderOneMap)) {
			return new JsonApi(ApiCode.OK, userServicePackageOrderOneMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param userServicePackageOrder
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月28日
	 * @version 1.0
	 * @description 线上新增订单
	 */
	@RequiresAuthentication(authc = true, value = { " " })
	@RequestMapping(value = { "/user/order/online" }, method = RequestMethod.POST)
	@Transactional
	public JsonApi insertUserOnlineOrder(@Validated({ UserServicePackageOrder.onlineOrder.class }) @RequestBody UserServicePackageOrder userServicePackageOrder, BindingResult result) {
		User user = new User();
		user.setId(userServicePackageOrder.getUserId());
		Map<String, Object> userMap = userServiceSer.getOne(user);
		if ((userMap != null && !userMap.isEmpty()) && (Integer) userMap.get("source") != null) {
			user.setSource(UserStatusCode.Source.BUYSERVICEPACKAGE.getValue());
			if (userServiceSer.update(user) <= 0) {
				throw new RuntimeException();
			}
		}
		// 查询套餐包是否存在
		TeamOrganizationServicePackage teamOrganizationServicePackage = new TeamOrganizationServicePackage();
		teamOrganizationServicePackage.setId(userServicePackageOrder.getTeamOrganizationServicePackageId());
		Map<String, Object> teamOrganizationServicePackageOneMap = teamOrganizationServicePackageService.getOne(teamOrganizationServicePackage);
		if (MapUtils.isEmpty(teamOrganizationServicePackageOneMap)) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("team.organization.service.package.data.unexist"));
		}
		// 设置订单
		userServicePackageOrder.setStatus(UserStatusCode.UserServicePackageOrder.Waiting.getValue());
		userServicePackageOrder.setType(UserStatusCode.Channel.OnLineWechat.getValue());
		/* 设置默认值 */
		/* 设置服务包团队ID */
		userServicePackageOrder.setDoctorTeamId(Integer.parseInt(teamOrganizationServicePackageOneMap.get("doctorTeamId").toString()));
		/* 设置机构服务包ID */
		userServicePackageOrder.setOrganizationServicePackageId(Integer.parseInt(teamOrganizationServicePackageOneMap.get("organizationServicePackageId").toString()));
		/* 设置订单价格 */
		userServicePackageOrder.setPrice(new BigDecimal(teamOrganizationServicePackageOneMap.get("price").toString()));
		String OrderNo = UserStatusCode.UserServicePackageOrder.OrderNumber.GetOrderNumber(UserStatusCode.Channel.OnLineWechat.getValue(), UserStatusCode.Pay.WeChat.getValue(),
				UserStatusCode.Business.yzg.getValue(), userServicePackageOrder.getUserId());
		/* 设置订单号码 */
		userServicePackageOrder.setOrderNo(OrderNo);
		/* 设置订单创建时间 */
		userServicePackageOrder.setCreateDate(new Date());
		userServicePackageOrder.setMerchantNumber(teamOrganizationServicePackageOneMap.get("merchantNumber") != null ? teamOrganizationServicePackageOneMap.get("merchantNumber").toString() : null);
		userServicePackageOrder.setAppId(teamOrganizationServicePackageOneMap.get("appId") != null ? teamOrganizationServicePackageOneMap.get("appId").toString() : null);
		userServicePackageOrder.setAppSecret(teamOrganizationServicePackageOneMap.get("appSecret") != null ? teamOrganizationServicePackageOneMap.get("appSecret").toString() : null);
		userServicePackageOrder.setPayKey(teamOrganizationServicePackageOneMap.get("payKey").toString() != null ? teamOrganizationServicePackageOneMap.get("payKey").toString() : null);
		userServicePackageOrder.setWechatId(teamOrganizationServicePackageOneMap.get("wechatId") != null ? teamOrganizationServicePackageOneMap.get("wechatId").toString() : null);
		userServicePackageOrder.setWechatKey(teamOrganizationServicePackageOneMap.get("wechatKey") != null ? teamOrganizationServicePackageOneMap.get("wechatKey").toString() : null);
		if (userServicePackageOrderService.insert(userServicePackageOrder) > 0) {
			// 商户号信息
			Map<String, Object> merchantNumberMap = new HashMap<>();
			merchantNumberMap.put("orderId", userServicePackageOrder.getId());
			merchantNumberMap.put("merchantNumber", teamOrganizationServicePackageOneMap.get("merchantNumber") != null ? teamOrganizationServicePackageOneMap.get("merchantNumber").toString() : null);
			merchantNumberMap.put("payKey", teamOrganizationServicePackageOneMap.get("payKey") != null ? teamOrganizationServicePackageOneMap.get("payKey").toString() : null);
			merchantNumberMap.put("wechatId", teamOrganizationServicePackageOneMap.get("wechatId") != null ? teamOrganizationServicePackageOneMap.get("wechatId").toString() : null);
			merchantNumberMap.put("appId", teamOrganizationServicePackageOneMap.get("appId") != null ? teamOrganizationServicePackageOneMap.get("appId").toString() : null);
			merchantNumberMap.put("wechatKey", teamOrganizationServicePackageOneMap.get("wechatKey") != null ? teamOrganizationServicePackageOneMap.get("wechatKey").toString() : null);
			merchantNumberMap.put("price", teamOrganizationServicePackageOneMap.get("price") != null ? teamOrganizationServicePackageOneMap.get("price").toString() : null);
			merchantNumberMap.put("originalPrice", teamOrganizationServicePackageOneMap.get("originalPrice") != null ? teamOrganizationServicePackageOneMap.get("originalPrice").toString() : null);
			return new JsonApi(ApiCode.OK, merchantNumberMap);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param id
	 * @param userServicePackageOrder
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月28日
	 * @version 1.0
	 * @description 确认订单
	 */
	@RequiresAuthentication(ignore = true, value = { " " })
	@RequestMapping(value = { "/user/order/sure/{orderNo}" }, method = RequestMethod.PUT)
	@Transactional
	public JsonApi updateUserSureOrder(@PathVariable("orderNo") String orderNo,
			@Validated({ UserServicePackageOrder.sureOrder.class }) @RequestBody UserServicePackageOrder userServicePackageOrder,BindingResult result) {
		// 订单是否存在
		/* 设置条件 */
		userServicePackageOrder.setOrderNo(orderNo);
		/* 查询订单详情，获取机构服务包ID查询服务列表 */
		Map<String, Object> orderMap = userServicePackageOrderService.getOne(userServicePackageOrder);
		if (MapUtils.isEmpty(orderMap)) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("user.service.package.order.data.empty"));
		}
		 // 微信支付状态
		String wexinTradeState = null;
		/* 价格大于0 查看微信支付状态 */
		if ((((BigDecimal)orderMap.get("price")).compareTo(BigDecimal.ZERO)) > 0) {
			Document docData;
			try {
				logger.info("userServicePackageOrder.getDataXML()-----"+userServicePackageOrder.getData());
				docData = DocumentHelper.parseText(userServicePackageOrder.getData());
				Element rootEltData = docData.getRootElement(); // 获取根节点
				userServicePackageOrder.setAppid(rootEltData.elementText("appid"));
				userServicePackageOrder.setMch_id(rootEltData.elementText("mch_id"));
				userServicePackageOrder.setTransaction_id(rootEltData.elementText("transaction_id"));
				userServicePackageOrder.setNonce_str(rootEltData.elementText("nonce_str"));
				// 生成签名
				String stringA="appid="+userServicePackageOrder.getAppid()+"&mch_id="+userServicePackageOrder.getMch_id()
				+"&nonce_str="+userServicePackageOrder.getNonce_str()+"&transaction_id="+rootEltData.elementText("transaction_id");
				String stringSignTemp=stringA+"&key="+orderMap.get("payKey").toString()+""; //注：key为商户平台设置的密钥key
				String sign=MD5Util.getInstance().getMD5Code(stringSignTemp); //注：MD5签名方式
				userServicePackageOrder.setSign(sign);
			} catch (DocumentException e1) {
				e1.printStackTrace();
				logger.error("DocumentException"+e1.toString());
			}
			try {
				 //发送POST请求 
				URL url = new URL(postUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setUseCaches(false);
				conn.setDoOutput(true);
				StringBuffer dataStr = new StringBuffer();
				String xml = "<xml>"
							+ "<appid>"+userServicePackageOrder.getAppid()+"</appid>"
							+ "<mch_id>"+userServicePackageOrder.getMch_id()+"</mch_id>"
							+ "<nonce_str>"+userServicePackageOrder.getNonce_str()+"</nonce_str>"
							+ "<transaction_id>"+userServicePackageOrder.getTransaction_id()+"</transaction_id>"
							+ "<sign>"+userServicePackageOrder.getSign()+"</sign>"
						+ "</xml>";
				dataStr.append(xml);
				conn.setRequestProperty("Content-Length", "" + dataStr.length());
				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				out.write(dataStr.toString());
				out.flush();
				out.close();
				// 获取响应状态
				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					logger.warn("pay orderquery server fail");
				}
				// 获取响应内容体
				String line, results = "";
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				while ((line = in.readLine()) != null) {
					results += line + "\n";
				}
				in.close();
				Document doc = DocumentHelper.parseText(results);
				Element rootElt = doc.getRootElement(); // 获取根节点
				 //获取  交易状态  : SUCCESS—支付成功 
				wexinTradeState = rootElt.elementText("trade_state");
				logger.info("wexinTradeState-----"+wexinTradeState);
			} catch (IOException | DocumentException e) {
				logger.error(e.getMessage());
			}
		}
		Integer id = (Integer) orderMap.get("id");
		/* 只有状态：0 待支付， 可以进行确认完成订单 */
		int status = Integer.parseInt(orderMap.get("status").toString());
		if (UserStatusCode.UserServicePackageOrder.Waiting.getValue() != status) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.service.package.order.status.error"));
		}
		userServicePackageOrder.setId(id);
		/* 设置完成支付状态：2 已支付 */
		userServicePackageOrder.setStatus(UserStatusCode.UserServicePackageOrder.Success.getValue());
		/* 设置完成时间 */
		userServicePackageOrder.setCompleteDate(new Date());
		// 取有效期类型
		int acquisitiveType = (int) orderMap.get("acquisitiveType");
		// 取有效期
		int acquisitive = (int) orderMap.get("acquisitive");
		/*
		 * 设置过期时间:根据有效期类型 1：按年 :则是指从签约当天起向后顺延12个月 2：按自然年: 自然年就是指到当年的12月31日结束 3：按月 4：按自然月
		 */
		if (acquisitiveType == UserStatusCode.OrganizationPackage.YEAROFNATURE.getValue()) {
			userServicePackageOrder.setExpireDate(DateUtils.getYearOrMonthLastDay(UserStatusCode.OrganizationPackage.YEAR.toString(), acquisitive));
		} else if (acquisitiveType == UserStatusCode.OrganizationPackage.MONTHOFNATURE.getValue()) {
			userServicePackageOrder.setExpireDate(DateUtils.getYearOrMonthLastDay(UserStatusCode.OrganizationPackage.MONTH.toString(), acquisitive));
		} else if (acquisitiveType == UserStatusCode.OrganizationPackage.YEAR.getValue()) {
			userServicePackageOrder.setExpireDate(DateUtils.getYearOrMonthLater(UserStatusCode.OrganizationPackage.YEAR.toString(), acquisitive));
		} else if (acquisitiveType == UserStatusCode.OrganizationPackage.MONTH.getValue()) {
			userServicePackageOrder.setExpireDate(DateUtils.getYearOrMonthLater(UserStatusCode.OrganizationPackage.MONTH.toString(), acquisitive));
		}
		/*价格为0 或者支付成功*/
		if ((orderMap.get("price") != null && (((BigDecimal)orderMap.get("price")).compareTo(BigDecimal.ZERO)) == 0)
				|| wexinTradeState.equals(tradeState)) {
			/* 修改订单状态，过期时间 */
			if (userServicePackageOrderService.updateSureUserServicePackageOrder(userServicePackageOrder) > 0) {
				logger.info("updateSureUserServicePackageOrder-----"+"修改订单状态，过期时间");
				/* 查询服务包内的服务列表 */
				OrganizationPackageService packageService = new OrganizationPackageService();
				packageService.setOrganizationServicePackageId(Integer.parseInt(orderMap.get("organizationServicePackageId").toString()));
				List<Map<String, Object>> serviceList = organizationPackageServiceService.getList(packageService);
				if (CollectionUtils.isNotEmpty(serviceList)) {
					/* 创建用户服务集合:并进行设置值 */
					List<UserService> userServices = new ArrayList<>();
					UserService userService = null;
					for (int i = 0; i < serviceList.size(); i++) {
						userService = new UserService();
						userService.setUserId(Integer.parseInt(orderMap.get("userId").toString()));
						userService.setUserServicePackageOrderId(userServicePackageOrder.getId());
						userService.setServiceTypeId((int) serviceList.get(i).get("serviceTypeId"));
						userService.setTimes((int) serviceList.get(i).get("times"));
						userService.setName(serviceList.get(i).get("name").toString());
						userService.setLockTimes(0);
						userService.setUseTimes(0);
						userService.setCreateTime(new Date());
						userServices.add(userService);
					}
					/* 添加用户服务 */
					if (userServiceService.insertByList(userServices) != userServices.size()) {
						throw new RuntimeException();
					}
				}

				logger.info("userServiceService.insertByList()-----"+"添加用户服务");
				Organization organization = new Organization();
				organization.setId((Integer) orderMap.get("organizationId"));
				Map<String, Object> smsMap = organizationService.getSmsInfo(organization);
				if (null == smsMap || smsMap.isEmpty()) {
					/* 短信不足 订单 备注为机构短信余额不足 */
					UserServicePackageOrder upOrder = new UserServicePackageOrder();
					upOrder.setId(id);
					upOrder.setRemark(Prompt.bundle("organization.sms.num.less.than.one"));
					userServicePackageOrderService.update(upOrder);
					logger.info("短信不足-----");
				} else {
					logger.info("短信足-----");
					int remainder = ((Integer) smsMap.get("totalFrequency")).intValue() - ((Integer) smsMap.get("useFrequency")).intValue();
					Sms sms = new Sms();
					sms.setContent(Prompt.bundle("sms.message", orderMap.get("servicePackageName"), orderMap.get("organizationName"), orderMap.get("doctorTeamName"), orderMap.get("teamPhone")));
					List<Recipient> recipients = new ArrayList<>();
					if (null == orderMap.get("userPhone") || "".equals(orderMap.get("userPhone"))) {
						logger.info("用户电话号码为空，获取组电话-----");
						User user = new User();
						user.setId(Integer.parseInt(orderMap.get("userId").toString()));
						/* 获取家庭组的电话号码 */
						List<Map<String, Object>> phoneList = userServiceSer.getGroupList(user);
						for (Map<String, Object> map : phoneList) {
							if (!map.get("phone").equals(null)) {
								Recipient recipient = new Recipient();
								recipient.setType(RecipientCode.Type.SEND.getValue());
								recipient.setCalledNumber(map.get("phone").toString());
								recipients.add(recipient);
							}
						}
						sms.setRecipients(recipients);
					} else {
						logger.info("用户电话号码不为空，获取用户电话-----");
						Recipient recipient = new Recipient();
						recipient.setType(RecipientCode.Type.SEND.getValue());
						recipient.setCalledNumber(orderMap.get("userPhone").toString());
						recipients.add(recipient);
						sms.setRecipients(recipients);
					}
					sms.setSign(orderMap.get("organizationName").toString());
					/* 根据内容计算需要短信条数 67个字符/条短信 */
					int content = sms.getContent().length() + sms.getSign().length() + 2;
					int needSmsNum = content <= 70 ? 1 : ((int) Math.ceil(content / 67d));
					/* 短信条数大于0推送短信 */
					if (remainder >= needSmsNum * recipients.size()) {
						logger.info("iSmsService.send(sms)-----"+"调用短信服务");
						/* 延迟时间 */
						sms.setTime(0);
						iSmsService.send(sms);
					} else {
						logger.info("短信不足-----");
						/* 短信不足 订单 备注为机构短信余额不足 */
						UserServicePackageOrder upOrder = new UserServicePackageOrder();
						upOrder.setId(id);
						upOrder.setRemark(Prompt.bundle("organization.sms.num.less.than.one"));
						userServicePackageOrderService.update(upOrder);
					}
					
				}
				User user = new User();
				user.setId(Integer.parseInt(orderMap.get("userId").toString()));
				Map<String, Object> userMap = userServiceSer.getWechat(user);
				Map<String, Object> wechatMap = organizationService.getWechatInfo(organization);
				if (null != userMap && null != wechatMap && wechatMap.get("appId")!=null && userMap.get("userOpenId")!= null) {
					logger.info("用户有微信-----");
					/* 推送微信，短信 */
					WechatApi wechatApi = new WechatApi();
					wechatApi.setType(IWechatApi.Type.ONE.getValue());
					wechatApi.setAppId(wechatMap.get("appId").toString());
					wechatApi.setOpenId(userMap.get("userOpenId").toString());
					if (orderMap.get("userName")!= null) {
						wechatApi.setUserName(orderMap.get("userName").toString());
					}else{
						wechatApi.setUserName("-");
					}
					wechatApi.setContent(Prompt.bundle("sure.order.message", orderMap.get("servicePackageName").toString(), orderMap.get("organizationName").toString(),
							orderMap.get("doctorTeamName").toString()));
					/* 通知人-推送人 */
					wechatApi.setNotifier(orderMap.get("doctorTeamName").toString());
					wechatApi.setTime(0);
					wechatApi.setRecordId(0);
					wechatApi.setRemark("");
					/* 推送微信 */
					iWeChatService.insertWechatNews(wechatApi);
					logger.info("iWeChatService.insertWechatNews(wechatApi)-----"+"调用推送微信服务");
				}
				/* 维护团队关系 */
				Map<String, Object> parm = new HashMap<>();
				parm.put("userId", Integer.parseInt(orderMap.get("userId").toString()));
				parm.put("organizationTeamId", Integer.parseInt(orderMap.get("doctorTeamId").toString()));
				parm.put("isIncrementService", true);
				parm.put("time", 0);
				iUserRabbitService.userRabbit(parm);
				logger.info("iUserRabbitService.userRabbit(parm);-----"+"维护团队关系");
				return new JsonApi(ApiCode.OK);
			}
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param id
	 * @param userServicePackageOrder
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年3月28日
	 * @version 1.0
	 * @description 取消订单
	 */
	@RequiresAuthentication(authc = true, value = { " " })
	@RequestMapping(value = { "/user/order/refuse/{id}" }, method = RequestMethod.PUT)
	public JsonApi updateUserRefuseOrder(@PathVariable("id") Integer id, @Validated({ UserServicePackageOrder.refuseOrder.class }) UserServicePackageOrder userServicePackageOrder,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		/* 设置条件 */
		userServicePackageOrder.setId(id);
		/* 查询订单详情，获取机构服务包ID查询服务列表 */
		Map<String, Object> orderMap = userServicePackageOrderService.getOne(userServicePackageOrder);
		if (MapUtils.isEmpty(orderMap)) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("user.service.package.order.data.empty"));
		}
		/* 只有状态：0 待支付， 可以进行取消订单 */
		int status = Integer.parseInt(orderMap.get("status").toString());
		if (UserStatusCode.UserServicePackageOrder.Waiting.getValue() != status) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("user.service.package.order.status.error"));
		}
		/* 设置完成支付状态：1 已取消 */
		userServicePackageOrder.setStatus(UserStatusCode.UserServicePackageOrder.Cancel.getValue());
		/* 修改订单 */
		if (userServicePackageOrderService.updateCancelUserServicePackageOrder(userServicePackageOrder) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
