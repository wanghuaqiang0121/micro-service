package org.module.organization.configure.service.doctor;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.doctor.DoctorCategoryMapper;
import org.module.organization.configure.domain.doctor.DoctorCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorCategoryService {

	@Autowired
	private DoctorCategoryMapper doctorCategoryMapper;

	public List<Map<String, Object>> getList(DoctorCategory doctorCategory) {
		return doctorCategoryMapper.getList(doctorCategory);
	}
}
