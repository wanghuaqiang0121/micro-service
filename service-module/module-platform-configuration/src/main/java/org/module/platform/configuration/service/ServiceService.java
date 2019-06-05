package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.ServiceMapper;
import org.module.platform.configuration.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceService {

	@Autowired
	private ServiceMapper serviceMapper;
	
	
	public List<Map<String, Object>> getList(Service service) {
		return serviceMapper.getList(service);
	}


	public Map<String, Object> getOne(Service service) {
		return serviceMapper.getOne(service);
	}


	public int update(Service service) {
		return serviceMapper.update(service);
	}


	public int insert(Service service) {
		return serviceMapper.insert(service);
	}

}
