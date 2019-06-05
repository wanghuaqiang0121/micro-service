package org.module.user.service.order;

import java.util.List;
import java.util.Map;

import org.module.user.dao.order.UserServicePackageOrderMapper;
import org.module.user.domain.order.UserServicePackageOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicePackageOrderService {

	@Autowired
	private UserServicePackageOrderMapper userServicePackageOrderMapper;

	public List<Map<String, Object>> getList(UserServicePackageOrder userServicePackageOrder) {
		return userServicePackageOrderMapper.getList(userServicePackageOrder);
	}

	public int insert(UserServicePackageOrder userServicePackageOrder) {
		return userServicePackageOrderMapper.insert(userServicePackageOrder);
	}

	public Map<String, Object> getOne(UserServicePackageOrder userServicePackageOrder) {
		return userServicePackageOrderMapper.getOne(userServicePackageOrder);
	}

	public int updateSureUserServicePackageOrder(UserServicePackageOrder userServicePackageOrder) {
		return userServicePackageOrderMapper.updateSureUserServicePackageOrder(userServicePackageOrder);
	}

	public int updateCancelUserServicePackageOrder(UserServicePackageOrder userServicePackageOrder) {
		return userServicePackageOrderMapper.updateCancelUserServicePackageOrder(userServicePackageOrder);
	}

	public int update(UserServicePackageOrder userServicePackageOrder) {
		return userServicePackageOrderMapper.update(userServicePackageOrder);
	}

	public List<Map<String, Object>> getListByUserId(UserServicePackageOrder userServicePackageOrder) {
		return userServicePackageOrderMapper.getListByUserId(userServicePackageOrder);
	}



}
