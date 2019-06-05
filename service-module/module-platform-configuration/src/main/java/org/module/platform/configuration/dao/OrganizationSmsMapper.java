package org.module.platform.configuration.dao;

import java.util.List;

import org.module.platform.configuration.domain.OrganizationSms;
import org.service.core.dao.IBaseMapper;

public interface OrganizationSmsMapper extends IBaseMapper<OrganizationSms> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationSms record);

    OrganizationSms selectByPrimaryKey(Integer id);

    List<OrganizationSms> selectAll();

    int updateByPrimaryKey(OrganizationSms record);
}