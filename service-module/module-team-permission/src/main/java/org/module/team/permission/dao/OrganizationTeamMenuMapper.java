package org.module.team.permission.dao;

import java.util.List;

import org.module.team.permission.domain.OrganizationTeamMenu;
import org.service.core.dao.IBaseMapper;

public interface OrganizationTeamMenuMapper extends IBaseMapper<OrganizationTeamMenu> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTeamMenu record);

    OrganizationTeamMenu selectByPrimaryKey(Integer id);

    List<OrganizationTeamMenu> selectAll();

    int updateByPrimaryKey(OrganizationTeamMenu record);
}