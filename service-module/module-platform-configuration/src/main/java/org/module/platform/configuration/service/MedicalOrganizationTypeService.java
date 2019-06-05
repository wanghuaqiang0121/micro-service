package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.platform.configuration.dao.MedicalOrganizationTypeMapper;
import org.module.platform.configuration.domain.MedicalOrganizationType;
import org.springframework.stereotype.Service;

@Service
public class MedicalOrganizationTypeService {
	
	@Resource
	private MedicalOrganizationTypeMapper medicalOrganizationTypeMapper;
	
	public int insert(MedicalOrganizationType medicalOrganizationType) {
		return medicalOrganizationTypeMapper.insert(medicalOrganizationType);
	}

	public int update(MedicalOrganizationType medicalOrganizationType) {
		return medicalOrganizationTypeMapper.update(medicalOrganizationType);
	}

	public List<Map<String, Object>> getList(MedicalOrganizationType medicalOrganizationType) {
		return medicalOrganizationTypeMapper.getList(medicalOrganizationType);

	}

	public Map<String, Object> getRepeat(MedicalOrganizationType medicalOrganizationType) {
		return medicalOrganizationTypeMapper.getRepeat(medicalOrganizationType);
	}
	
	public Map<String, Object> getDetail(MedicalOrganizationType medicalOrganizationType) {
		return medicalOrganizationTypeMapper.getOne(medicalOrganizationType);
	}

}
