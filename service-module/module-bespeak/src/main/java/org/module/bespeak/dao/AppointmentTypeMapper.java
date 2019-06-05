package org.module.bespeak.dao;

import java.util.List;

import org.module.bespeak.domain.AppointmentType;
import org.service.core.dao.IBaseMapper;

public interface AppointmentTypeMapper extends IBaseMapper<AppointmentType> {
    int deleteByPrimaryKey(Integer id);

    int insert(AppointmentType record);

    AppointmentType selectByPrimaryKey(Integer id);

    List<AppointmentType> selectAll();

    int updateByPrimaryKey(AppointmentType record);
}