package org.module.user.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.user.dao.itv.OrganizationMapper;
import org.module.user.domain.itv.Organization;
import org.springframework.stereotype.Service;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月16日
 * @Version
 * @Description 机构
 */
@Service
public class OrganizationService {

	@Resource
	private OrganizationMapper organizationMapper;

	public Map<String, Object> getOne(Organization organization) {
		return organizationMapper.getOne(organization);
	}

	public List<Map<String, Object>> getList(Organization organization) {
		return organizationMapper.getList(organization);
	}

	public Map<String, Object> getSmsInfo(Organization organization) {
		return organizationMapper.getSmsInfo(organization);
	}

	public Map<String, Object> getWechatInfo(Organization organization) {
		return organizationMapper.getWechatInfo(organization);
	}
}
