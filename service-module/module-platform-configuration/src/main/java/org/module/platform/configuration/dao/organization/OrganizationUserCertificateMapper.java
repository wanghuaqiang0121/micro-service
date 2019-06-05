package org.module.platform.configuration.dao.organization;

import java.util.List;

import org.module.platform.configuration.domain.organization.OrganizationUserCertificate;
import org.service.core.dao.IBaseMapper;

public interface OrganizationUserCertificateMapper extends IBaseMapper<OrganizationUserCertificate> {
  

	int batchInsert(List<OrganizationUserCertificate> organizationUserCertificates);
}