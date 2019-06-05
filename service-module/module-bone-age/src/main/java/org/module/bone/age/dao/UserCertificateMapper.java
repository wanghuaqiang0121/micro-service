package org.module.bone.age.dao;

import java.util.List;

import org.module.bone.age.domain.UserCertificate;
import org.service.core.dao.IBaseMapper;

public interface UserCertificateMapper extends IBaseMapper<UserCertificate> {

	int batchInsert(List<UserCertificate> userCertificates);
}