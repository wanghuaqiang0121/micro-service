package org.module.work.order.service;

import org.module.work.order.dao.WorkOrderLogMapper;
import org.module.work.order.domain.WorkOrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderLogService {

	@Autowired
	private WorkOrderLogMapper workOrderLogMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param workOrderLog
	* @return 
	* @date 2018年3月28日
	* @version 1.0
	* @description 添加日志
	*/
	public int insert(WorkOrderLog workOrderLog) {
		return workOrderLogMapper.insert(workOrderLog);
	}
	
}
