package org.module.bespeak.dao;

import java.util.List;
import java.util.Map;

import org.module.bespeak.domain.TeamAppointmentConfigure;
import org.service.core.dao.IBaseMapper;

public interface TeamAppointmentConfigureMapper extends IBaseMapper<TeamAppointmentConfigure> {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamAppointmentConfigure record);

    TeamAppointmentConfigure selectByPrimaryKey(Integer id);

    List<TeamAppointmentConfigure> selectAll();

    int updateByPrimaryKey(TeamAppointmentConfigure record);

	List<Map<String, Object>> getAppointmentDetail(TeamAppointmentConfigure teamAppointmentConfigure);
}