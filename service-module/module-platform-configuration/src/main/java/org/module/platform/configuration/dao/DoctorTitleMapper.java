package org.module.platform.configuration.dao;

import java.util.List;

import org.module.platform.configuration.domain.DoctorTitle;
import org.service.core.dao.IBaseMapper;

public interface DoctorTitleMapper extends IBaseMapper<DoctorTitle> {
    int deleteByPrimaryKey(Integer id);

    int insert(DoctorTitle record);

    DoctorTitle selectByPrimaryKey(Integer id);

    List<DoctorTitle> selectAll();

    int updateByPrimaryKey(DoctorTitle record);
}