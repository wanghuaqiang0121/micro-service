package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.PensionOrganizationInfoMapper;
import org.module.platform.configuration.domain.PensionOrganizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PensionOrganizationInfoService {

	@Autowired
	private PensionOrganizationInfoMapper pensionOrganizationInfoMapper;

	public List<Map<String, Object>> getList(PensionOrganizationInfo pensionOrganizationInfo) {
		return pensionOrganizationInfoMapper.getList(pensionOrganizationInfo);
	}

	public int insert(PensionOrganizationInfo pensionOrganizationInfo) {
		return pensionOrganizationInfoMapper.insert(pensionOrganizationInfo);
	}

	public int update(PensionOrganizationInfo pensionOrganizationInfo) {
		return pensionOrganizationInfoMapper.update(pensionOrganizationInfo);
	}

	public Map<String, Object> getOne(PensionOrganizationInfo pensionOrganizationInfo) {
		return pensionOrganizationInfoMapper.getOne(pensionOrganizationInfo);
	}
}
