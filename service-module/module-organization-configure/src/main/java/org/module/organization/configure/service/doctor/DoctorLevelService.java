package org.module.organization.configure.service.doctor;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.doctor.DoctorLevelMapper;
import org.module.organization.configure.domain.doctor.DoctorLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorLevelService {

	
	@Autowired
	private DoctorLevelMapper doctorLevelMapper;

	public List<Map<String, Object>> getList(DoctorLevel doctorLevel) {
		return doctorLevelMapper.getList(doctorLevel);
	}

	public Map<String, Object> getRepeat(DoctorLevel doctorLevel) {
		return doctorLevelMapper.getRepeat(doctorLevel);
	}

	public int insert(DoctorLevel doctorLevel) {
		return doctorLevelMapper.insert(doctorLevel);
	}

	public Map<String, Object> getOne(DoctorLevel doctorLevel) {
		return doctorLevelMapper.getOne(doctorLevel);
	}

	public int update(DoctorLevel doctorLevel) {
		return doctorLevelMapper.update(doctorLevel);
	}
}
