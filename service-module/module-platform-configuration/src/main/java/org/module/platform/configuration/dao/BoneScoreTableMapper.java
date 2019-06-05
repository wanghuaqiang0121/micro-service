package org.module.platform.configuration.dao;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.BoneScoreTable;
import org.service.core.dao.IBaseMapper;

public interface BoneScoreTableMapper extends IBaseMapper<BoneScoreTable> {

	List<Map<String, Object>> getBoneType(BoneScoreTable boneScoreTable);

	List<Map<String, Object>> getBoneGrade(BoneScoreTable boneScoreTable);
}