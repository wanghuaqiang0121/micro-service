package org.module.organization.configure.service;

import java.util.Map;

import org.module.organization.configure.dao.ServiceTypeMapper;
import org.module.organization.configure.domain.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService {

	@Autowired
	private ServiceTypeMapper serviceTypeMapper;

	public Map<String, Object> getOne(ServiceType serviceType) {
		return serviceTypeMapper.getOne(serviceType);
	}
}
