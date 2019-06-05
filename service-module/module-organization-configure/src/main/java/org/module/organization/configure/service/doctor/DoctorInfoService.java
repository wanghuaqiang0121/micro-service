package org.module.organization.configure.service.doctor;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.doctor.DoctorInfoMapper;
import org.module.organization.configure.domain.doctor.DoctorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorInfoService {
	@Autowired
	private DoctorInfoMapper doctorInfoMapper;
	
	public	int insert(DoctorInfo doctorInfo) {
		return doctorInfoMapper.insert(doctorInfo);

	}

	public	int update(DoctorInfo doctorInfo) {
		return doctorInfoMapper.update(doctorInfo);
	}

	public	int delete(DoctorInfo doctorInfo) {
		return doctorInfoMapper.delete(doctorInfo);
	}

	public	Map<String, Object> getRepeat(DoctorInfo doctorInfo) {
		return doctorInfoMapper.getRepeat(doctorInfo);
	}

	public	Map<String, Object> getOne(DoctorInfo doctorInfo) {
		return doctorInfoMapper.getOne(doctorInfo);
	}

	public	List<Map<String, Object>> getList(DoctorInfo doctorInfo){
		return doctorInfoMapper.getList(doctorInfo);
	}
}
