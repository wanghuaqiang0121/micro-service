package org.module.inquiry.service;

import java.util.Map;

import javax.annotation.Resource;

import org.module.inquiry.dao.InquiryReplyMapper;
import org.module.inquiry.domain.InquiryReply;
import org.springframework.stereotype.Service;

@Service
public class InquiryReplyService {
	
	@Resource
	private InquiryReplyMapper inquiryReplyMapper;

	public int insert(InquiryReply inquiryReply) {
		return inquiryReplyMapper.insert(inquiryReply);
	}

	public Map<String, Object> getInquiriesNum(InquiryReply inquiryReply) {
		return inquiryReplyMapper.getInquiriesNum(inquiryReply);
	}
	
}
