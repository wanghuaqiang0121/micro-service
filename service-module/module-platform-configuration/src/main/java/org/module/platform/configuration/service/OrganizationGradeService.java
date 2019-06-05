package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.platform.configuration.dao.OrganizationGradeMapper;
import org.module.platform.configuration.domain.OrganizationGrade;
import org.springframework.stereotype.Service;

@Service
public class OrganizationGradeService {

	@Resource
	private OrganizationGradeMapper organizationGradeMapper;

	public int insert(OrganizationGrade organizationGrade) {
		return organizationGradeMapper.insert(organizationGrade);
	}

	public int update(OrganizationGrade organizationGrade) {
		return organizationGradeMapper.update(organizationGrade);
	}

	public List<Map<String, Object>> getList(OrganizationGrade organizationGrade) {
		return organizationGradeMapper.getList(organizationGrade);

	}

	public Map<String, Object> getRepeat(OrganizationGrade organizationGrade) {
		return organizationGradeMapper.getRepeat(organizationGrade);
	}
	
	public Map<String, Object> getDetail(OrganizationGrade organizationGrade) {
		return organizationGradeMapper.getOne(organizationGrade);
	}

}
