package org.module.user.service.order;

import java.util.List;
import java.util.Map;

import org.module.user.dao.order.OrganizationPackageServiceMapper;
import org.module.user.domain.order.OrganizationPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationPackageServiceService {

	@Autowired
	private OrganizationPackageServiceMapper organizationPackageServiceMapper;

	public List<Map<String, Object>> getList(OrganizationPackageService packageService) {
		return organizationPackageServiceMapper.getList(packageService);
	}
}
