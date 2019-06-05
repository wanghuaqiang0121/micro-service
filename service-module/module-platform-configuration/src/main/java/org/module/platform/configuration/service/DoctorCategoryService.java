package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.DoctorCategoryMapper;
import org.module.platform.configuration.domain.DoctorCategory;
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
