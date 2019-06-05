package org.module.user.dao.order;

import java.util.List;
import java.util.Map;

import org.module.user.domain.order.UserService;
import org.service.core.dao.IBaseMapper;

public interface UserServiceMapper extends IBaseMapper<UserService> {
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param userServices
	* @return 
	* @date 2018年3月27日
	* @version 1.0
	* @description 批量新增用户服务
	*/
	int insertByList(List<UserService> userServices);

	List<Map<String, Object>> getAppointmentServiceList(UserService userService);

	List<Map<String, Object>> getServicesByServiceTypeId(UserService userService);
}