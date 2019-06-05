package org.module.common.service;

import java.util.Map;

import org.module.common.dao.SystemKeyInformationMapper;
import org.module.common.domain.SystemKeyInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemKeyInformationService {

	@Autowired
	private SystemKeyInformationMapper systemKeyInformationMapper;

	public Map<String, Object> getOne(SystemKeyInformation systemKeyInformation) {
		return systemKeyInformationMapper.getOne(systemKeyInformation);
	}
}
