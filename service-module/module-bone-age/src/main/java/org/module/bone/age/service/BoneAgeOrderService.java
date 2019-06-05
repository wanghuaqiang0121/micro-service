package org.module.bone.age.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.module.bone.age.dao.BoneAgeOrderMapper;
import org.module.bone.age.domain.BoneAgeOrder;
import org.module.bone.age.domain.BoneAgeStandardPercentile;
import org.module.bone.age.global.BaseGlobal;
import org.service.tools.calculate.CalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoneAgeOrderService {
	@Autowired
	private BoneAgeOrderMapper boneAgeOrderMapper;

	public int insert(BoneAgeOrder eAgeOrder) {
		return boneAgeOrderMapper.insert(eAgeOrder);
	}

	public int update(BoneAgeOrder eAgeOrder) {
		return boneAgeOrderMapper.update(eAgeOrder);
	}

	public Map<String, Object> getOne(BoneAgeOrder eAgeOrder) {
		return boneAgeOrderMapper.getOne(eAgeOrder);
	}

	public List<Map<String, Object>> getBoneAgeOrderList(BoneAgeOrder eAgeOrder) {
		return boneAgeOrderMapper.getList(eAgeOrder);
	}

	public Map<String, Object> getBoneAgeStandardPercentile(BoneAgeStandardPercentile standardPercentile){
		Map<String, Object> map = new HashMap<String, Object>();
		//查询3百分位
		standardPercentile.setPercentile(BaseGlobal.THREE_PERCENTILE);
		map.put("threePercentile", boneAgeOrderMapper.getBoneAgeStandardPercentileList(standardPercentile));
		//查询10百分位
		standardPercentile.setPercentile(BaseGlobal.TEN_PERCENTILE);
		map.put("tenPercentile", boneAgeOrderMapper.getBoneAgeStandardPercentileList(standardPercentile));
		//查询25百分位
		standardPercentile.setPercentile(BaseGlobal.TWENTY_FIVE_PERCENTILE);
		map.put("twentyFivePercentile", boneAgeOrderMapper.getBoneAgeStandardPercentileList(standardPercentile));
		//查询50百分位
		standardPercentile.setPercentile(BaseGlobal.FIFTY_PERCENTILE);
		map.put("fiftyPercentile", boneAgeOrderMapper.getBoneAgeStandardPercentileList(standardPercentile));
		//查询70百分位
		standardPercentile.setPercentile(BaseGlobal.SEVENTY_FIVE_PERCENTILE);
		map.put("seventyFivePercentile", boneAgeOrderMapper.getBoneAgeStandardPercentileList(standardPercentile));
		//查询90百分位
		standardPercentile.setPercentile(BaseGlobal.NINETY_PERCENTILE);
		map.put("ninetyPercentile", boneAgeOrderMapper.getBoneAgeStandardPercentileList(standardPercentile));
		//查询97百分位
		standardPercentile.setPercentile(BaseGlobal.NINETY_SEVEN_PERCENTILE);
		map.put("ninetySevenPercentile", boneAgeOrderMapper.getBoneAgeStandardPercentileList(standardPercentile));
		return map;
	}
	
	
	public List<Map<String, Object>> getBoneAgeUserScore(BoneAgeOrder boneAgeOrder){
		//根据用户ID和时间查询工单列表
		List<Map<String, Object>> orderList = boneAgeOrderMapper.getBoneAgeUserScoreList(boneAgeOrder);
		if(orderList != null && orderList.size()>0){
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(int i=0; i<orderList.size(); i++){
				Map<String, Object> map = new HashMap<String, Object>();
				//获取用户骨龄总分
				if(BaseGlobal.TW2CHN20TW2T.equals(orderList.get(i).get("algorithm").toString().trim())){
					boneAgeOrder.setIsTW2T(true);
					boneAgeOrder.setScoreTableType("T");
				}else{
					boneAgeOrder.setScoreTableType("R");
				}
				boneAgeOrder.setId(Integer.valueOf(orderList.get(i).get("id").toString()));
				boneAgeOrder.setSex(Integer.valueOf(orderList.get(0).get("sex").toString()));
				Map<String, Object> totalPointsMap = boneAgeOrderMapper.getBoneTotalPoints(boneAgeOrder);
				//设置分数
				map.put("score", CalculateUtil.boneAgeGraphical(Double.valueOf(totalPointsMap.get("totalPoints").toString())));
				//计算年龄，工单创建时间-用户生日=月龄，月龄/12=保留一位小数
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Calendar c1 = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					c1.setTime(sdf.parse(orderList.get(i).get("birthday").toString()));
					c2.setTime(sdf.parse(orderList.get(i).get("createDate").toString()));
					int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
					BigDecimal monthAge = new BigDecimal(year*12 + c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH));
					map.put("age", monthAge.divide(new BigDecimal(12), 1, BigDecimal.ROUND_DOWN));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				list.add(map);
			}
			return list;
		}
		return null;
	}
	
	public Map<String, Object> getTW2BoneTestResult(BoneAgeOrder boneAgeOrder){
		Map<String, Object> orderMap = boneAgeOrderMapper.getOne(boneAgeOrder);
		if(orderMap!=null && orderMap.size()>0){
			//骨龄
			double boneAge = 0;
			//计算用户当前的骨得分
			//获取用户骨龄总分
			boneAgeOrder.setSex(Integer.valueOf(orderMap.get("sex").toString()));
			Map<String, Object> totalPointsMap = boneAgeOrderMapper.getBoneTotalPoints(boneAgeOrder);
			//计算骨龄总分
			Integer score = Integer.valueOf(totalPointsMap.get("totalPoints").toString());
			//查询R骨龄标准百分位数表是否有准确的骨龄数据
			BoneAgeStandardPercentile standardPercentile = new BoneAgeStandardPercentile();
			standardPercentile.setBoneAgeFraction(score);
			standardPercentile.setSex(Integer.valueOf(orderMap.get("sex").toString()));
			standardPercentile.setPercentile(BaseGlobal.FIFTY_PERCENTILE);
			standardPercentile.setType(boneAgeOrder.getScoreTableType());
			List<Map<String, Object>> standardList = boneAgeOrderMapper.getBoneAgeStandardPercentileList(standardPercentile);
			if(standardList != null && standardList.size() == 1){
				boneAge = Double.valueOf(standardList.get(0).get("boneAge").toString());
			}else{
				standardPercentile.setBoneAgeFraction(null);
				standardPercentile.setAdjacentValues(score);
				standardList = boneAgeOrderMapper.getBoneAgeStandardPercentileList(standardPercentile);
				if(standardList != null && standardList.size() == 2){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("Atop", standardList.get(0).get("boneAgeFraction"));
					map.put("Adown", standardList.get(1).get("boneAgeFraction"));
					map.put("Btop", standardList.get(0).get("boneAge"));
					map.put("Bdown", standardList.get(1).get("boneAge"));
					map.put("D", score);
					boneAge = CalculateUtil.boneAge(map);
				}
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("boneAge", boneAge);
			return resultMap;
		}
		return null;
	}
	
	public int updateBoneAgeOrder(BoneAgeOrder boneAgeOrder){
		return boneAgeOrderMapper.update(boneAgeOrder);
	}
	
}
