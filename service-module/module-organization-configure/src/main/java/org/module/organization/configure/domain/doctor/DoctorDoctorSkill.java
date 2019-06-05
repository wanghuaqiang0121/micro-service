package org.module.organization.configure.domain.doctor;

import org.service.core.entity.BaseEntity;

public class DoctorDoctorSkill extends BaseEntity {
    private Integer id;

    private Integer doctorInfoId;

    private Integer doctorSkillId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorInfoId() {
        return doctorInfoId;
    }

    public void setDoctorInfoId(Integer doctorInfoId) {
        this.doctorInfoId = doctorInfoId;
    }

    public Integer getDoctorSkillId() {
        return doctorSkillId;
    }

    public void setDoctorSkillId(Integer doctorSkillId) {
        this.doctorSkillId = doctorSkillId;
    }
}