package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.platform.configuration.dao.ReadFilmRechargeRecordMapper;
import org.module.platform.configuration.domain.ReadFilmRechargeRecord;
import org.springframework.stereotype.Service;

@Service
public class ReadFilmRechargeRecordService {
	@Resource
	private ReadFilmRechargeRecordMapper mapper;

	public List<Map<String, Object>> getList(ReadFilmRechargeRecord readFilmRechargeRecord) {
		return mapper.getList(readFilmRechargeRecord);
	}

	public Map<String, Object> getOne(ReadFilmRechargeRecord readFilmRechargeRecord) {
		return mapper.getOne(readFilmRechargeRecord);
	}

	public int update(ReadFilmRechargeRecord readFilmRechargeRecord) {
		return mapper.update(readFilmRechargeRecord);
	}

	public int insert(ReadFilmRechargeRecord readFilmRechargeRecord) {
		return mapper.insert(readFilmRechargeRecord);
	}

	public Map<String, Object> getRepeat(ReadFilmRechargeRecord readFilmRechargeRecord) {
		return mapper.getRepeat(readFilmRechargeRecord);
	}
	
	public List<Map<String, Object>> getOrganizationList(ReadFilmRechargeRecord readFilmRechargeRecord){
		return mapper.getOrganizationList(readFilmRechargeRecord);
	}

}
