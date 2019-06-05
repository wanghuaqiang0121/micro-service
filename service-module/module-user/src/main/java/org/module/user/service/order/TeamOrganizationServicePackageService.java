package org.module.user.service.order;

import java.util.Map;

import org.module.user.dao.order.TeamOrganizationServicePackageMapper;
import org.module.user.domain.order.TeamOrganizationServicePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamOrganizationServicePackageService {

	@Autowired
	private TeamOrganizationServicePackageMapper teamOrganizationServicePackageMapper;

	public Map<String, Object> getOne(TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.getOne(teamOrganizationServicePackage);
	}
}
