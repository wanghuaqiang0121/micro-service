package org.module.bone.age.calculate;

import java.util.HashMap;
import java.util.Map;

import org.module.bone.age.domain.Factor;
import org.module.bone.age.domain.HeightForecastFactorTable;
import org.module.bone.age.domain.HeightPercentileConvertTable;
import org.module.bone.age.global.BaseGlobal;
import org.module.bone.age.service.HeightPercentileConvertTableService;
import org.module.bone.age.tools.SpringContextUtils;
import org.service.tools.calculate.CalculateUtil;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年6月1日
 * @Version 
 * @Description 因素法
 */

public class FactorMethod {
	
	private static HeightPercentileConvertTableService heightPercentileConvertTableService = 
			SpringContextUtils.getBean(HeightPercentileConvertTableService.class);
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param m
	* @return 
	* @date 2018年6月1日
	* @version 1.0
	* @description 三因素法预测身高
	*/
	public static Map<String, Object> threeFactorMethod(Factor factor,HeightForecastFactorTable heightForecastFactorTable){
		// y`是成年身高预测值的转换值，下面用y表示。H’是当前身高的转换值，下面用H表示，G是当前年龄（岁），R是当前R骨龄（岁），M是月经初潮年龄（岁）
        // a:身高系数、b:年龄系数、c:R骨龄系数、d:初潮年龄系数、K:相应系数或常数
		double G=CalculateUtil.round(factor.getAge(),2);
		double R=factor.getBoneAge();
		double a=CalculateUtil.round(heightForecastFactorTable.getHeightCoefficient(),2);
		double b=CalculateUtil.round(heightForecastFactorTable.getAgeCoefficient(),2);
		double c=CalculateUtil.round(heightForecastFactorTable.getrBoneCoefficient(),2);
		
		double K=heightForecastFactorTable.getConstant();
		// 标准差
		double standardDeviation=CalculateUtil.round(heightForecastFactorTable.getStandardDeviation(),2);
		// 实际值转换为转换值
		HeightPercentileConvertTable heightPercentileConvertTable = new HeightPercentileConvertTable();
		heightPercentileConvertTable.setAge(G >= 18 ? 18 : (int)CalculateUtil.round(G,0));
		heightPercentileConvertTable.setSex(factor.getSex());
		if (heightPercentileConvertTable.getSex().equals(BaseGlobal.SEX_WOMAN)) {
			heightPercentileConvertTable.setAge(G > 15 ? 18 : (int)CalculateUtil.round(G,0));
		}
		heightPercentileConvertTable.setActual(Float.parseFloat(factor.getHeight().toString()));
		// 求转换值
		double H=heightPercentileConvertTableService.getActualToConvert(heightPercentileConvertTable);
		Map<String, Object> map = new HashMap<String, Object>();
		// 计算成年身高预测值的转换值
		if (factor.getSex()== BaseGlobal.SEX_WOMAN && factor.getMenarcheAge() > 0) {
			double d=CalculateUtil.round(heightForecastFactorTable.getMenarcheCoefficient(),2);
			double M=factor.getMenarcheAge();
			// 初潮己有的女孩: y`=aH’+bG+cR+dM+K
			double heightConvert = CalculateUtil.round(
					CalculateUtil.add(
						CalculateUtil.add(
							CalculateUtil.add(
								CalculateUtil.add(CalculateUtil.mul(a, H), CalculateUtil.mul(b, G)),
								CalculateUtil.mul(c, R)),
							CalculateUtil.mul(d, M)),
						K),
					2);
			// 95%可能范围的转换值y`±2*标准差
			// y`+2*标准差
			double heightMaxConvert = CalculateUtil.round(CalculateUtil.add(heightConvert,CalculateUtil.mul(2, standardDeviation)),2);
			// y`-2*标准差
			double heightMinConvert = CalculateUtil.round(CalculateUtil.sub(heightConvert,CalculateUtil.mul(2, standardDeviation)),2);
			HeightPercentileConvertTable heightPercentileConvertTableActual = new HeightPercentileConvertTable();
			heightPercentileConvertTableActual.setAge(G >= 18 ? 18 : (int)CalculateUtil.round(G,0));
			heightPercentileConvertTableActual.setSex(factor.getSex());
			heightPercentileConvertTableActual.setConvert((float)heightConvert);
			map.put("height", heightPercentileConvertTableService.getConvertToActual(heightPercentileConvertTableActual));
			heightPercentileConvertTableActual.setConvert((float)heightMaxConvert);
			map.put("heightMax", heightPercentileConvertTableService.getConvertToActual(heightPercentileConvertTableActual));
			heightPercentileConvertTableActual.setConvert((float)heightMinConvert);
			map.put("heightMin", heightPercentileConvertTableService.getConvertToActual(heightPercentileConvertTableActual));
			return map;
		}else {
			// 其他:y`=aH’+bG+cR+K
			double heightConvert = CalculateUtil.round(
					CalculateUtil.add(
						CalculateUtil.add(
							CalculateUtil.add(CalculateUtil.mul(a, H), CalculateUtil.mul(b, G)),
							CalculateUtil.mul(c, R)),
						K),
					2);
			// 95%可能范围的转换值y`±2*标准差
			// y`+2*标准差
			double heightMaxConvert = CalculateUtil.round(CalculateUtil.add(heightConvert,CalculateUtil.mul(2, standardDeviation)),2);
			// y`-2*标准差
			double heightMinConvert = CalculateUtil.round(CalculateUtil.sub(heightConvert,CalculateUtil.mul(2, standardDeviation)),2);
			// 转换值转换为实际值
			HeightPercentileConvertTable heightPercentileConvertTableConvert = new HeightPercentileConvertTable();
			heightPercentileConvertTableConvert.setAge(G >= 18 ? 18 : (int)CalculateUtil.round(G,0));
			heightPercentileConvertTableConvert.setSex(factor.getSex());
			heightPercentileConvertTableConvert.setConvert((float)heightConvert);
			map.put("height", heightPercentileConvertTableService.getConvertToActual(heightPercentileConvertTableConvert));
			heightPercentileConvertTableConvert.setConvert((float)heightMaxConvert);
			map.put("heightMax", heightPercentileConvertTableService.getConvertToActual(heightPercentileConvertTableConvert));
			heightPercentileConvertTableConvert.setConvert((float)heightMinConvert);
			map.put("heightMin", heightPercentileConvertTableService.getConvertToActual(heightPercentileConvertTableConvert));
			return map;
		}
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param m
	 * @return 
	 * @date 2018年6月1日
	 * @version 1.0
	 * @description 四因素法预测身高
	 */
	public static Map<String, Object> fourFactorMethod(Factor factor,HeightForecastFactorTable heightForecastFactorTable){
		// y为成年身高预测值，
		// H:当前的身高、G:年龄（岁）、R骨龄（岁），
		// △H、△R分别为上1年（0.88-1.12年，或1年前后6周内）的身高增加数、R骨龄增加数；
		// a、b、c、d、e为有关因素的系数，K为常数，
		double H=factor.getHeight();
		double G=CalculateUtil.round(factor.getAge(),2);
		double R=factor.getBoneAge();
		double a=CalculateUtil.round(heightForecastFactorTable.getHeightCoefficient(),2);
		double b=CalculateUtil.round(heightForecastFactorTable.getAgeCoefficient(),2);
		double c=CalculateUtil.round(heightForecastFactorTable.getrBoneCoefficient(),2);
		double d=CalculateUtil.round(heightForecastFactorTable.getLastYearHeightAddCoefficient(),2);
		//double e=heightForecastFactorTable.getLastYearRBoneAddCoefficient();
		// △H:上1年（0.88-1.12年，或1年前后6周内）的身高增加数 从工单中查询记录，再用现在实际的减去工单的
		double deltaH=factor.getDeltaH();
		double K=heightForecastFactorTable.getConstant();
		// 标准差
		double standardDeviation=CalculateUtil.round(heightForecastFactorTable.getStandardDeviation(),2);
		Map<String, Object> map = new HashMap<String, Object>();
		//4因素和5因素法的H变量使用的是真实身高而非转换值，因此无需做转换，直接计算即可
		// 4因素:y =aH+bG+cR+d△H+K
		double height = CalculateUtil.round(
				CalculateUtil.add(
					CalculateUtil.add(
						CalculateUtil.add(
							CalculateUtil.add(CalculateUtil.mul(a, H), CalculateUtil.mul(b, G)),
							CalculateUtil.mul(c, R)),
						CalculateUtil.mul(d, deltaH)),
					K),
				2);
		// 95%可能范围的转换值y`±2*标准差
		// y`+2*标准差
		double heightMax = CalculateUtil.round(CalculateUtil.add(height,CalculateUtil.mul(2, standardDeviation)),2);
		// y`-2*标准差
		double heightMin = CalculateUtil.round(CalculateUtil.sub(height,CalculateUtil.mul(2, standardDeviation)),2);
		map.put("height", height);
		map.put("heightMax", heightMax);
		map.put("heightMin", heightMin);
		return map;
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param m
	 * @return 
	 * @date 2018年6月1日
	 * @version 1.0
	 * @description 五因素法预测身高
	 */
	public static Map<String, Object> fiveFactorMethod(Factor factor,HeightForecastFactorTable heightForecastFactorTable){
		// y为成年身高预测值，
		// H:当前的身高、G:年龄（岁）、R骨龄（岁），
		// △H、△R分别为上1年（0.88-1.12年，或1年前后6周内）的身高增加数、R骨龄增加数；
		// a、b、c、d、e为有关因素的系数，K为常数，
		double H=factor.getHeight();
		double G=CalculateUtil.round(factor.getAge(),2);
		double R=factor.getBoneAge();
		double a=CalculateUtil.round(heightForecastFactorTable.getHeightCoefficient(),2);
		double b=CalculateUtil.round(heightForecastFactorTable.getAgeCoefficient(),2);
		double c=CalculateUtil.round(heightForecastFactorTable.getrBoneCoefficient(),2);
		double d=CalculateUtil.round(heightForecastFactorTable.getLastYearHeightAddCoefficient(),2);
		double e=CalculateUtil.round(heightForecastFactorTable.getLastYearRBoneAddCoefficient(),2);
		// △H:上1年（0.88-1.12年，或1年前后6周内）的身高增加数 从工单中查询记录，再用现在实际的减去工单的
		double deltaH=factor.getDeltaH();
		double deltaR=factor.getDeltaR();
		double K=heightForecastFactorTable.getConstant();
		// 标准差
		double standardDeviation=CalculateUtil.round(heightForecastFactorTable.getStandardDeviation(),2);
		Map<String, Object> map = new HashMap<String, Object>();
		//4因素和5因素法的H变量使用的是真实身高而非转换值，因此无需做转换，直接计算即可
		// 4因素:y =aH+bG+cR+d△H+K
		// 5因素:y =aH+bG+cR+d△H+e△R+K
		double height = CalculateUtil.round(
				CalculateUtil.add(
					CalculateUtil.add(
						CalculateUtil.add(
							CalculateUtil.add(
									CalculateUtil.add(CalculateUtil.mul(a, H), CalculateUtil.mul(b, G)),
									CalculateUtil.mul(c, R)),
							CalculateUtil.mul(d, deltaH)),
					    CalculateUtil.mul(e, deltaR)),
					K),
				2);
		// 95%可能范围的转换值y`±2*标准差
		// y`+2*标准差
		double heightMax = CalculateUtil.round(CalculateUtil.add(height,CalculateUtil.mul(2, standardDeviation)),2);
		// y`-2*标准差
		double heightMin = CalculateUtil.round(CalculateUtil.sub(height,CalculateUtil.mul(2, standardDeviation)),2);
		map.put("height", height);
		map.put("heightMax", heightMax);
		map.put("heightMin", heightMin);
		return map;
	}
	
}
