package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import org.module.platform.configuration.dao.OrganizationWechatInfoMapper;
import org.module.platform.configuration.domain.OrganizationWechatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationWechatInfoService {

	@Autowired
	private OrganizationWechatInfoMapper organizationWechatInfoMapper;

	public List<Map<String, Object>> getList(OrganizationWechatInfo organizationWechatInfo) {
		return organizationWechatInfoMapper.getList(organizationWechatInfo);
	}

	public int insert(OrganizationWechatInfo organizationWechatInfo) {
		return organizationWechatInfoMapper.insert(organizationWechatInfo);
	}

	public Map<String, Object> getOne(OrganizationWechatInfo organizationWechatInfo) {
		return organizationWechatInfoMapper.getOne(organizationWechatInfo);
	}

	public int update(OrganizationWechatInfo organizationWechatInfo) {
		return organizationWechatInfoMapper.update(organizationWechatInfo);
	}

	public Map<String, Object> getRepeat(OrganizationWechatInfo organizationWechatInfo) {
		return organizationWechatInfoMapper.getRepeat(organizationWechatInfo);
	}
}
