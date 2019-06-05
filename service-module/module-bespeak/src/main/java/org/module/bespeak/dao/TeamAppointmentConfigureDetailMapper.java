package org.module.bespeak.dao;

import java.util.List;
import org.module.bespeak.domain.TeamAppointmentConfigureDetail;
import org.service.core.dao.IBaseMapper;

public interface TeamAppointmentConfigureDetailMapper extends IBaseMapper<TeamAppointmentConfigureDetail> {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamAppointmentConfigureDetail record);

    TeamAppointmentConfigureDetail selectByPrimaryKey(Integer id);

    List<TeamAppointmentConfigureDetail> selectAll();

    int updateByPrimaryKey(TeamAppointmentConfigureDetail record);
}