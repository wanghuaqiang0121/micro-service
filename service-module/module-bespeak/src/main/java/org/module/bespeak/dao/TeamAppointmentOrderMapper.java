package org.module.bespeak.dao;

import java.util.List;
import java.util.Map;

import org.module.bespeak.domain.TeamAppointmentOrder;
import org.service.core.dao.IBaseMapper;

public interface TeamAppointmentOrderMapper extends IBaseMapper<TeamAppointmentOrder> {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamAppointmentOrder record);

    TeamAppointmentOrder selectByPrimaryKey(Integer id);

    List<TeamAppointmentOrder> selectAll();

    int updateByPrimaryKey(TeamAppointmentOrder record);

	int cancelOrder(TeamAppointmentOrder teamAppointmentOrder);

	Map<String, Object> getAppointmentNo(TeamAppointmentOrder teamAppointmentOrder);
}