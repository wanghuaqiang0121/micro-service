package org.module.inquiry.dao;

import java.util.List;
import java.util.Map;

import org.module.inquiry.domain.InquiryReply;
import org.service.core.dao.IBaseMapper;

public interface InquiryReplyMapper extends IBaseMapper<InquiryReply>{
    int deleteByPrimaryKey(Integer id);

    int insert(InquiryReply record);

    InquiryReply selectByPrimaryKey(Integer id);

    List<InquiryReply> selectAll();

    int updateByPrimaryKey(InquiryReply record);

	Map<String, Object> getInquiriesNum(InquiryReply inquiryReply);
}