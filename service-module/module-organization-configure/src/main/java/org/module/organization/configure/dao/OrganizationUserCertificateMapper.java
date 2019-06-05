package org.module.organization.configure.dao;

import java.util.List;
import org.module.organization.configure.domain.OrganizationUserCertificate;
import org.service.core.dao.IBaseMapper;

public interface OrganizationUserCertificateMapper extends IBaseMapper<OrganizationUserCertificate> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationUserCertificate record);

    OrganizationUserCertificate selectByPrimaryKey(Integer id);

    List<OrganizationUserCertificate> selectAll();

    int updateByPrimaryKey(OrganizationUserCertificate record);

	int batchInsert(List<OrganizationUserCertificate> organizationUserCertificates);
}