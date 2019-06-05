package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.OrganizationPackageServiceMapper;
import org.module.organization.configure.domain.OrganizationPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationPackageServiceService {

	@Autowired
	private OrganizationPackageServiceMapper organizationPackageServiceMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageService
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 查询数据是否存在重复
	*/
	public Map<String, Object> getRepeat(OrganizationPackageService organizationPackageService) {
		return organizationPackageServiceMapper.getRepeat(organizationPackageService);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageService
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 添加机构服务包和服务关联
	*/
	public int insert(OrganizationPackageService organizationPackageService) {
		return organizationPackageServiceMapper.insert(organizationPackageService);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param oPackageService
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除服务包所有服务关联
	*/
	public int deleteByOrganizationPackageId(OrganizationPackageService oPackageService) {
		return organizationPackageServiceMapper.deleteByOrganizationPackageId(oPackageService);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageService
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 批量添加机构服务包和服务关联
	*/
	public int batchInsert(List<OrganizationPackageService> organizationPackageService) {
		return organizationPackageServiceMapper.batchInsert(organizationPackageService);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageService
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除服务包服务关联(单条)
	*/
	public int delete(OrganizationPackageService organizationPackageService) {
		return organizationPackageServiceMapper.delete(organizationPackageService);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageService
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 查询详情
	*/
	public Map<String, Object> getOne(OrganizationPackageService organizationPackageService) {
		return organizationPackageServiceMapper.getOne(organizationPackageService);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationPackageService
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 修改服务包服务关联
	*/
	public int update(OrganizationPackageService organizationPackageService) {
		return organizationPackageServiceMapper.update(organizationPackageService);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param packageServiceRepeat
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 查询服务包的服务项列表
	*/
	public List<Map<String, Object>> getList(OrganizationPackageService packageServiceRepeat) {
		return organizationPackageServiceMapper.getList(packageServiceRepeat);
	}

	public List<Map<String, Object>> getOrganizationPackageIsNullService(
			OrganizationPackageService organizationPackageService) {
		return organizationPackageServiceMapper.getOrganizationPackageIsNullService(organizationPackageService);
	}
	
}
