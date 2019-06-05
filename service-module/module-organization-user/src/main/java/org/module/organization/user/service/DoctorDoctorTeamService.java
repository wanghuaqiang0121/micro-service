package org.module.organization.user.service;

import java.util.Map;

import org.module.organization.user.dao.DoctorDoctorTeamMapper;
import org.module.organization.user.domain.DoctorDoctorTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorDoctorTeamService {

	@Autowired
	private DoctorDoctorTeamMapper doctorDoctorTeamMapper;

	public Map<String, Object> getOne(DoctorDoctorTeam doctorDoctorTeam) {
		return doctorDoctorTeamMapper.getOne(doctorDoctorTeam);
	}
}
