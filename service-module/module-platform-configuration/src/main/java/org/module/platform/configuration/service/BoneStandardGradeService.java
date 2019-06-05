package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.BoneStandardGradeMapper;
import org.module.platform.configuration.domain.BoneStandardGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoneStandardGradeService {

	
	@Autowired
	private BoneStandardGradeMapper boneStandardGradeMapper;

	public int insert(BoneStandardGrade boneStandardGrade) {
		return boneStandardGradeMapper.insert(boneStandardGrade);
	}

	public List<Map<String, Object>> getList(BoneStandardGrade boneStandardGrade) {
		return boneStandardGradeMapper.getList(boneStandardGrade);
	}

	public Map<String, Object> getOne(BoneStandardGrade boneStandardGrade) {
		return boneStandardGradeMapper.getOne(boneStandardGrade);
	}

	public int update(BoneStandardGrade boneStandardGrade) {
		return boneStandardGradeMapper.update(boneStandardGrade);
	}

	public int delete(BoneStandardGrade boneStandardGrade) {
		return boneStandardGradeMapper.delete(boneStandardGrade);
	}
}
