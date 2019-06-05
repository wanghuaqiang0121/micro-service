package org.module.organization.configure.service.doctor;

import org.module.organization.configure.dao.doctor.DoctorDoctorSkillMapper;
import org.module.organization.configure.domain.doctor.DoctorDoctorSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorDoctorSkillService {

	@Autowired
	private DoctorDoctorSkillMapper mapper;

	public int insert(DoctorDoctorSkill doctorDoctorSkill) {
		return mapper.insert(doctorDoctorSkill);
	}

	public int deleteByDoctorInfoId(DoctorDoctorSkill doctorDoctorSkillDelete) {
		return mapper.deleteByDoctorInfoId(doctorDoctorSkillDelete);
	}
	
}
