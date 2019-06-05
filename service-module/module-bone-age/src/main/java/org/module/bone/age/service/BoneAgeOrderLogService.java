package org.module.bone.age.service;

import org.module.bone.age.dao.BoneAgeOrderLogMapper;
import org.module.bone.age.domain.BoneAgeOrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoneAgeOrderLogService {
	@Autowired
	private BoneAgeOrderLogMapper boneAgeOrderLogMapper;
	
	public int insert(BoneAgeOrderLog boneAgeOrderLog){
		return boneAgeOrderLogMapper.insert(boneAgeOrderLog);
	}
}
