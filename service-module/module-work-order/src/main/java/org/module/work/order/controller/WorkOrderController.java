package org.module.work.order.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.work.order.domain.UserService;
import org.module.work.order.domain.WorkOrder;
import org.module.work.order.domain.WorkOrderLog;
import org.module.work.order.global.BaseGlobal;
import org.module.work.order.global.SignStatusCode;
import org.module.work.order.global.SignStatusCode.WorkOrderStatus;
import org.module.work.order.message.Prompt;
import org.module.work.order.service.UserServiceService;
import org.module.work.order.service.WorkOrderLogService;
import org.module.work.order.service.WorkOrderService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.service.tools.calc.DateUtils;
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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月15日
 * @Version 
 * @Description 工单
 */
@RestController
public class WorkOrderController {
	
	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private UserServiceService userServiceService;
	@Autowired
	private WorkOrderLogService workOrderLogService;
	@Resource
	private RedisCacheManager cacheManager;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param workOrder
	* @param result
	* @return 
	* @date 2018年3月27日
	* @version 1.0
	* @description 发起工单
	*/
	@Transactional
	@RequiresAuthentication(authc = true, value = { "work.order:workOrder:insert" })
	@RequestMapping(value = { "/work/order" }, method = RequestMethod.POST)
	public JsonApi insert(@RequestBody @Validated({ BaseEntity.Insert.class })  WorkOrder workOrder,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token){
		/* 获取用户缓存信息 */
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		Integer operationUserId = (Integer) session.get(Map.class).get("id");
		// 判断服务是否存在
		UserService userService = new UserService();
		// 设置用户服务类型id
		userService.setId(workOrder.getUserServiceId());
		//根据服务类型id查询用户服务详情 
		Map<String, Object> userServiceOneRestltMap = userServiceService.getOne(userService);
		if(MapUtils.isEmpty(userServiceOneRestltMap)){
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.userService.unexist"));
		}
		// 判断用户购买的服务包是否过期
		if (!DateUtils.isBeforeNow((Date)userServiceOneRestltMap.get("expireDate"))) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.service.package.order.is.expired"));
		}
		// 查询用户是否存在未完成的相同服务的工单
		WorkOrder notFinishWorkOrder =new WorkOrder();
		notFinishWorkOrder.setUserId(workOrder.getUserId());
		notFinishWorkOrder.setUserServiceId(workOrder.getUserServiceId());
		Integer[] statuss = new Integer[]{WorkOrderStatus.PENDINGRESPONSE.getValue(),
				WorkOrderStatus.TOBEEXECUTED.getValue(),
				WorkOrderStatus.INTHEEXECUTION.getValue()
			};
		notFinishWorkOrder.setStatuss(statuss);
		// 查询未完成的相同服务的工单
		List<Map<String, Object>> notFinishWorkordersList = workOrderService.getList(notFinishWorkOrder);
		if (CollectionUtils.isNotEmpty(notFinishWorkordersList)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("exists.user.workorder.is.not.finished"));
		}
		// 剩余次数
		int times = Integer.parseInt(userServiceOneRestltMap.get("availableTimes").toString());
		/* 判断服务次数是否大于0 */
		if(times <= 0){
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("user.service.times.less.than.zero"));
		}
		// TODO 数据权限控制 判断被操作者是否是操作者的组成员
		
		// 设置发起人id
		workOrder.setLaunchUserId(operationUserId);
		workOrder.setNumber(MD5Util.getInstance().getOrderNo());
		workOrder.setOrganizationTeamId(Integer.parseInt(userServiceOneRestltMap.get("doctorTeamId").toString()));
		workOrder.setOrderSources(WorkOrderStatus.ORDERRESOURCEONLINEWECHAT.getValue());
		workOrder.setStatus(WorkOrderStatus.PENDINGRESPONSE.getValue());
		workOrder.setCreateDate(new Date());
		workOrder.setUpdateDate(new Date());
		// 添加工单
		if(workOrderService.insert(workOrder) > 0){
			// 锁定次数+1 且 总次数-1
			if(userServiceService.updateLockTime(userService) > 0){
				//添加日志
				WorkOrderLog workOrderLog = new WorkOrderLog();
				workOrderLog.setOperationUserId(operationUserId);
				workOrderLog.setLogType(WorkOrderStatus.WORKORDERLOGTYPEOPERATIONLOG.getValue());
				workOrderLog.setLaunchType(WorkOrderStatus.WORKORDERLOGTYPEUSERSPONSOR.getValue());
				workOrderLog.setWorkOrderId(workOrder.getId());
				workOrderLog.setStatus(WorkOrderStatus.PENDINGRESPONSE.getValue());
				workOrderLog.setCreateDate(new Date());
				if(workOrderLogService.insert(workOrderLog) > 0){
					return new JsonApi(ApiCode.OK);
				}
			}
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param workOrder
	* @param result
	* @param token
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 用户工单列表
	*/
	@RequiresAuthentication(authc = true, value = { "sign:workOrder:list" })
	@RequestMapping(value = { "/work/orders" }, method = RequestMethod.GET)
	public JsonApi getList(@Validated({ BaseEntity.SelectAll.class })  WorkOrder workOrder,BindingResult result){
		// TODO 数据权限控制 判断被操作者是否是操作者的组成员
		Page<?> page = PageHelper.startPage(workOrder.getPage(), workOrder.getPageSize());
		// 查询用户工单列表
		List<Map<String, Object>> workOrdersResult = workOrderService.getListByUserId(workOrder);
		if (CollectionUtils.isNotEmpty(workOrdersResult)) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),workOrdersResult));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param workOrder
	* @param result
	* @return 
	* @date 2018年3月27日
	* @version 1.0
	* @description 查询每天预约的人数
	*/
	@RequiresAuthentication(authc = true, value = { "sign:sign:insert" })
	@RequestMapping(value = { "/work/order/reservation/number" }, method = RequestMethod.GET)
	public JsonApi getReservationNumber( @Validated({ WorkOrder.GetReservationNumber.class })  WorkOrder workOrder,BindingResult result){
		// TODO 数据权限控制 判断被操作者是否是操作者的组成员
		Page<?> page = PageHelper.startPage(workOrder.getPage(), workOrder.getPageSize());
		// 查询每天预约的人数
		List<Map<String, Object>> workOrdersResultList = workOrderService.getReservationNumber(workOrder);
		if (CollectionUtils.isNotEmpty(workOrdersResultList)) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),workOrdersResultList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param id
	 * @param workOrder
	 * @param result
	 * @param token
	 * @return
	 * @date 2018年5月4日
	 * @version 1.0
	 * @description 用户评价工单
	 */
	@Transactional
	@RequiresAuthentication(authc = true, value = { "sign:workOrder:update" })
	@RequestMapping(value = { "/work/order/{id}" }, method = RequestMethod.PUT)
	public JsonApi updateUserWorkOrder(@PathVariable("id")Integer id,
			@RequestBody @Validated({ BaseEntity.Update.class })  WorkOrder workOrder,BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token){
		/* 获取用户缓存信息 */
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		// 登录用户的id
		Integer operationUserId = (Integer) session.get(Map.class).get("id");
		workOrder.setId(id);
		//查询工单明细
		Map<String, Object> workOrderMap = workOrderService.getWorkOrderDetail(workOrder);
		if(MapUtils.isEmpty(workOrderMap)){
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("workorder.is.not.exists"));
		}
		// 工单状态为  已执行（待评价）:5-才能评价
		if(Integer.valueOf(workOrderMap.get("status").toString()) != WorkOrderStatus.EXECUTED.getValue()){
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("workorder.status.fail"));
		}
		workOrder.setEvaluateDate(new Date());
		workOrder.setUpdateDate(new Date());
		workOrder.setStatus(SignStatusCode.WorkOrderStatus.EVALUATED.getValue());
		//用户评价工单
		if (workOrderService.updateUserWorkOrder(workOrder) > 0) {
			//添加日志
			WorkOrderLog workOrderLog = new WorkOrderLog();
			workOrderLog.setOperationUserId(operationUserId);
			workOrderLog.setLogType(WorkOrderStatus.WORKORDERLOGTYPEOPERATIONLOG.getValue());
			workOrderLog.setLaunchType(WorkOrderStatus.WORKORDERLOGTYPEUSERSPONSOR.getValue());
			workOrderLog.setWorkOrderId(id);
			workOrderLog.setStatus(WorkOrderStatus.EVALUATED.getValue());
			workOrderLog.setCreateDate(new Date());
			if(workOrderLogService.insert(workOrderLog) > 0){
				return new JsonApi(ApiCode.OK);
			}
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
