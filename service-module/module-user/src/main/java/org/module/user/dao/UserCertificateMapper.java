package org.module.user.dao;

import java.util.Map;

import org.module.user.domain.UserCertificate;
import org.service.core.dao.IBaseMapper;

public interface UserCertificateMapper extends IBaseMapper<UserCertificate> {
	
	Map<String, Object> getRepeatByType(UserCertificate userCertificate);

	int deleteByUserId(UserCertificate userCertificate);
}