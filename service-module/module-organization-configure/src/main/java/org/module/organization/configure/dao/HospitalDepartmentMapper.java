package org.module.organization.configure.dao;

import java.util.Map;

import org.module.organization.configure.domain.HospitalDepartment;
import org.service.core.dao.IBaseMapper;

public interface HospitalDepartmentMapper extends IBaseMapper<HospitalDepartment> {

	Map<String, Object> getMedicalOrganizationInfo(HospitalDepartment hospitalDepartment);
}