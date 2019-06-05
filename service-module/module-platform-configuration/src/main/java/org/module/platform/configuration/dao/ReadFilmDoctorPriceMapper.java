package org.module.platform.configuration.dao;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.ReadFilmDoctorPrice;
import org.service.core.dao.IBaseMapper;

public interface ReadFilmDoctorPriceMapper extends IBaseMapper<ReadFilmDoctorPrice>{
	List<Map<String, Object>> getDoctorList(ReadFilmDoctorPrice readFilmDoctorPrice);
}
