package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationTeam;
import org.service.core.dao.IBaseMapper;

public interface OrganizationTeamMapper extends IBaseMapper<OrganizationTeam> {

	Map<String, Object> getOrganizationVerType(OrganizationTeam organizationTeam);

	List<Map<String, Object>> getwechatQrCode(OrganizationTeam organizationTeam);

}
