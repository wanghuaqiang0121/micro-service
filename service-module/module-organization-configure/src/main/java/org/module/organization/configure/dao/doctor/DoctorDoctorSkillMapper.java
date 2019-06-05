package org.module.organization.configure.dao.doctor;

import org.module.organization.configure.domain.doctor.DoctorDoctorSkill;
import org.service.core.dao.IBaseMapper;

public interface DoctorDoctorSkillMapper extends IBaseMapper<DoctorDoctorSkill> {

	int deleteByDoctorInfoId(DoctorDoctorSkill doctorDoctorSkill);
}