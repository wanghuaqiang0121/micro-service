package org.module.team.permission.dao;

import java.util.List;

import org.module.team.permission.domain.OrganizationTeamOperation;
import org.service.core.dao.IBaseMapper;

public interface OrganizationTeamOperationMapper extends IBaseMapper<OrganizationTeamOperation> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTeamOperation record);

    OrganizationTeamOperation selectByPrimaryKey(Integer id);

    List<OrganizationTeamOperation> selectAll();

    int updateByPrimaryKey(OrganizationTeamOperation record);
}