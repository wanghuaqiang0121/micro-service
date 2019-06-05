package org.module.sign.dao;

import java.util.List;

import org.module.sign.domain.UserSignLog;
import org.service.core.dao.IBaseMapper;

public interface UserSignLogMapper extends IBaseMapper<UserSignLog> {
    int deleteByPrimaryKey(Integer id);

    int insert(UserSignLog record);

    UserSignLog selectByPrimaryKey(Integer id);

    List<UserSignLog> selectAll();

    int updateByPrimaryKey(UserSignLog record);
}