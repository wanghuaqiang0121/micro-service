package org.module.inquiry.dao;

import java.util.List;

import org.module.inquiry.domain.Inquiry;
import org.service.core.dao.IBaseMapper;

public interface InquiryMapper extends IBaseMapper<Inquiry> {
    int deleteByPrimaryKey(Integer id);

    int insert(Inquiry record);

    Inquiry selectByPrimaryKey(Integer id);

    List<Inquiry> selectAll();

    int updateByPrimaryKey(Inquiry record);
}