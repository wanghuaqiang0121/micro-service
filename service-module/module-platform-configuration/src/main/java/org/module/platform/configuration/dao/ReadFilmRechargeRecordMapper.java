package org.module.platform.configuration.dao;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.ReadFilmRechargeRecord;
import org.service.core.dao.IBaseMapper;

public interface ReadFilmRechargeRecordMapper extends IBaseMapper<ReadFilmRechargeRecord> {

	
	List<Map<String, Object>> getOrganizationList(ReadFilmRechargeRecord readFilmRechargeRecord);
}
