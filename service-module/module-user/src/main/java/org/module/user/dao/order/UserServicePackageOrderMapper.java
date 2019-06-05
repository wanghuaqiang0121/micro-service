package org.module.user.dao.order;

import java.util.List;
import java.util.Map;

import org.module.user.domain.order.UserServicePackageOrder;
import org.service.core.dao.IBaseMapper;

public interface UserServicePackageOrderMapper extends IBaseMapper<UserServicePackageOrder> {
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param baseUserServicePackageOrder
	* @return 
	* @date 2018年3月27日
	* @version 1.0
	* @description 确认完成订单
	*/
	int updateSureUserServicePackageOrder(UserServicePackageOrder baseUserServicePackageOrder);

	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param userServicePackageOrder
	* @return 
	* @date 2018年8月15日
	* @version 1.0
	* @description 取消订单
	*/
	int updateCancelUserServicePackageOrder(UserServicePackageOrder userServicePackageOrder);

	List<Map<String, Object>> getListByUserId(UserServicePackageOrder userServicePackageOrder);
}