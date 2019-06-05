package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationTeamMapper;
import org.module.organization.configure.domain.OrganizationTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationTeamService {
  @Autowired
  private OrganizationTeamMapper organizationTeamMapper;
  
  public	int insert(OrganizationTeam organizationTeam) {
		return organizationTeamMapper.insert(organizationTeam);

	}

	public	int update(OrganizationTeam organizationTeam) {
		return organizationTeamMapper.update(organizationTeam);
	}

	public	int delete(OrganizationTeam organizationTeam) {
		return organizationTeamMapper.delete(organizationTeam);
	}

	public	Map<String, Object> getRepeat(OrganizationTeam organizationTeam) {
		return organizationTeamMapper.getRepeat(organizationTeam);
	}

	public	Map<String, Object> getOne(OrganizationTeam organizationTeam) {
		return organizationTeamMapper.getOne(organizationTeam);
	}

	public	List<Map<String, Object>> getList(OrganizationTeam organizationTeam){
		return organizationTeamMapper.getList(organizationTeam);
	}

	public Map<String, Object> getOrganizationVerType(OrganizationTeam organizationTeam) {
		return organizationTeamMapper.getOrganizationVerType(organizationTeam);
	}

	public List<Map<String, Object>> getwechatQrCode(OrganizationTeam organizationTeam) {
		return organizationTeamMapper.getwechatQrCode(organizationTeam);
	}
}
