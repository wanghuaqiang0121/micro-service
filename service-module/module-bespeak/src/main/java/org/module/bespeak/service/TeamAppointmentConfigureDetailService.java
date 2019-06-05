package org.module.bespeak.service;

import java.util.Map;

import javax.annotation.Resource;

import org.module.bespeak.dao.TeamAppointmentConfigureDetailMapper;
import org.module.bespeak.domain.TeamAppointmentConfigureDetail;
import org.springframework.stereotype.Service;

@Service
public class TeamAppointmentConfigureDetailService {
	@Resource
	private TeamAppointmentConfigureDetailMapper mapper;

	public Map<String, Object> getOne(TeamAppointmentConfigureDetail teamAppointmentConfigureDetail) {
		return mapper.getOne(teamAppointmentConfigureDetail);
	}
	
}
