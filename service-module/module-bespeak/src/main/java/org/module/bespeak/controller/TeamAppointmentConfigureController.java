package org.module.bespeak.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.bespeak.domain.TeamAppointmentConfigure;
import org.module.bespeak.global.TeamAppointmentConfigure.Cycle;
import org.module.bespeak.global.TeamAppointmentConfigure.CycleNum;
import org.module.bespeak.global.TeamAppointmentConfigure.Status;
import org.module.bespeak.service.TeamAppointmentConfigureService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.tools.calc.DateUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年10月10日
 * @Version 
 * @Description 团队预约配置
 */
@RestController
public class TeamAppointmentConfigureController {
	
	@Resource
	private TeamAppointmentConfigureService teamAppointmentConfigureService;
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param teamAppointmentConfigure
	* @param result
	* @return 
	* @date 2018年10月10日
	* @version 1.0
	* @description 预约详情(暂定查7天的数据，修改天数只需要修改CycleNum.WEEK.getValue())
	*/
	@RequiresAuthentication(authc = true, value = { " " })
	@RequestMapping(value = { "/team/appointment/configure" }, method = RequestMethod.GET)
	public JsonApi getList(@Validated({ BaseEntity.SelectOne.class })  TeamAppointmentConfigure teamAppointmentConfigure,BindingResult result){
		/* 查询后7天的团队可预约配置列表 
		 * 同一团队同一服务同一时间段同一状态只有一条配置数据*/
		teamAppointmentConfigure.setStatus(Status.ENABLE.getValue());
		teamAppointmentConfigure.setCycle(Cycle.EVERYDAY.getValue());
		/* 先查询是否有每天的配置 */
		Map<String, Object> teamAppointmentConfigureMap = teamAppointmentConfigureService.getOne(teamAppointmentConfigure);
		if (MapUtils.isNotEmpty(teamAppointmentConfigureMap)) {
			int cycle = (Integer)teamAppointmentConfigureMap.get("cycle");
			if (Cycle.CUSTOM.getValue() == cycle) {
				teamAppointmentConfigure.setStartDate(new Date());
				teamAppointmentConfigure.setEndDate(DateUtils.getDayAfter(CycleNum.WEEK.getValue()));
				teamAppointmentConfigureMap = teamAppointmentConfigureService.getOne(teamAppointmentConfigure);
			}
			TeamAppointmentConfigure appointmentConfigure = new TeamAppointmentConfigure();
			appointmentConfigure.setId((Integer) teamAppointmentConfigureMap.get("id"));
			appointmentConfigure.setStatus(Status.ENABLE.getValue());
			List<Map<String, Object>> appointmentWeekMap = new ArrayList<>();
			// 查询每天是否可预约
			for (int i = 1; i <= CycleNum.WEEK.getValue(); i++) {
				appointmentConfigure.setStrAppointmentDate(new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.getDayAfter(i)));
				appointmentConfigure.setAppointmentDate(DateUtils.getDayAfter(i));
				Map<String, Object> appointmentDetailMap = new HashMap<>();
				appointmentDetailMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.getDayAfter(i)));
				/* 预约周期类型为:每天 直接查询预约配置详情统计一天的总号数
				 * 预约周期类型为:自定义 需要查询预约时间在预约配置范围内的数据*/
				if (Cycle.CUSTOM.getValue() == cycle) {
					appointmentConfigure.setCycle(Cycle.CUSTOM.getValue());
				}
				/* 是否可预约详情 */
				List<Map<String, Object>> appointmentDetailList = teamAppointmentConfigureService.getAppointmentDetail(appointmentConfigure);
				if (CollectionUtils.isNotEmpty(appointmentDetailList)) {
					/* 统计总号数和总预约数 */
					int sumTotal = 0;
					int sumAppointmentedNum = 0;
					for (Map<String, Object> map : appointmentDetailList) {
						sumTotal += new Integer(map.get("total").toString());
						sumAppointmentedNum += new Integer(map.get("appointmentedNum").toString());
					}
					appointmentDetailMap.put("appointmentTotal", sumTotal > sumAppointmentedNum ? "有号" : "已满");
					appointmentDetailMap.put("appointmentDetailList", appointmentDetailList);
				}else if (CollectionUtils.isEmpty(appointmentDetailList)) {
					// 数据为空表示没有配置，提示：无号
					appointmentDetailMap.put("appointmentTotal", "无号");
				}
				appointmentWeekMap.add(appointmentDetailMap);
			}
			teamAppointmentConfigureMap.put("appointmentWeekMap",appointmentWeekMap);
			return new JsonApi(ApiCode.OK,teamAppointmentConfigureMap);
		}else{
			teamAppointmentConfigure.setCycle(Cycle.CUSTOM.getValue());
			teamAppointmentConfigure.setStartDate(new Date());
			teamAppointmentConfigure.setEndDate(DateUtils.getDayAfter(CycleNum.WEEK.getValue()));
			/* 查询预约配置列表 */
			List<Map<String, Object>> teamAppointmentConfigureList = teamAppointmentConfigureService.getList(teamAppointmentConfigure);
			Map<String, Object> resultMap = new HashMap<>();
			if (CollectionUtils.isEmpty(teamAppointmentConfigureList)) {
				return new JsonApi(ApiCode.NOT_FOUND);
			}else{

				List<Integer> ids =new ArrayList<>();
				for (Map<String, Object> configMap : teamAppointmentConfigureList) {
					ids.add((Integer)configMap.get("id"));
				}
				TeamAppointmentConfigure appointmentConfigure = new TeamAppointmentConfigure();
				appointmentConfigure.setId(null);
				appointmentConfigure.setIds(ids);
				appointmentConfigure.setStatus(Status.ENABLE.getValue());
				List<Map<String, Object>> appointmentWeekMap = new ArrayList<>();
				// 查询每天是否可预约
				for (int i = 1; i <= CycleNum.WEEK.getValue(); i++) {
					appointmentConfigure.setStrAppointmentDate(new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.getDayAfter(i)));
					appointmentConfigure.setAppointmentDate(DateUtils.getDayAfter(i));
					Map<String, Object> appointmentDetailMap = new HashMap<>();
					appointmentDetailMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.getDayAfter(i)));
					/* 预约周期类型为:每天 直接查询预约配置详情统计一天的总号数
					 * 预约周期类型为:自定义 需要查询预约时间在预约配置范围内的数据*/
					appointmentConfigure.setCycle(Cycle.CUSTOM.getValue());
					/* 是否可预约详情 */
					List<Map<String, Object>> appointmentDetailList = teamAppointmentConfigureService.getAppointmentDetail(appointmentConfigure);
					if (CollectionUtils.isNotEmpty(appointmentDetailList)) {
						/* 统计总号数和总预约数 */
						int sumTotal = 0;
						int sumAppointmentedNum = 0;
						for (Map<String, Object> map : appointmentDetailList) {
							sumTotal += new Integer(map.get("total").toString());
							sumAppointmentedNum += new Integer(map.get("appointmentedNum").toString());
						}
						appointmentDetailMap.put("appointmentTotal", sumTotal > sumAppointmentedNum ? "有号" : "已满");
						appointmentDetailMap.put("appointmentDetailList", appointmentDetailList);
					}else if (CollectionUtils.isEmpty(appointmentDetailList)) {
						// 数据为空表示没有配置，提示：无号
						appointmentDetailMap.put("appointmentTotal", "无号");
					}
					appointmentWeekMap.add(appointmentDetailMap);
				}
				resultMap.put("appointmentWeekMap",appointmentWeekMap);
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}
	}
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param id
	* @param teamAppointmentConfigure
	* @param result
	* @return 
	* @date 2018年10月11日
	* @version 1.0
	* @description 是否可预约详情
	*/
	/*@RequiresAuthentication(authc = true, value = { " " })
	@RequestMapping(value = { "/team/appointment/configures" }, method = RequestMethod.GET)*/
	/*public JsonApi getOne(@PathVariable("id") Integer id, @Validated({ BaseEntity.SelectOne.class })  TeamAppointmentConfigure teamAppointmentConfigure,BindingResult result){
		teamAppointmentConfigure.setId(id);
		teamAppointmentConfigure.setStatus(Status.ENABLE.getValue());
		Map<String, Object> resultMap = new HashMap<>();
		if (DateUtils.isBeforeNow(teamAppointmentConfigure.getAppointmentDate())) {
			Map<String, Object> teamAppointmentConfigureMap = teamAppointmentConfigureService.getOne(teamAppointmentConfigure);
			if (MapUtils.isNotEmpty(teamAppointmentConfigureMap)) {
				int cycle = (Integer)teamAppointmentConfigureMap.get("cycle");
				 预约周期类型为:每天 直接查询预约配置详情统计一天的总号数
				 * 预约周期类型为:自定义 需要查询预约时间在预约配置范围内的数据
				if (Cycle.CUSTOM.getValue() == cycle) {
					teamAppointmentConfigure.setCycle(Cycle.CUSTOM.getValue());
				}
				 是否可预约详情 
				List<Map<String, Object>> appointmentDetailList = teamAppointmentConfigureService.getAppointmentDetail(teamAppointmentConfigure);
				if (CollectionUtils.isNotEmpty(appointmentDetailList)) {
					 统计总号数和总预约数 
					int sumTotal = 0;
					int sumAppointmentedNum = 0;
					for (Map<String, Object> map : appointmentDetailList) {
						sumTotal += new Integer(map.get("total").toString());
						sumAppointmentedNum += new Integer(map.get("appointmentedNum").toString());
					}
					resultMap.put("appointmentTotal", sumTotal > sumAppointmentedNum ? "有号" : "已满");
					resultMap.put("appointmentDetailList", appointmentDetailList);
					return new JsonApi(ApiCode.OK,resultMap);
				}
			}
		}
		// 数据为空表示没有配置，提示：无号
		resultMap.put("appointmentTotal", "无号");
		return new JsonApi(ApiCode.NOT_FOUND,resultMap);
	}*/
	
	
}
