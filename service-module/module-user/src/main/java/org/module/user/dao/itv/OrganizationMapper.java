package org.module.user.dao.itv;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.module.user.domain.itv.Organization;
import org.service.core.dao.IBaseMapper;

@Mapper
public interface OrganizationMapper extends IBaseMapper<Organization> {
	int deleteByPrimaryKey(Integer id);

	Map<String, Object> getSmsInfo(Organization organization);

	Map<String, Object> getWechatInfo(Organization organization);
}