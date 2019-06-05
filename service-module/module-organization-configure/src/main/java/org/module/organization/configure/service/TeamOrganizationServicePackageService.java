package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.TeamOrganizationServicePackageMapper;
import org.module.organization.configure.domain.TeamOrganizationServicePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamOrganizationServicePackageService {

	@Autowired
	private TeamOrganizationServicePackageMapper teamOrganizationServicePackageMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 删除团队机构服务包关联
	*/
	public int delete(TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.delete(teamOrganizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 添加团队机构服务包关联
	*/
	public int insert(TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.insert(teamOrganizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 查询数据是否存在重复
	*/
	public Map<String, Object> getRepeat(TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.getRepeat(teamOrganizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 查询详情
	*/
	public Map<String, Object> getOne(TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.getOne(teamOrganizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 修改团队机构服务包关联
	*/
	public int update(TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.update(teamOrganizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 团队机构服务包列表
	*/
	public List<Map<String, Object>> getList(TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.getList(teamOrganizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月21日
	* @version 1.0
	* @description 查询本机构服务包对应团队授权操作
	*/
	public List<Map<String, Object>> getTeamAuthorizeList(
			TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.getTeamAuthorizeList(teamOrganizationServicePackage);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param teamOrganizationServicePackage
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 根据机构服务包id查询是否有团队使用
	*/
	public List<Map<String, Object>> getListByOrganizationServicePackageId(
			TeamOrganizationServicePackage teamOrganizationServicePackage) {
		return teamOrganizationServicePackageMapper.getListByOrganizationServicePackageId(teamOrganizationServicePackage);
	}
	
}
