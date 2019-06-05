package org.module.organization.configure.service.doctor;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationUserTeamMapper;
import org.module.organization.configure.domain.OrganizationUserTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationUserTeamService {

	@Autowired
	private OrganizationUserTeamMapper doctorDoctorTeamMapper;
	
	
	public int insert(OrganizationUserTeam doctorDoctorTeam){
		return doctorDoctorTeamMapper.insert(doctorDoctorTeam);
	}


	public Map<String, Object> getRepeat(OrganizationUserTeam doctorDoctorTeam) {
		return doctorDoctorTeamMapper.getRepeat(doctorDoctorTeam);
	}


	public int delete(OrganizationUserTeam doctorDoctorTeam) {
		return doctorDoctorTeamMapper.delete(doctorDoctorTeam);
	}


	public List<Map<String, Object>> getList(OrganizationUserTeam doctorDoctorTeam) {
		return doctorDoctorTeamMapper.getList(doctorDoctorTeam);
	}


	public List<Map<String, Object>> getTeamDoctors(OrganizationUserTeam doctorDoctorTeam) {
		return doctorDoctorTeamMapper.getTeamDoctors(doctorDoctorTeam);
	}


	public List<Map<String, Object>> getOrganizationUserMembers(OrganizationUserTeam doctorDoctorTeam) {
		return doctorDoctorTeamMapper.getOrganizationUserMembers(doctorDoctorTeam);
	}
	
}
