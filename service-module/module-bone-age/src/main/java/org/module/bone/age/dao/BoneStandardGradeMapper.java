package org.module.bone.age.dao;

import java.util.List;

import org.module.bone.age.domain.BoneStandardGrade;
import org.service.core.dao.IBaseMapper;

public interface BoneStandardGradeMapper extends IBaseMapper<BoneStandardGrade> {
    int deleteByPrimaryKey(Integer id);

    int insert(BoneStandardGrade record);

    BoneStandardGrade selectByPrimaryKey(Integer id);

    List<BoneStandardGrade> selectAll();

    int updateByPrimaryKey(BoneStandardGrade record);
}