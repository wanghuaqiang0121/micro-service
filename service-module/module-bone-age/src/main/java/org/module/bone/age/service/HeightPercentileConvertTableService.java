package org.module.bone.age.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.module.bone.age.dao.HeightPercentileConvertTableMapper;
import org.module.bone.age.domain.HeightPercentileConvertTable;
import org.module.bone.age.global.HeightPercentileConvertTableStatusCode;
import org.service.tools.calculate.CalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeightPercentileConvertTableService {

	@Autowired
	private HeightPercentileConvertTableMapper heightPercentileConvertTableMapper;

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param actual
	 * @return
	 * @date 2018年6月4日
	 * @version 1.0
	 * @description 身高实际值求转换值
	 */
	public Double getActualToConvert(HeightPercentileConvertTable heightPercentileConvertTable) {
		Float actual = heightPercentileConvertTable.getActual();
		List<Map<String, Object>> heightPercentileConvertTableList= heightPercentileConvertTableMapper.getActualToConvert(heightPercentileConvertTable);
		if (heightPercentileConvertTableList != null && heightPercentileConvertTableList.size() > 0) {
			Map<String, Object> heightPercentileConvertTableMax = heightPercentileConvertTableList.get(0);
			Map<String, Object> heightPercentileConvertTableMin = heightPercentileConvertTableList.get(1);
			Float actualMax = (Float) heightPercentileConvertTableMax.get("actual");
			Float actualMin = (Float) heightPercentileConvertTableMin.get("actual");
			// 实际身高超过最大值
			if (actual > actualMax) {
				Float convertMax = (Float) heightPercentileConvertTableMax.get("convert");
				Map<String, Object> heightPercentileConvertTableMaxMap = new HashMap<>();
				heightPercentileConvertTableMaxMap.put("convertBercentile", convertMax);
				heightPercentileConvertTableMaxMap.put("actualBercentile", actualMax);
				heightPercentileConvertTableMaxMap.put("actual", actual);
				double proportionConvertValue = CalculateUtil
						.proportionConvertValue(heightPercentileConvertTableMaxMap);
				return proportionConvertValue;
			}
			// 实际身高小于最小身高
			if (actual < actualMin) {
				Float convertMin = (Float) heightPercentileConvertTableMin.get("convert");
				Map<String, Object> heightPercentileConvertTableMaxMap = new HashMap<>();
				heightPercentileConvertTableMaxMap.put("convertBercentile", convertMin);
				heightPercentileConvertTableMaxMap.put("actualBercentile", actualMin);
				heightPercentileConvertTableMaxMap.put("actual", actual);
				double proportionConvertValue = CalculateUtil
						.proportionConvertValue(heightPercentileConvertTableMaxMap);
				return proportionConvertValue;
			}
			// 判断是否能直接查询到转换值
			heightPercentileConvertTable.setCondition(
					HeightPercentileConvertTableStatusCode.HeightPercentileConvertTable.BE_EQUAL_TO.getValue());
			List<Map<String, Object>> heightPercentileConvertTableEqualList = heightPercentileConvertTableMapper
					.getActualToConvert(heightPercentileConvertTable);
			// 如果直接找到转换值，直接返回
			if (heightPercentileConvertTableEqualList != null && heightPercentileConvertTableEqualList.size() > 0) {
				Map<String, Object> heightPercentileConvertTableEqualMap = heightPercentileConvertTableEqualList.get(0);
				Double convertEqual = Double.valueOf(heightPercentileConvertTableEqualMap.get("convert").toString());
				return convertEqual;
			}
			// 利用插入法计算身高转换
			heightPercentileConvertTable.setCondition(
					HeightPercentileConvertTableStatusCode.HeightPercentileConvertTable.BOTH_SIDES.getValue());
			List<Map<String, Object>> heightPercentileConvertTableSidesList= heightPercentileConvertTableMapper
					.getActualToConvert(heightPercentileConvertTable);
			//利用插入法公式计算转换值
			if (heightPercentileConvertTableSidesList != null && heightPercentileConvertTableSidesList.size() >0) {
				Map<String, Object> heightPercentileConvertTableSidesMaxMap = heightPercentileConvertTableSidesList.get(0);
				Map<String, Object> heightPercentileConvertTableSidesMinMap = heightPercentileConvertTableSidesList.get(1);
				Map<String, Object> heightPercentileConvertTableSidesMap = new HashMap<>();
				// 转换值小convertSmall、转换值大convertBig、实际值小actualSmall、实际值大actualBig
				heightPercentileConvertTableSidesMap.put("convertBig", heightPercentileConvertTableSidesMaxMap.get("convert"));
				heightPercentileConvertTableSidesMap.put("convertSmall", heightPercentileConvertTableSidesMinMap.get("convert"));
				heightPercentileConvertTableSidesMap.put("actualBig", heightPercentileConvertTableSidesMaxMap.get("actual"));
				heightPercentileConvertTableSidesMap.put("actualSmall", heightPercentileConvertTableSidesMinMap.get("actual"));
				heightPercentileConvertTableSidesMap.put("actual", heightPercentileConvertTable.getActual());
				double insertMethodheightConvertValue = CalculateUtil.insertMethodheightConvertValue(heightPercentileConvertTableSidesMap);
				return insertMethodheightConvertValue;
			}
		}
		return (Double) null;
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param heightPercentileConvertTable
	* @return 
	* @date 2018年6月4日
	* @version 1.0
	* @description 身高转换值求实际值
	*/
	public Double getConvertToActual(HeightPercentileConvertTable heightPercentileConvertTable) {
		
		Float convert = heightPercentileConvertTable.getConvert();
		// 身高转换值求实际值：转换值是成年后的转换值所以下面的数据是查询成年的数据
		// 查询成年的数据
		HeightPercentileConvertTable heightPercentileConvertTableadult = new HeightPercentileConvertTable();
		heightPercentileConvertTableadult.setAge(18);
		heightPercentileConvertTableadult.setSex(heightPercentileConvertTable.getSex());
		List<Map<String, Object>> heightPercentileConvertTableResultMax = heightPercentileConvertTableMapper
				.getConvertToActual(heightPercentileConvertTableadult);
		Map<String, Object> heightPercentileConvertTableMaxAdultMap = heightPercentileConvertTableResultMax.get(0);
		Map<String, Object> heightPercentileConvertTableMinAdultMap = heightPercentileConvertTableResultMax.get(1);
		
		Float convertMax = (Float) heightPercentileConvertTableMaxAdultMap.get("convert");
		Float convertMin = (Float) heightPercentileConvertTableMinAdultMap.get("convert");
		// 实际身高超过最大值
		if (convert > convertMax) {
			Float actualMax = (Float) heightPercentileConvertTableMaxAdultMap.get("actual");
			Map<String, Object> heightPercentileConvertTableMaxMap = new HashMap<>();
			heightPercentileConvertTableMaxMap.put("convertBercentile", convertMax);
			heightPercentileConvertTableMaxMap.put("actualBercentile", actualMax);
			heightPercentileConvertTableMaxMap.put("convert", convert);
			 double proportionActualValue = CalculateUtil
					.proportionActualValue(heightPercentileConvertTableMaxMap);
			return proportionActualValue;
		}
		// 实际身高小于最小身高
		if (convert < convertMin) {
			Float actualMin = (Float) heightPercentileConvertTableMinAdultMap.get("actual");
			Map<String, Object> heightPercentileConvertTableMaxMap = new HashMap<>();
			heightPercentileConvertTableMaxMap.put("convertBercentile", convertMin);
			heightPercentileConvertTableMaxMap.put("actualBercentile", actualMin);
			heightPercentileConvertTableMaxMap.put("convert", convert);
			 double proportionActualValue = CalculateUtil
					.proportionActualValue(heightPercentileConvertTableMaxMap);
			return proportionActualValue;
		}
		heightPercentileConvertTable.setAge(18);
		// 判断是否能直接查询到实际值
		heightPercentileConvertTable.setCondition(
				HeightPercentileConvertTableStatusCode.HeightPercentileConvertTable.BE_EQUAL_TO.getValue());
		List<Map<String, Object>> heightPercentileConvertTableEqualResultList = heightPercentileConvertTableMapper
				.getConvertToActual(heightPercentileConvertTable);
		// 如果直接找到转换值，直接返回
		if (heightPercentileConvertTableEqualResultList != null && heightPercentileConvertTableEqualResultList.size() > 0) {
			Map<String, Object> heightPercentileConvertTableEqualMap = heightPercentileConvertTableEqualResultList.get(0);
			Double actualEqual = Double.valueOf(heightPercentileConvertTableEqualMap.get("actual").toString());
			return actualEqual;
		}
		// 利用插入法计算身高实际值
		heightPercentileConvertTable.setCondition(
				HeightPercentileConvertTableStatusCode.HeightPercentileConvertTable.BOTH_SIDES.getValue());
		List<Map<String, Object>> heightPercentileConvertTableSidesResultList = heightPercentileConvertTableMapper
				.getConvertToActual(heightPercentileConvertTable);
		//利用插入法公式计算转换值
		if (heightPercentileConvertTableSidesResultList != null && heightPercentileConvertTableSidesResultList.size() >0) {
			Map<String, Object> heightPercentileConvertTableSidesMaxMap = heightPercentileConvertTableSidesResultList.get(0);
			Map<String, Object> heightPercentileConvertTableSidesMinMap = heightPercentileConvertTableSidesResultList.get(1);
			Map<String, Object> heightPercentileConvertTableSidesMap = new HashMap<>();
			// 转换值小convertSmall、转换值大convertBig、实际值小actualSmall、实际值大actualBig
			heightPercentileConvertTableSidesMap.put("convertBig", heightPercentileConvertTableSidesMaxMap.get("convert"));
			heightPercentileConvertTableSidesMap.put("convertSmall", heightPercentileConvertTableSidesMinMap.get("convert"));
			heightPercentileConvertTableSidesMap.put("actualBig", heightPercentileConvertTableSidesMaxMap.get("actual"));
			heightPercentileConvertTableSidesMap.put("actualSmall", heightPercentileConvertTableSidesMinMap.get("actual"));
			heightPercentileConvertTableSidesMap.put("convert", heightPercentileConvertTable.getConvert());
			 double insertMethodheightActualValue = CalculateUtil.insertMethodheightActualValue(heightPercentileConvertTableSidesMap);
			return insertMethodheightActualValue;
		}
		return (Double) null;
	}
}
