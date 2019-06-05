package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationUserTypeMapper;
import org.module.organization.configure.domain.OrganizationUserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationUserTypeService {

	@Autowired
	private OrganizationUserTypeMapper organizationUserTypeMapper;

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationUserType
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 新增机构用户标签关联
	 */
	public int insert(OrganizationUserType organizationUserType) {
		return organizationUserTypeMapper.insert(organizationUserType);

	}

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationUserType
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 删除机构用户标签关联
	 */
	public int delete(OrganizationUserType organizationUserType) {
		return organizationUserTypeMapper.delete(organizationUserType);
	}

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationUserType
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 检查数据是否重复
	 */
	public Map<String, Object> getRepeat(OrganizationUserType organizationUserType) {
		return organizationUserTypeMapper.getRepeat(organizationUserType);
	}

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationUserType
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 查询机构用户标签关联列表
	 */
	public List<Map<String, Object>> getList(OrganizationUserType organizationUserType) {
		return organizationUserTypeMapper.getList(organizationUserType);
	}

	public List<Map<String, Object>> getOrganizationUserTypeIsNull(OrganizationUserType organizationUserType) {
		return organizationUserTypeMapper.getOrganizationUserTypeIsNull(organizationUserType);
	}

	public List<Map<String, Object>> getOrganizationUserTypehaveAndNotHaveList(
			OrganizationUserType organizationUserType) {
		return organizationUserTypeMapper.getOrganizationUserTypehaveAndNotHaveList(organizationUserType);
	}

}
