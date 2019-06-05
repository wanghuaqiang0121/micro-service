package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.BusinessTypeMapper;
import org.module.platform.configuration.domain.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessTypeService {
	@Autowired
	private BusinessTypeMapper businessTypeMapper;

	public List<Map<String, Object>> getList(BusinessType businessType) {
		return businessTypeMapper.getList(businessType);
	}
}
