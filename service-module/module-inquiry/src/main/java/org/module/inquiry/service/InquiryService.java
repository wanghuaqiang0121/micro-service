package org.module.inquiry.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.inquiry.dao.InquiryMapper;
import org.module.inquiry.domain.Inquiry;
import org.springframework.stereotype.Service;

@Service
public class InquiryService {

	@Resource
	private InquiryMapper inquiryMapper;

	public int insert(Inquiry inquiry) {
		return inquiryMapper.insert(inquiry);
	}

	public List<Map<String, Object>> getList(Inquiry inquiry) {
		return inquiryMapper.getList(inquiry);
	}

	public int update(Inquiry inquiry) {
		return inquiryMapper.update(inquiry);
	}

	public Map<String, Object> getOne(Inquiry inquiry) {
		return inquiryMapper.getOne(inquiry);
	}
	
}
