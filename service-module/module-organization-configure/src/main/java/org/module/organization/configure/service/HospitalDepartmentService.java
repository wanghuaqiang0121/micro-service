package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.HospitalDepartmentMapper;
import org.module.organization.configure.domain.HospitalDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalDepartmentService {

	@Autowired
	private HospitalDepartmentMapper hospitalDepartmentMapper;

	public int insert(HospitalDepartment hospitalDepartment) {
		return hospitalDepartmentMapper.insert(hospitalDepartment);
	}

	public Map<String, Object> getOne(HospitalDepartment hospitalDepartment) {
		return hospitalDepartmentMapper.getOne(hospitalDepartment);
	}

	public int update(HospitalDepartment hospitalDepartment) {
		return hospitalDepartmentMapper.update(hospitalDepartment);
	}

	public int delete(HospitalDepartment hospitalDepartment) {
		return hospitalDepartmentMapper.delete(hospitalDepartment);
	}

	public Map<String, Object> getMedicalOrganizationInfo(HospitalDepartment hospitalDepartment) {
		return hospitalDepartmentMapper.getMedicalOrganizationInfo(hospitalDepartment);
	}

	public List<Map<String, Object>> getList(HospitalDepartment hospitalDepartment) {
		return hospitalDepartmentMapper.getList(hospitalDepartment);
	}

	public Map<String, Object> getRepeat(HospitalDepartment hospitalDepartment) {
		return hospitalDepartmentMapper.getRepeat(hospitalDepartment);
	}

}
