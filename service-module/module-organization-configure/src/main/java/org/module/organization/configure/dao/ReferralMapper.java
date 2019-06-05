package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.Referral;
import org.service.core.dao.IBaseMapper;

public interface ReferralMapper extends IBaseMapper<Referral> {

	List<Map<String, Object>> getReferralByOrganizationId(Referral referral);

	List<Map<String, Object>> getReferralByDepartment(Referral referral);
}