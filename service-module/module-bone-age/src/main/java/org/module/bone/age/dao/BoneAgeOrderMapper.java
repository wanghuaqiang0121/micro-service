package org.module.bone.age.dao;

import java.util.List;
import java.util.Map;

import org.module.bone.age.domain.BoneAgeOrder;
import org.module.bone.age.domain.BoneAgeStandardPercentile;
import org.service.core.dao.IBaseMapper;

public interface BoneAgeOrderMapper extends IBaseMapper<BoneAgeOrder> {

	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param standardPercentile
	 * @return
	 * @date 2018年6月4日
	 * @version 1.0
	 * @description 获取R骨龄标准百分位数
	 */
	public List<Map<String, Object>> getBoneAgeStandardPercentileList(BoneAgeStandardPercentile standardPercentile);
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param boneAgeOrder
	 * @return
	 * @date 2018年6月4日
	 * @version 1.0
	 * @description 获取用户骨龄总分
	 */
	public Map<String, Object> getBoneTotalPoints(BoneAgeOrder boneAgeOrder);


	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param boneAgeOrder
	* @return 
	* @date 2018年6月4日
	* @version 1.0
	* @description 查询用户0.88-1.12年前的工单最新的一条
	*/
	public Map<String, Object> getBoneAgeOrdersByYearsAgo(BoneAgeOrder boneAgeOrder);

	public List<Map<String, Object>> getBoneAgeUserScoreList(BoneAgeOrder boneAgeOrder);
}
