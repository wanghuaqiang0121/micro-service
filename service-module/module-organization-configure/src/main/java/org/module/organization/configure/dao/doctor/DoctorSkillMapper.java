package org.module.organization.configure.dao.doctor;

import java.util.List;
import org.module.organization.configure.domain.doctor.DoctorSkill;
import org.service.core.dao.IBaseMapper;

public interface DoctorSkillMapper extends IBaseMapper<DoctorSkill> {
    int deleteByPrimaryKey(Integer id);

    int insert(DoctorSkill record);

    DoctorSkill selectByPrimaryKey(Integer id);

    List<DoctorSkill> selectAll();

    int updateByPrimaryKey(DoctorSkill record);
}