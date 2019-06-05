package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationUserTeam;
import org.service.core.dao.IBaseMapper;

public interface OrganizationUserTeamMapper extends IBaseMapper<OrganizationUserTeam> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationUserTeam record);

    OrganizationUserTeam selectByPrimaryKey(Integer id);

    List<OrganizationUserTeam> selectAll();

    int updateByPrimaryKey(OrganizationUserTeam record);

	List<Map<String, Object>> getTeamDoctors(OrganizationUserTeam doctorDoctorTeam);

	List<Map<String, Object>> getOrganizationUserMembers(OrganizationUserTeam doctorDoctorTeam);
}