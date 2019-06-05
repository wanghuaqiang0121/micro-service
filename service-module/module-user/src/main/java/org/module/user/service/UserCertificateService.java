package org.module.user.service;

import java.util.Map;

import org.module.user.dao.UserCertificateMapper;
import org.module.user.domain.UserCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCertificateService {

	@Autowired
	private UserCertificateMapper userCertificateMapper;

	public Map<String, Object> getRepeatByType(UserCertificate userCertificate) {
		return userCertificateMapper.getRepeatByType(userCertificate);
	}

	public int insert(UserCertificate userCertificate) {
		return userCertificateMapper.insert(userCertificate);
	}

	public Map<String, Object> getRepeat(UserCertificate userCertificate) {
		return userCertificateMapper.getRepeat(userCertificate);
	}

	public int deleteByUserId(UserCertificate userCertificate) {
		return userCertificateMapper.deleteByUserId(userCertificate);
	}
}
