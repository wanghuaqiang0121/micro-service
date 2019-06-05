package org.module.user.dao.itv;

import java.util.List;

import org.module.user.domain.itv.OrganizationUserItvAccount;
import org.service.core.dao.IBaseMapper;

public interface OrganizationUserItvAccountMapper extends IBaseMapper<OrganizationUserItvAccount> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationUserItvAccount record);

    OrganizationUserItvAccount selectByPrimaryKey(Integer id);

    List<OrganizationUserItvAccount> selectAll();

    int updateByPrimaryKey(OrganizationUserItvAccount record);
}