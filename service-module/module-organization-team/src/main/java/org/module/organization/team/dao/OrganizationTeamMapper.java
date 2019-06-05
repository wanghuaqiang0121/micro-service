package org.module.organization.team.dao;

import java.util.List;
import org.module.organization.team.domain.OrganizationTeam;
import org.service.core.dao.IBaseMapper;

public interface OrganizationTeamMapper extends IBaseMapper<OrganizationTeam> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTeam record);

    OrganizationTeam selectByPrimaryKey(Integer id);

    List<OrganizationTeam> selectAll();

    int updateByPrimaryKey(OrganizationTeam record);
}