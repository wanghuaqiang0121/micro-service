package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.BoneScoreTableMapper;
import org.module.platform.configuration.domain.BoneScoreTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoneScoreTableService {

	@Autowired
	private BoneScoreTableMapper boneScoreTableMapper;

	public List<Map<String, Object>> getBoneType(BoneScoreTable boneScoreTable) {
		return boneScoreTableMapper.getBoneType(boneScoreTable);
	}

	public List<Map<String, Object>> getBoneGrade(BoneScoreTable boneScoreTable) {
		return boneScoreTableMapper.getBoneGrade(boneScoreTable);
	}
}
