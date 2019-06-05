package org.module.sign.dao;

import java.util.List;

import org.module.sign.domain.UserSign;
import org.service.core.dao.IBaseMapper;

public interface UserSignMapper extends IBaseMapper<UserSign> {
    int deleteByPrimaryKey(Integer id);

    int insert(UserSign record);

    UserSign selectByPrimaryKey(Integer id);

    List<UserSign> selectAll();

    int updateByPrimaryKey(UserSign record);
}