package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationUserCertificateMapper;
import org.module.organization.configure.domain.OrganizationUserCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationUserCertificateService {

	@Autowired
	private OrganizationUserCertificateMapper organizationUserCertificateMapper;

	public int batchInsert(List<OrganizationUserCertificate> organizationUserCertificates) {
		return organizationUserCertificateMapper.batchInsert(organizationUserCertificates);
	}

	public Map<String,Object> getRepeat(OrganizationUserCertificate organizationUserCertificate) {
		return organizationUserCertificateMapper.getRepeat(organizationUserCertificate);
	}
	
}
