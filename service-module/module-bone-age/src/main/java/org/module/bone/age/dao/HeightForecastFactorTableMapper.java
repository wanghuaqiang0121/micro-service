package org.module.bone.age.dao;

import org.module.bone.age.domain.HeightForecastFactorTable;
import org.service.core.dao.IBaseMapper;

public interface HeightForecastFactorTableMapper extends IBaseMapper<HeightForecastFactorTable> {

	HeightForecastFactorTable getDetali(HeightForecastFactorTable heightForecastFactorTable);
	
}