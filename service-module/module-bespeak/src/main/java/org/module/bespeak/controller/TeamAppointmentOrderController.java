package org.module.bespeak.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.bespeak.domain.ServiceTimeout;
import org.module.bespeak.domain.TeamAppointmentConfigureDetail;
import org.module.bespeak.domain.TeamAppointmentOrder;
import org.module.bespeak.domain.User;
import org.module.bespeak.domain.UserService;
import org.module.bespeak.domain.WechatApi;
import org.module.bespeak.global.BaseGlobal;
import org.module.bespeak.global.IWechatApi;
import org.module.bespeak.global.ServiceType;
import org.module.bespeak.global.TeamAppointmentConfigure.CycleNum;
import org.module.bespeak.global.TeamAppointmentOrder.Status;
import org.module.bespeak.message.Prompt;
import org.module.bespeak.service.TeamAppointmentConfigureDetailService;
import org.module.bespeak.service.TeamAppointmentOrderService;
import org.module.bespeak.service.UserServiceService;
import org.module.bespeak.task.IAppointmentService;
import org.module.bespeak.task.IWeChatService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.service.tools.date.DateUtils;
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
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年10月11日
 * @Version 
 * @Description 用户预约订单
 */
@RestController
public class TeamAppointmentOrderController {
	@Resource
	private TeamAppointmentOrderService teamAppointmentOrderService;
	@Resource
	private TeamAppointmentConfigureDetailService teamAppointmentConfigureDetailService;
	@Resource
	private UserServiceService userServiceService;
	@Resource
	private RedisCacheManager cacheManager;
	@Autowired
	private IWeChatService iWeChatService;
	@Autowired
	private IAppointmentService appointmentService;
	@Autowired
	private org.module.bespeak.service.UserService userServices;
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param TeamAppointmentOrder
	* @param result
	* @return 
	* @date 2018年10月11日
	* @version 1.0
	* @description 用户组预约列表
	*/
	@RequiresAuthentication(authc = true, value = {})
	@RequestMapping(value = { "/team/appointment/orders" }, method = RequestMethod.GET)
	public JsonApi getList(@RequestHeader(value = BaseGlobal.TOKEN_FLAG) String token,@Validated({ BaseEntity.SelectAll.class })  TeamAppointmentOrder TeamAppointmentOrder,BindingResult result){
		// 从缓存中获取用户
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		// 登录用户的id
		Integer operationUserIdId = (Integer) session.get(Map.class).get("id");
		TeamAppointmentOrder.setUserId(operationUserIdId);
		Page<?> page = PageHelper.startPage(TeamAppointmentOrder.getPage(), TeamAppointmentOrder.getPageSize());
		List<Map<String, Object>> TeamAppointmentOrdersList = teamAppointmentOrderService.getList(TeamAppointmentOrder);
		if (CollectionUtils.isNotEmpty(TeamAppointmentOrdersList)) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),TeamAppointmentOrdersList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param teamAppointmentOrder
	* @param result
	* @return 
	* @date 2018年10月12日
	* @version 1.0
	* @description 预约
	*/
	@Transactional
	@RequiresAuthentication(authc = true, value = {})
	@RequestMapping(value = { "/team/appointment/order" }, method = RequestMethod.POST)
	public JsonApi insert(@RequestBody @Validated({ BaseEntity.Insert.class })  TeamAppointmentOrder teamAppointmentOrder,BindingResult result){
		teamAppointmentOrder.setStatus(Status.PENDINGNUMBER.getValue());
		/* 限制时间只能在当前到7天后的时间 */
		if (!DateUtils.isEffectiveDate(teamAppointmentOrder.getAppointmentDate(),new Date(),DateUtils.getDayAfter(CycleNum.WEEK.getValue()))) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("appointment.date.can.only.be.within.one.week"));
		}
		/* 查询团队预约配置详情是否存在*/
		TeamAppointmentConfigureDetail teamAppointmentConfigureDetail = new TeamAppointmentConfigureDetail();
		teamAppointmentConfigureDetail.setId(teamAppointmentOrder.getTeamAppointmentConfigureDetailId());
		Map<String, Object> appointmentConfigureDetailMap = teamAppointmentConfigureDetailService.getOne(teamAppointmentConfigureDetail);
		if (MapUtils.isEmpty(appointmentConfigureDetailMap)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("team.appointment.configure.detail.is.not.exists"));
		}
		UserService userService = new UserService();
		userService.setId(teamAppointmentOrder.getUserServiceId());
		//根据服务类型id查询用户服务详情 
		Map<String, Object> userServiceMap = userServiceService.getOne(userService);
		if(MapUtils.isEmpty(userServiceMap)){
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.userService.unexist"));
		}
		/* 判断服务次数是否大于0 */
		int times = Integer.parseInt(userServiceMap.get("availableTimes").toString());
		if(times <= 0){
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.service.times.less.than.zero"));
		}
		
		/* 判断是否有未取号的预约 */
		Map<String, Object> appointmentMap = teamAppointmentOrderService.getOne(teamAppointmentOrder);
		if (MapUtils.isNotEmpty(appointmentMap)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.appointment.is.exists"));
		}
		/* 查询预约号 */
		Map<String, Object> appointmentNoMap = teamAppointmentOrderService.getAppointmentNo(teamAppointmentOrder);
		teamAppointmentOrder.setAppointmentNo(Integer.parseInt(appointmentNoMap.get("appointmentNo").toString()));
		teamAppointmentOrder.setCreateDate(new Date());
		if (teamAppointmentOrderService.insert(teamAppointmentOrder) > 0) {
			if (userServiceService.lockTime(userService) > 0) {
				/*写入队列预约时间的第二天*/
				ServiceTimeout serviceTimeout = new ServiceTimeout();
				serviceTimeout.setId(teamAppointmentOrder.getId());
				serviceTimeout.setType(ServiceType.APPOINTMENT);
				/* 设置时间为当前时间到预约时间的第二天的秒数 */
				serviceTimeout.setTime(DateUtils.calLastedTimes(new Date(), DateUtils.getDateDayAfter(teamAppointmentOrder.getAppointmentDate(), 1)));
				appointmentService.sendTimeout(serviceTimeout);
				/*推送微信*/
				User user = new User();
				user.setId(teamAppointmentOrder.getUserId());
				
				Map<String, Object> userWechatMap = userServices.getWechat(user);
				if (appointmentConfigureDetailMap.get("appId") != null && userWechatMap != null && userWechatMap.get("userOpenId") != null) {
					WechatApi wechatApi= new WechatApi();
					wechatApi.setType(IWechatApi.Type.ONE.getValue());
					wechatApi.setAppId(appointmentConfigureDetailMap.get("appId").toString());
					wechatApi.setOpenId(userWechatMap.get("userOpenId").toString());
					if (userWechatMap.get("userName") != null) {
						wechatApi.setUserName(userWechatMap.get("userName").toString());
					}else{
						wechatApi.setUserName("-");
					}
					String appointmentDate = new SimpleDateFormat("yyyy-MM-dd").format(teamAppointmentOrder.getAppointmentDate())
							+" "+appointmentConfigureDetailMap.get("startDate").toString()+"~"
							+appointmentConfigureDetailMap.get("endDate").toString();
					wechatApi.setContent(Prompt.bundle("appointment.message", appointmentDate,appointmentConfigureDetailMap.get("serviceName").toString(),appointmentNoMap.get("appointmentNo").toString() ));
					/*通知人-推送人*/
					wechatApi.setNotifier(appointmentConfigureDetailMap.get("teamName").toString());
					wechatApi.setTime(0);
					wechatApi.setRecordId(0);
					wechatApi.setRemark("");
					/*预约成功推送微信*/
					iWeChatService.insertWechatNews(wechatApi);
					/* 预约时间在后天及7天后这个区间  推送预约时间的前一天的10:00:00 */
					if (DateUtils.isEffectiveDate(teamAppointmentOrder.getAppointmentDate(),DateUtils.getDayAfter(1),DateUtils.getDayAfter(CycleNum.WEEK.getValue()))) {
						wechatApi.setContent(Prompt.bundle("appointment.tips.message",appointmentConfigureDetailMap.get("organizatioName").toString(),appointmentConfigureDetailMap.get("serviceName").toString(), appointmentDate ));
						/* 计算 当前时间  到  预约时间的前一天的10:00:00相差的秒数*/
						wechatApi.setTime((int)DateUtils.calLastedTimes(new Date(),DateUtils.getDateDayBefore(teamAppointmentOrder.getAppointmentDate(), 1)) );
						/*预约提醒推送微信*/
						iWeChatService.insertWechatNews(wechatApi);
					}
				}
				return new JsonApi(ApiCode.OK);
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param teamAppointmentOrder
	* @param result
	* @return 
	* @date 2018年10月11日
	* @version 1.0
	* @description 取消订单
	*/
	@RequiresAuthentication(authc = true, value = { " " })
	@RequestMapping(value = { "/team/appointment/order/{id}" }, method = RequestMethod.GET)
	public JsonApi cancelOrder(@PathVariable("id") Integer id, @Validated({ BaseEntity.Update.class })  TeamAppointmentOrder teamAppointmentOrder,BindingResult result){
		teamAppointmentOrder.setId(id);
		Map<String, Object>  teamAppointmentOrderMap = teamAppointmentOrderService.getOne(teamAppointmentOrder);
		if (MapUtils.isNotEmpty(teamAppointmentOrderMap)) {
			if (Status.FETCHEDNUMBER.getValue() == (int)teamAppointmentOrderMap.get("status")) {
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("status.is.fetched.number.cannot.cancle"));
			}
			teamAppointmentOrder.setStatus(Status.CANCEL.getValue());
			if (teamAppointmentOrderService.cancelOrder(teamAppointmentOrder) > 0) {
				return new JsonApi(ApiCode.OK);
			}
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	
}
