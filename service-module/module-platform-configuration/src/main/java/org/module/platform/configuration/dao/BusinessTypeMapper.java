package org.module.platform.configuration.dao;

import java.util.List;

import org.module.platform.configuration.domain.BusinessType;
import org.service.core.dao.IBaseMapper;

public interface BusinessTypeMapper extends IBaseMapper<BusinessType> {
    int deleteByPrimaryKey(Integer id);

    int insert(BusinessType record);

    BusinessType selectByPrimaryKey(Integer id);

    List<BusinessType> selectAll();

    int updateByPrimaryKey(BusinessType record);
}