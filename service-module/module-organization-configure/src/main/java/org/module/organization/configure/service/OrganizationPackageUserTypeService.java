package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationPackageUserTypeMapper;
import org.module.organization.configure.domain.OrganizationPackageUserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationPackageUserTypeService {

	@Autowired
	private OrganizationPackageUserTypeMapper organizationPackageUserTypeMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageUserType
	* @return 
	* @date 2018年3月20日
	* @version 1.0 
	* @description 查询数据是否存在重复
	*/
	public Map<String, Object> getRepeat(OrganizationPackageUserType organizationPackageUserType) {
		return organizationPackageUserTypeMapper.getRepeat(organizationPackageUserType);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageUserType
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 添加服务包使用人群关联
	*/
	public int insert(OrganizationPackageUserType organizationPackageUserType) {
		return organizationPackageUserTypeMapper.insert(organizationPackageUserType);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageUserType
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除服务包使用人群关联
	*/
	public int delete(OrganizationPackageUserType organizationPackageUserType) {
		return organizationPackageUserTypeMapper.delete(organizationPackageUserType);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param oPackageUserType 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除服务包所有使用人群关联
	*/
	public int deleteByOrganizationPackageId(OrganizationPackageUserType oPackageUserType) {
		return organizationPackageUserTypeMapper.deleteByOrganizationPackageId(oPackageUserType);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageUserType
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 批量添加服务包使用人群关联
	*/
	public int batchInsert(List<OrganizationPackageUserType> organizationPackageUserTypes) {
		return organizationPackageUserTypeMapper.batchInsert(organizationPackageUserTypes);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param packageUserTypeRepeat
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 查询服务包的人群类型
	*/
	public List<Map<String, Object>> getList(OrganizationPackageUserType packageUserTypeRepeat) {
		return organizationPackageUserTypeMapper.getList(packageUserTypeRepeat);
	}
	
}
