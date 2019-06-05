package org.module.organization.configure.domain;


import javax.validation.constraints.NotNull;

import org.module.organization.configure.domain.doctor.OrganizationTeamRole;
import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月20日
 * @Version
 * @Description 机构用户和团队关联表
 */
public class OrganizationUserTeamRole extends BaseEntity {
	/**
	 * 
	 * @author <font color="red"><b>Chen.Yan</b></font>
	 * @Date 2018年5月7日
	 * @Version OrganizationOrganizationTeam
	 * @Description  查询机构用户在这个团队的权限(是否存在)
	 */
	public interface GetOrganizationUserRoleIsChoose{};
	
	private Integer id;
	 @NotNull(message = "{organization.organization.team.doctor.doctor.team.id.is.not.null}" , groups = {Insert.class,GetOrganizationUserRoleIsChoose.class })
	private Integer organizationUserTeamId;
	 @NotNull(message = "{organization.organization.team.organization.team.id.is.not.null}" , groups = {Insert.class})
	private Integer organizationTeamRoleId;
	private Integer status;
	 @NotNull(message = "{organization.organization.team.organization.user.id.is.not.null}" , groups = {GetOrganizationUserRoleIsChoose.class })
	private Integer organizationUserId;
	 
	private Integer organizationId;
	 
	private Integer isChoose;
	
	private OrganizationTeamRole organizationTeamRole;
	
	
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	public Integer getOrganizationUserTeamId() {
		return organizationUserTeamId;
	}
	public void setOrganizationUserTeamId(Integer organizationUserTeamId) {
		this.organizationUserTeamId = organizationUserTeamId;
	}
	public Integer getIsChoose() {
		return isChoose;
	}
	public void setIsChoose(Integer isChoose) {
		this.isChoose = isChoose;
	}
	public OrganizationTeamRole getOrganizationTeamRole() {
		return organizationTeamRole;
	}
	public void setOrganizationTeamRole(OrganizationTeamRole organizationTeamRole) {
		this.organizationTeamRole = organizationTeamRole;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrganizationTeamRoleId() {
		return organizationTeamRoleId;
	}
	public void setOrganizationTeamRoleId(Integer organizationTeamRoleId) {
		this.organizationTeamRoleId = organizationTeamRoleId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getOrganizationUserId() {
		return organizationUserId;
	}
	public void setOrganizationUserId(Integer organizationUserId) {
		this.organizationUserId = organizationUserId;
	}
	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,GetOrganizationUserRoleIsChoose.class})
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,GetOrganizationUserRoleIsChoose.class})
	public Integer getPageSize() {
		return super.getPageSize();
	}
}
