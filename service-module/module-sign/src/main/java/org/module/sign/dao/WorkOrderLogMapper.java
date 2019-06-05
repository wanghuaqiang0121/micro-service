package org.module.sign.dao;

import java.util.List;
import org.module.sign.domain.WorkOrderLog;
import org.service.core.dao.IBaseMapper;

public interface WorkOrderLogMapper extends IBaseMapper<WorkOrderLog> {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkOrderLog record);

    WorkOrderLog selectByPrimaryKey(Integer id);

    List<WorkOrderLog> selectAll();

    int updateByPrimaryKey(WorkOrderLog record);
}