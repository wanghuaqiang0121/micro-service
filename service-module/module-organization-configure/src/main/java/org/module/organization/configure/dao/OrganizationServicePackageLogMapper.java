package org.module.organization.configure.dao;

import java.util.List;

import org.module.organization.configure.domain.OrganizationServicePackageLog;
import org.service.core.dao.IBaseMapper;

public interface OrganizationServicePackageLogMapper extends IBaseMapper<OrganizationServicePackageLog> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationServicePackageLog record);

    OrganizationServicePackageLog selectByPrimaryKey(Integer id);

    List<OrganizationServicePackageLog> selectAll();

    int updateByPrimaryKey(OrganizationServicePackageLog record);
}