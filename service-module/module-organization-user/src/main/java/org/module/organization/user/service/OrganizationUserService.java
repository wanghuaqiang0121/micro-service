package org.module.organization.user.service;

import java.util.List;
import java.util.Map;

import org.module.organization.user.dao.OrganizationUserMapper;
import org.module.organization.user.domain.OrganizationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationUserService {
	@Autowired
	private OrganizationUserMapper organizationUserMapper;

	public List<Map<String, Object>> getUserByAccount(OrganizationUser user) {
		return organizationUserMapper.getUserByAccount(user);
	}

	public List<Map<String, Object>> getOrganizationUserModuleList(OrganizationUser user) {
		return organizationUserMapper.getOrganizationUserModuleList(user);
	}

	public int update(OrganizationUser organizationUser) {
		return organizationUserMapper.update(organizationUser);
	}

	public List<Map<String, Object>> getOrganizationUserModuleMenuList(OrganizationUser organizationUser) {
		return organizationUserMapper.getOrganizationUserModuleMenuList(organizationUser);
	}

	public List<Map<String, Object>> getOrganizationUserModuleRoleList(OrganizationUser organizationUser) {
		return organizationUserMapper.getOrganizationUserModuleRoleList(organizationUser);
	}

	public List<Map<String, Object>> getOrganizationUserModulePermissionList(OrganizationUser organizationUser) {
		 return organizationUserMapper.getOrganizationUserModulePermissionList(organizationUser);
	}

	public List<Map<String, Object>> getOrganizationUserModuleOperationList(OrganizationUser organizationUser) {
		return organizationUserMapper.getOrganizationUserModuleOperationList(organizationUser);
	}
	
	public List<Map<String, Object>> getOrganzationUserOrganzationList(OrganizationUser organizationUser){
		return organizationUserMapper.getOrganzationUserOrganzationList(organizationUser);	
	}

	public List<Map<String, Object>> getManagerRoleList(OrganizationUser organizationUser) {
		return organizationUserMapper.getManagerRoleList(organizationUser);
	}

	public List<Map<String, Object>> getManagerModuleMenuList(OrganizationUser organizationUser) {
		return organizationUserMapper.getManagerModuleMenuList(organizationUser);
	}

	public List<Map<String, Object>> getManagerPermissionList(OrganizationUser organizationUser) {
		return organizationUserMapper.getManagerPermissionList(organizationUser);
	}

	public List<Map<String, Object>> getManagerOperationList(OrganizationUser organizationUser) {
		return organizationUserMapper.getManagerOperationList(organizationUser);
	}

	public Map<String, Object> getOne(OrganizationUser organizationUser) {
		return organizationUserMapper.getOne(organizationUser);
	}

	public List<Map<String, Object>> getUserByAccountAndPassword(OrganizationUser user) {
		return organizationUserMapper.getUserByAccountAndPassword(user);
	}
	
	public Map<String, Object> getDataReturn(OrganizationUser organizationUser){
		return organizationUserMapper.getDataReturn(organizationUser);
	}

	public Map<String, Object> getOrganizationUserCertificate(OrganizationUser user) {
		return organizationUserMapper.getOrganizationUserCertificate(user);
	}

}
