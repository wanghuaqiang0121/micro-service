package org.module.user.service.order;

import java.util.List;
import java.util.Map;

import org.module.user.dao.order.UserServiceMapper;
import org.module.user.domain.order.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceService {

	@Autowired
	private UserServiceMapper userServiceMapper;

	public int insertByList(List<UserService> userServices) {
		return userServiceMapper.insertByList(userServices);
	}

	public List<Map<String, Object>> getList(UserService userService) {
		return userServiceMapper.getList(userService);
	}

	public List<Map<String, Object>> getAppointmentServiceList(UserService userService) {
		return userServiceMapper.getAppointmentServiceList(userService);
	}

	public List<Map<String, Object>> getServicesByServiceTypeId(UserService userService) {
		return userServiceMapper.getServicesByServiceTypeId(userService);
	}
}
