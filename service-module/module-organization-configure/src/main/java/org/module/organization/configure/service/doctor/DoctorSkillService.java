package org.module.organization.configure.service.doctor;

import java.util.Map;

import org.module.organization.configure.dao.doctor.DoctorSkillMapper;
import org.module.organization.configure.domain.doctor.DoctorSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorSkillService {

	@Autowired
	private DoctorSkillMapper mapper;

	public Map<String, Object> getOne(DoctorSkill doctorSkill) {
		return mapper.getOne(doctorSkill);
	}

	public int insert(DoctorSkill doctorSkill) {
		return mapper.insert(doctorSkill);
	}
	
}
