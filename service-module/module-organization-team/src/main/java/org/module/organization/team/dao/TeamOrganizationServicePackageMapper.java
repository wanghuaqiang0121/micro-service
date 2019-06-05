package org.module.organization.team.dao;

import java.util.List;
import org.module.organization.team.domain.TeamOrganizationServicePackage;
import org.service.core.dao.IBaseMapper;

public interface TeamOrganizationServicePackageMapper extends IBaseMapper<TeamOrganizationServicePackage> {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamOrganizationServicePackage record);

    TeamOrganizationServicePackage selectByPrimaryKey(Integer id);

    List<TeamOrganizationServicePackage> selectAll();

    int updateByPrimaryKey(TeamOrganizationServicePackage record);
}