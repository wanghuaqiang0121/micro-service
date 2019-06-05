package org.module.bone.age.service;

import java.util.List;
import java.util.Map;

import org.module.bone.age.dao.BoneStandardGradeMapper;
import org.module.bone.age.domain.BoneStandardGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoneStandardGradeService {

	@Autowired
	private BoneStandardGradeMapper boneStandardGradeMapper;

	public List<Map<String, Object>> getList(BoneStandardGrade boneStandardGrade) {
		return boneStandardGradeMapper.getList(boneStandardGrade);
	}
	
}
