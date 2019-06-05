package org.module.bone.age.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.module.bone.age.domain.BoneAgeOrder;
import org.module.bone.age.domain.BoneAgeOrderLog;
import org.module.bone.age.domain.BoneAgeStandardPercentile;
import org.module.bone.age.domain.Factor;
import org.module.bone.age.global.BaseGlobal;
import org.module.bone.age.global.BoneAgeOrderStatusCode;
import org.module.bone.age.service.BoneAgeOrderLogService;
import org.module.bone.age.service.BoneAgeOrderService;
import org.module.bone.age.service.HeightForecastService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.service.tools.calculate.CalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;



/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月17日
 * @Version 
 * @Description 骨龄
 */
@RestController
public class BoneAgeOrderController {

	@Autowired
	private BoneAgeOrderService boneAgeOrderService;

	@Autowired
	private BoneAgeOrderLogService boneAgeOrderLogService;

	@Autowired
	private HeightForecastService heightForecastService;
	
	@Resource
	private RedisCacheManager cacheManager;
	
	/**
	 * 
	* @author <font color="green"><b>Chen.Yan</b></font>
	* @param boneAgeOrder
	* @param result
	* @param token
	* @param organizationTeamId
	* @return 
	* @date 2018年5月30日
	* @version 1.0
	* @description  新建骨龄工单信息
	 */
	@RequiresAuthentication(value = { "bone:age:order:insert" },level = Level.OPERATION)
	@RequestMapping(value = { "/bone/age/order" }, method = RequestMethod.POST)
	@Transactional
	public JsonApi insert(@RequestBody @Validated({ BaseEntity.Insert.class }) BoneAgeOrder boneAgeOrder,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId) {
		
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
		Integer organizationUserId = Integer.parseInt(session.get(Map.class).get("id").toString());
		boneAgeOrder.setOrganizationUserId(organizationUserId);
		boneAgeOrder.setOrganizationTeamId(organizationTeamId);
		boneAgeOrder.setStatus(BoneAgeOrderStatusCode.BoneAgeOrder.BONE_AGE_ORDER_GENERATE.getValue());
		boneAgeOrder.setCreateDate(new Date());
		if (boneAgeOrderService.insert(boneAgeOrder) > 0) {
			Map<String, Object> map= new HashMap<>();
			map.put("id", boneAgeOrder.getId());
			Map<String, Object> boneAgeOrderMap=boneAgeOrderService.getOne(boneAgeOrder);
			BoneAgeOrderLog boneAgeOrderLog=JSON.parseObject(JSON.toJSONString(boneAgeOrderMap), BoneAgeOrderLog.class);
			boneAgeOrderLog.setBoneAgeOrderId(boneAgeOrder.getId());
			boneAgeOrderLog.setStatus(BoneAgeOrderStatusCode.BoneAgeOrder.BONE_AGE_ORDER_GENERATE.getValue());
			boneAgeOrderLog.setCreateDate(new Date());
			if (boneAgeOrderLogService.insert(boneAgeOrderLog)>0) {
				return new JsonApi(ApiCode.OK,map);
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param boneAgeOrder
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年5月30日
	 * @version 1.0
	 * @description 保存骨龄每节骨评分 图片（修改）
	 */
	@RequiresAuthentication(value = { "bone:age:order:update" }, level = Level.OPERATION)
	@RequestMapping(value = { "/bone/age/order/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(@PathVariable("id") Integer id,
			@RequestBody @Validated({ BaseEntity.Update.class }) BoneAgeOrder boneAgeOrder, BindingResult result) {
		boneAgeOrder.setId(id);
		boneAgeOrder.setStatus(BoneAgeOrderStatusCode.BoneAgeOrder.BONE_AGE_ORDER_SAVE.getValue());
		if (boneAgeOrderService.update(boneAgeOrder) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param boneAgeOrder
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年5月31日
	 * @version 1.0
	 * @description 骨龄工单列表
	 */
	@RequiresAuthentication(value = { "bone:age:order:list" }, level = Level.OPERATION)
	@RequestMapping(value = { "/bone/age/orders" }, method = RequestMethod.GET)
	public JsonApi getBoneAgeOrderList(@Validated({ BaseEntity.SelectAll.class }) BoneAgeOrder boneAgeOrder,
			BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId) {
		boneAgeOrder.setOrganizationTeamId(organizationTeamId);
		Page<?> page = PageHelper.startPage(boneAgeOrder.getPage(), boneAgeOrder.getPageSize());
		List<Map<String, Object>> boneAgeOrderList = boneAgeOrderService.getBoneAgeOrderList(boneAgeOrder);
		if (boneAgeOrderList != null && !boneAgeOrderList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), boneAgeOrderList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param boneAgeOrder
	 * @param result
	 * @return
	 * @date 2018年5月31日
	 * @version 1.0
	 * @description 保存骨龄骨头所有评测结果
	 */
	@RequiresAuthentication(value = { "bone:age:order:status:update" }, level = Level.OPERATION)
	@RequestMapping(value = { "/bone/age/order/status/{id}" }, method = RequestMethod.PUT)
	@Transactional
	public JsonApi updateBoneAgeOrderStatus(@PathVariable("id") Integer id,
			@Validated({ BaseEntity.Update.class }) BoneAgeOrder boneAgeOrder, BindingResult result) {
		boneAgeOrder.setId(id);
		boneAgeOrder.setStatus(BoneAgeOrderStatusCode.BoneAgeOrder.BONE_AGE_ORDER_SAVE.getValue());
		if (boneAgeOrderService.update(boneAgeOrder) > 0) {
			Map<String, Object> boneAgeOrderMap = boneAgeOrderService.getOne(boneAgeOrder);

			BoneAgeOrderLog boneAgeOrderLog = JSON.parseObject(JSON.toJSONString(boneAgeOrderMap),BoneAgeOrderLog.class);
			boneAgeOrderLog.setBoneAgeOrderId(id);
			boneAgeOrderLog.setStatus(BoneAgeOrderStatusCode.BoneAgeOrder.BONE_AGE_ORDER_SAVE.getValue());
			boneAgeOrderLog.setCreateDate(new Date());
			if (boneAgeOrderLogService.insert(boneAgeOrderLog) > 0) {
				return new JsonApi(ApiCode.OK);
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param boneAgeOrder
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年6月1日
	 * @version 1.0
	 * @description 查询骨龄工单详情
	 */
	@RequiresAuthentication(value = { "bone:age:order:detail" },level = Level.OPERATION)
	@RequestMapping(value = { "/bone/age/order/{id}" }, method = RequestMethod.GET)
	public JsonApi getBoneAgeOrderDeatil(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class }) BoneAgeOrder boneAgeOrder,
			BindingResult result){
		boneAgeOrder.setId(id);
		Map<String, Object>  boneAgeOrderMap=boneAgeOrderService.getOne(boneAgeOrder);
		if (boneAgeOrderMap!=null && !boneAgeOrderMap.isEmpty()) {
			return new JsonApi(ApiCode.OK,boneAgeOrderMap);	
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param standardPercentile
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年5月31日
	 * @version 1.0
	 * @description 获取骨龄标准骨龄分值曲线
	 */
	@RequiresAuthentication( value = { "bone:age:standardPercentile" },level=Level.OPERATION)
	@RequestMapping(value = { "/standard/percentile" }, method = RequestMethod.GET)
	@Cacheable(value=BaseGlobal.BONE_AGE, key="'standard_percentile_' + #standardPercentile.type + #standardPercentile.sex")
	public JsonApi getBoneAgeStandardPercentile(
			@Validated(BoneAgeStandardPercentile.getStandardPercentile.class)BoneAgeStandardPercentile standardPercentile, BindingResult result){
		Map<String, Object> standardPercentileMap = boneAgeOrderService.getBoneAgeStandardPercentile(standardPercentile);
		if (standardPercentileMap!=null && !standardPercentileMap.isEmpty()) {
			return new JsonApi(ApiCode.OK, standardPercentileMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param user
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年5月31日
	 * @version 1.0
	 * @description 返回用户骨龄分值曲线
	 */
	@RequiresAuthentication( value = { "bone:age:userScore" },level=Level.OPERATION)
	@RequestMapping(value = { "/user/score" }, method = RequestMethod.GET)
	public JsonApi getRBoneAgeUserScore(
			@Validated(BoneAgeOrder.getUserScore.class)BoneAgeOrder boneAgeOrder, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_TEAM_ID) Integer organizationTeamId){
		// 根据工单id查询该用户该工单创建时间之前的数据
		Map<String, Object> boneAgeOrderOneResultMap = boneAgeOrderService.getOne(boneAgeOrder);
		if (MapUtils.isNotEmpty(boneAgeOrderOneResultMap)) {
			boneAgeOrder.setUserId(Integer.parseInt(boneAgeOrderOneResultMap.get("userId").toString()));
			boneAgeOrder.setCreateDate((Date)boneAgeOrderOneResultMap.get("createDate"));
		}
		boneAgeOrder.setOrganizationTeamId(organizationTeamId);
		List<Map<String, Object>> userScoreList = boneAgeOrderService.getBoneAgeUserScore(boneAgeOrder);
		if (userScoreList != null && !userScoreList.isEmpty()) {
			return new JsonApi(ApiCode.OK, userScoreList);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param boneAgeOrder
	 * @param result
	 * @return
	 * @date 2018年6月1日
	 * @version 1.0
	 * @description TW2法R骨测试结果(TW2-CHN13骨法测试结果)
	 */
	@RequiresAuthentication( value = { "bone:age:tw2r:result" },level=Level.OPERATION)
	@RequestMapping(value = { "/tw2/test/result" }, method = RequestMethod.GET)
	public JsonApi getTW2RBoneTestResult(
			@Validated(BoneAgeOrder.getTW2RBoneTestResult.class)BoneAgeOrder boneAgeOrder, BindingResult result){
		//查询工单详情
		Map<String, Object>  boneAgeOrderMap = boneAgeOrderService.getOne(boneAgeOrder);
		if(MapUtils.isNotEmpty(boneAgeOrderMap)){
			Integer sex = Integer.valueOf(boneAgeOrderMap.get("sex").toString());
			Float age = (float) CalculateUtil.round(Double.valueOf(boneAgeOrderMap.get("age").toString()), 0);
			boneAgeOrder.setScoreTableType("R");
			Map<String, Object> testResult = boneAgeOrderService.getTW2BoneTestResult(boneAgeOrder);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Double boneAge = testResult.get("boneAge") == null ? 0 : Double.valueOf(testResult.get("boneAge").toString());
			if((sex == 1 && age >= 6 && age <= 17) || (sex == 2 && age >= 5 && age <= 15)){
				//因素法预测身高
				Integer userId = boneAgeOrderMap.get("userId") == null ? 0 : Integer.valueOf(boneAgeOrderMap.get("userId").toString());
				Integer menarcheType = StringUtils.isBlank(boneAgeOrderMap.get("menarcheType").toString()) ? null : Integer.valueOf(boneAgeOrderMap.get("menarcheType").toString());
				Double height = boneAgeOrderMap.get("height") == null ? 0 : Double.valueOf(boneAgeOrderMap.get("height").toString());
				Double menarcheAge = boneAgeOrderMap.get("menarcheAge") == null ? 0 : Double.valueOf(boneAgeOrderMap.get("menarcheAge").toString());
				Factor factor = new Factor();
				factor.setSex(sex);
				factor.setAge(age);
				factor.setUserId(userId);
				factor.setHeight(height);
				factor.setMenarcheAge(menarcheAge);
				factor.setBoneAge(boneAge);
				factor.setMenarcheType(menarcheType);
				resultMap = heightForecastService.getHeightForecast(factor);
				resultMap.put("boneAge", boneAge);
			}else {
				resultMap.put("boneAge", boneAge);
				resultMap.put("height", 0);
				resultMap.put("heightMax", 0);
				resultMap.put("heightMin", 0);
			}
			return new JsonApi(ApiCode.OK,resultMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param boneAgeOrder
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年6月21日
	 * @version 1.0
	 * @description TW2法T骨测试结果(TW2-CHN20骨法测试结果)
	 */
	@RequiresAuthentication( value = { "bone:age:tw2t:result" },level=Level.OPERATION)
	@RequestMapping(value = { "/tw2t/test/result" }, method = RequestMethod.GET)
	public JsonApi getTW2TBoneTestResult(
			@Validated(BoneAgeOrder.getTW2TBoneTestResult.class)BoneAgeOrder boneAgeOrder, BindingResult result){
		//查询工单详情
		Map<String, Object>  boneAgeOrderMap = boneAgeOrderService.getOne(boneAgeOrder);
		if(MapUtils.isNotEmpty(boneAgeOrderMap)){
			boneAgeOrder.setIsTW2T(true);
			boneAgeOrder.setScoreTableType("T");
			Map<String, Object> testResultMap = boneAgeOrderService.getTW2BoneTestResult(boneAgeOrder);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Double boneAge = testResultMap.get("boneAge") == null ? 0 : Double.valueOf(testResultMap.get("boneAge").toString());
				resultMap.put("boneAge", boneAge);
			return new JsonApi(ApiCode.OK,resultMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param boneAgeOrder
	 * @param result
	 * @param token
	 * @param organizationTeamId
	 * @return
	 * @date 2018年6月1日
	 * @version 1.0
	 * @description 保存骨龄检测结果
	 */
	@RequiresAuthentication( value = { "bone:age:order:check:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/bone/age/order/{id}/check" }, method = RequestMethod.PUT)
	@Transactional
	public JsonApi updateBoneAgeOrderCheck(@PathVariable("id")Integer id,
			@RequestBody @Validated(BoneAgeOrder.updateCheckWorkOrder.class)BoneAgeOrder boneAgeOrder, BindingResult result){
		boneAgeOrder.setId(id);
		boneAgeOrder.setStatus(BoneAgeOrderStatusCode.BoneAgeOrder.BONE_AGE_ORDER_FINISH.getValue());
		//如果医生没有传预测身高，则设置默认值
		if(boneAgeOrder.getDoctorForecastHeight() == null){
			boneAgeOrder.setDoctorForecastHeight(boneAgeOrder.getForecastHeight());
		}
		//如果医生没有传预测最小身高，则设置默认值
		if(boneAgeOrder.getDoctorMinForecastHeight() == null){
			boneAgeOrder.setDoctorMinForecastHeight(boneAgeOrder.getMinForecastHeight());
		}
		//如果医生没有传预测最大身高，则设置默认值
		if(boneAgeOrder.getDoctorMaxForecastHeight() == null){
			boneAgeOrder.setDoctorMaxForecastHeight(boneAgeOrder.getMaxForecastHeight());
		}
		if (boneAgeOrderService.updateBoneAgeOrder(boneAgeOrder) > 0) {
			Map<String, Object> boneAgeOrderMap = boneAgeOrderService.getOne(boneAgeOrder);
			BoneAgeOrderLog boneAgeOrderLog=JSON.parseObject(JSON.toJSONString(boneAgeOrderMap), BoneAgeOrderLog.class);
			boneAgeOrderLog.setBoneAgeOrderId(id);
			boneAgeOrderLog.setStatus(BoneAgeOrderStatusCode.BoneAgeOrder.BONE_AGE_ORDER_FINISH.getValue());
			if (boneAgeOrderLogService.insert(boneAgeOrderLog)>0) {
				return new JsonApi(ApiCode.OK);
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
