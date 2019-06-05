package org.module.bone.age.dao;

import java.util.List;
import java.util.Map;

import org.module.bone.age.domain.HeightPercentileConvertTable;
import org.service.core.dao.IBaseMapper;

public interface HeightPercentileConvertTableMapper extends IBaseMapper<HeightPercentileConvertTable> {

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param heightPercentileConvertTable
	 * @return
	 * @date 2018年8月13日
	 * @version 1.0
	 * @description 身高实际值求转换值
	 */
	List<Map<String, Object>> getActualToConvert(HeightPercentileConvertTable heightPercentileConvertTable);

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param heightPercentileConvertTable
	 * @return
	 * @date 2018年8月13日
	 * @version 1.0
	 * @description 身高转换值求实际值
	 */
	List<Map<String, Object>> getConvertToActual(HeightPercentileConvertTable heightPercentileConvertTable);
}