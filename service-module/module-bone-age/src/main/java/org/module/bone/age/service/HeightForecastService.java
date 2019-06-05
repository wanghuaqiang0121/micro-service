package org.module.bone.age.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.bone.age.calculate.FactorMethod;
import org.module.bone.age.dao.BoneAgeOrderMapper;
import org.module.bone.age.dao.HeightForecastFactorTableMapper;
import org.module.bone.age.domain.BoneAgeOrder;
import org.module.bone.age.domain.Factor;
import org.module.bone.age.domain.HeightForecastFactorTable;
import org.module.bone.age.global.BaseGlobal;
import org.service.tools.calculate.CalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年6月1日
 * @Version 
 * @Description 预测身高
 */
@Service
public class HeightForecastService {
	
	@Autowired
	private BoneAgeOrderMapper boneAgeOrderMapper;
	@Autowired
	private HeightForecastFactorTableMapper heightForecastFactorTableMapper;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param m
	* @return 
	* @date 2018年6月1日
	* @version 1.0
	* @description 因素法预测身高
	*/
	public Map<String, Object> getHeightForecast(Factor factor){
		BoneAgeOrder boneAgeOrder = new BoneAgeOrder();
		boneAgeOrder.setUserId(factor.getUserId());
		boneAgeOrder.setStartCreateDate(CalculateUtil.getYearsAgo(1.12));
		boneAgeOrder.setEndCreateDate(CalculateUtil.getYearsAgo(0.88));
		// 查询用户0.88-1.12年前的工单最新的一条
		Map<String, Object> userBoneAgeOrderMap = boneAgeOrderMapper.getBoneAgeOrdersByYearsAgo(boneAgeOrder);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//当男孩有0.88-1.12年前的身高记录时做成年身高预测，11-18.5岁 ，可以使用4因素法。
		//当女孩有0.88-1.12年前的身高记录时做成年身高预测，8-16.5岁 ，可以使用4因素法。
		if ((factor.getSex() == BaseGlobal.SEX_MAN && factor.getAge() >= 11 && factor.getAge() <= 18.5 && MapUtils.isNotEmpty(userBoneAgeOrderMap))
				||(factor.getSex() == BaseGlobal.SEX_WOMAN && factor.getAge() >= 8 && factor.getAge() <= 16.5 && MapUtils.isNotEmpty(userBoneAgeOrderMap))) {
			// 查询4因素法的参数
			HeightForecastFactorTable heightForecastFactorTable = new HeightForecastFactorTable();
			heightForecastFactorTable.setSex(BaseGlobal.SEX_MAN);
			heightForecastFactorTable.setAge(factor.getAge());
			heightForecastFactorTable.setType(BaseGlobal.HEIGHT_FORECAST_FACTOR_TYPE_FOUR);
			heightForecastFactorTable = heightForecastFactorTableMapper.getDetali(heightForecastFactorTable);
			if (null == heightForecastFactorTable) {
				resultMap.put("height", 0);
				resultMap.put("heightMax",0 );
				resultMap.put("heightMin", 0);
				return resultMap;
			}
			double orderHeight = Double.parseDouble(userBoneAgeOrderMap.get("height").toString());
			factor.setDeltaH(CalculateUtil.sub(factor.getHeight(), orderHeight));
			// 调用4因素法
			return FactorMethod.fourFactorMethod(factor, heightForecastFactorTable);
		}else if(factor.getSex() == BaseGlobal.SEX_WOMAN && factor.getAge() >= 10 && factor.getAge() <= 15 && MapUtils.isNotEmpty(userBoneAgeOrderMap)){
		//当女孩有0.88-1.12年前的身高和骨龄数据时做成年身高预测，10-15岁可以用5因素法。
			// 查询5因素法的参数
			HeightForecastFactorTable heightForecastFactorTable = new HeightForecastFactorTable();
			heightForecastFactorTable.setSex(BaseGlobal.SEX_MAN);
			heightForecastFactorTable.setAge(factor.getAge());
			heightForecastFactorTable.setType(BaseGlobal.HEIGHT_FORECAST_FACTOR_TYPE_FIVE);
			heightForecastFactorTable = heightForecastFactorTableMapper.getDetali(heightForecastFactorTable);
			if (null == heightForecastFactorTable) {
				resultMap.put("height", 0);
				resultMap.put("heightMax",0 );
				resultMap.put("heightMin", 0);
				return resultMap;
			}
			double orderH = Double.parseDouble(userBoneAgeOrderMap.get("height").toString());
			double orderR = Double.parseDouble(userBoneAgeOrderMap.get("boneAge").toString());
			factor.setDeltaH(CalculateUtil.sub(factor.getHeight(), orderH));
			factor.setDeltaR(CalculateUtil.sub(factor.getBoneAge(), orderR));
			// 调用5因素法
			return FactorMethod.fiveFactorMethod(factor, heightForecastFactorTable);
		}else{
		//除去以上的3种情况，男孩女孩都应该用3因素法做成年身高预测
			// 查询3因素法的参数
			HeightForecastFactorTable heightForecastFactorTable = new HeightForecastFactorTable();
			heightForecastFactorTable.setSex(factor.getSex() == BaseGlobal.SEX_MAN ? BaseGlobal.SEX_MAN : BaseGlobal.SEX_WOMAN);
			// 3因素法的年龄是整数：需要把年龄转成整数
			heightForecastFactorTable.setAge((float)CalculateUtil.round(factor.getAge(),0));
			heightForecastFactorTable.setType(BaseGlobal.HEIGHT_FORECAST_FACTOR_TYPE_THREE);
			// 根据初潮类型不同查询不同身高预测因素法数据
			if (null != factor.getMenarcheType()) {
				heightForecastFactorTable.setMenarcheType(factor.getMenarcheType());
				// 初潮已有：设置初潮已有类型
				if (factor.getMenarcheType() == BaseGlobal.MENARCHE_TYPE_TEO && factor.getMenarcheAge() > 0 && factor.getSex() == BaseGlobal.SEX_WOMAN) {
					heightForecastFactorTable.setMenarcheType(BaseGlobal.MENARCHE_TYPE_TEO);
					// 年龄小于12且初潮年龄在12-16，则：年龄用初潮年龄去查询身高预测因素法数据
					if (factor.getAge() < 12 && factor.getMenarcheAge() >= 12 && factor.getMenarcheAge() <= 16) {
						heightForecastFactorTable.setAge((float)CalculateUtil.round(factor.getMenarcheAge(),0));
					}else{
						resultMap.put("height", 0);
						resultMap.put("heightMax",0);
						resultMap.put("heightMin", 0);
						return resultMap;
					}// 3因数法无法获取参数
				}else if ((factor.getMenarcheType() == BaseGlobal.MENARCHE_TYPE_ONE && factor.getSex() == BaseGlobal.SEX_WOMAN && factor.getAge() < 5) 
						|| (factor.getMenarcheType() == BaseGlobal.MENARCHE_TYPE_ONE && factor.getSex() == BaseGlobal.SEX_WOMAN && factor.getAge() > 14) 
						|| (factor.getMenarcheType() == BaseGlobal.MENARCHE_TYPE_THREE && factor.getSex() == BaseGlobal.SEX_WOMAN && factor.getAge() < 12) 
						|| (factor.getMenarcheType() == BaseGlobal.MENARCHE_TYPE_THREE && factor.getSex() == BaseGlobal.SEX_WOMAN && factor.getAge() > 16) ) {
					resultMap.put("height", 0);
					resultMap.put("heightMax",0);
					resultMap.put("heightMin", 0);
					return resultMap;
				}
			}
			// 查询身高预测因素法数据
			heightForecastFactorTable = heightForecastFactorTableMapper.getDetali(heightForecastFactorTable);
			if (null == heightForecastFactorTable) {
				resultMap.put("height", 0);
				resultMap.put("heightMax",0 );
				resultMap.put("heightMin", 0);
				return resultMap;
			}
			// 调用3因素法
			return FactorMethod.threeFactorMethod(factor, heightForecastFactorTable);
		}
	}
}
