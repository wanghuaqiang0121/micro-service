package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationUserType;
import org.service.core.dao.IBaseMapper;

public interface OrganizationUserTypeMapper extends IBaseMapper<OrganizationUserType>  {

	List<Map<String, Object>> getOrganizationUserTypeIsNull(OrganizationUserType organizationUserType);

	List<Map<String, Object>> getOrganizationUserTypehaveAndNotHaveList(OrganizationUserType organizationUserType);

}
