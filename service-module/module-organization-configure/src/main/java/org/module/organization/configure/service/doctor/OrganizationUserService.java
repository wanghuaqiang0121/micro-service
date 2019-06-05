package org.module.organization.configure.service.doctor;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.doctor.OrganizationUserMapper;
import org.module.organization.configure.domain.doctor.OrganizationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationUserService {
	@Autowired
	private OrganizationUserMapper organizationUserMapper;

	public int insert(OrganizationUser organizationUser) {
		return organizationUserMapper.insert(organizationUser);

	}

	public int update(OrganizationUser organizationUser) {
		return organizationUserMapper.update(organizationUser);
	}

	public int delete(OrganizationUser organizationUser) {
		return organizationUserMapper.delete(organizationUser);
	}

	public Map<String, Object> getRepeat(OrganizationUser organizationUser) {
		return organizationUserMapper.getRepeat(organizationUser);
	}

	public Map<String, Object> getOne(OrganizationUser organizationUser) {
		return organizationUserMapper.getOne(organizationUser);
	}

	public List<Map<String, Object>> getList(OrganizationUser organizationUser) {
		return organizationUserMapper.getList(organizationUser);
	}

}
