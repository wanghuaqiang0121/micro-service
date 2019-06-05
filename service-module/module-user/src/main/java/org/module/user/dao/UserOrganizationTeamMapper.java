package org.module.user.dao;

import java.util.List;

import org.module.user.domain.UserOrganizationTeam;
import org.service.core.dao.IBaseMapper;

public interface UserOrganizationTeamMapper extends IBaseMapper<UserOrganizationTeam> {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOrganizationTeam record);

    UserOrganizationTeam selectByPrimaryKey(Integer id);

    List<UserOrganizationTeam> selectAll();

    int updateByPrimaryKey(UserOrganizationTeam record);
}