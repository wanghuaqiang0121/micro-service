package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.OrganizationUserTeamRole;
import org.service.core.dao.IBaseMapper;

public interface OrganizationUserTeamRoleMapper extends IBaseMapper<OrganizationUserTeamRole> {
	
	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationOrganizationTeam
	 * @return
	 * @date 2018年3月20日
	 * @version 1.0
	 * @description 删除 (删除权限)
	 */
	public int deleteRole(OrganizationUserTeamRole organizationOrganizationTeam);

	/**
	 * 
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param organizationOrganizationTeam
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 查询机构用户在这个团队的权限(是否存在)
	 */
	public List<Map<String, Object>> getOrganizationUserRoleIsChoose(OrganizationUserTeamRole organizationOrganizationTeam);

	public int deleteByDoctorDoctorTeamId(OrganizationUserTeamRole organizationOrganizationTeam);

	public List<Map<String, Object>> getOrganizationUserRoles(
			OrganizationUserTeamRole organizationOrganizationTeam);

	
}
