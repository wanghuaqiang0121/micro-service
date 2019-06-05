package org.module.platform.configuration.dao.organization;

import java.util.List;

import org.module.platform.configuration.domain.OrganizationSmsInfo;
import org.service.core.dao.IBaseMapper;

public interface OrganizationSmsInfoMapper extends IBaseMapper<OrganizationSmsInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationSmsInfo record);

    OrganizationSmsInfo selectByPrimaryKey(Integer id);

    List<OrganizationSmsInfo> selectAll();

    int updateByPrimaryKey(OrganizationSmsInfo record);
}