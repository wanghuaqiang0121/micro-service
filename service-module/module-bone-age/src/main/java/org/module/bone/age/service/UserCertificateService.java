package org.module.bone.age.service;

import java.util.List;
import java.util.Map;

import org.module.bone.age.dao.UserCertificateMapper;
import org.module.bone.age.domain.UserCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCertificateService {

	@Autowired
	private UserCertificateMapper userCertificateMapper;

	public Map<String, Object> getRepeat(UserCertificate userCertificate) {
		return userCertificateMapper.getRepeat(userCertificate);
	}

	public int insert(UserCertificate userCertificate) {
		return userCertificateMapper.insert(userCertificate);
	}

	public int batchInsert(List<UserCertificate> userCertificates) {
		return userCertificateMapper.batchInsert(userCertificates);
	}
}
