package org.module.bespeak.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.bespeak.dao.TeamAppointmentOrderMapper;
import org.module.bespeak.domain.TeamAppointmentOrder;
import org.springframework.stereotype.Service;

@Service
public class TeamAppointmentOrderService {
	@Resource
	private TeamAppointmentOrderMapper teamAppointmentOrderMapper;

	public List<Map<String, Object>> getList(TeamAppointmentOrder teamAppointmentOrder) {
		return teamAppointmentOrderMapper.getList(teamAppointmentOrder);
	}


	public int cancelOrder(TeamAppointmentOrder teamAppointmentOrder) {
		return teamAppointmentOrderMapper.cancelOrder(teamAppointmentOrder);
	}


	public Map<String, Object> getOne(TeamAppointmentOrder teamAppointmentOrder) {
		return teamAppointmentOrderMapper.getOne(teamAppointmentOrder);
	}


	public Map<String, Object> getAppointmentNo(TeamAppointmentOrder teamAppointmentOrder) {
		return teamAppointmentOrderMapper.getAppointmentNo(teamAppointmentOrder);
	}


	public int insert(TeamAppointmentOrder teamAppointmentOrder) {
		return teamAppointmentOrderMapper.insert(teamAppointmentOrder);
	}
}
