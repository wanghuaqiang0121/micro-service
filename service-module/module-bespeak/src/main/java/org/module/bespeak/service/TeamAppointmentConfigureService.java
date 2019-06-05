package org.module.bespeak.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.bespeak.dao.TeamAppointmentConfigureMapper;
import org.module.bespeak.domain.TeamAppointmentConfigure;
import org.springframework.stereotype.Service;

@Service
public class TeamAppointmentConfigureService {

	@Resource
	private TeamAppointmentConfigureMapper teamAppointmentConfigureMapper;

	public List<Map<String, Object>> getList(TeamAppointmentConfigure teamAppointmentConfigure) {
		return teamAppointmentConfigureMapper.getList(teamAppointmentConfigure);
	}

	public Map<String, Object> getOne(TeamAppointmentConfigure teamAppointmentConfigure) {
		return teamAppointmentConfigureMapper.getOne(teamAppointmentConfigure);
	}

	public List<Map<String, Object>> getAppointmentDetail(TeamAppointmentConfigure teamAppointmentConfigure) {
		return teamAppointmentConfigureMapper.getAppointmentDetail(teamAppointmentConfigure);
	}
	
}
