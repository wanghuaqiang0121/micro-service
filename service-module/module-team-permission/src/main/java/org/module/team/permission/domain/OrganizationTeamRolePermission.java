package org.module.team.permission.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationTeamRolePermission extends BaseEntity {
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年5月28日
	 * @Version OrganizationTeamRolePermission
	 * @Description 团队角色（拥有未拥有）的权限
	 */
	public interface HaveAndNotHaveOperation {

	}

	private Integer id;
	@NotNull(message = "{organizationTeamRolePermission.organizationTeamRoleId.is.not.null}", groups = { Insert.class })
	private Integer organizationTeamRoleId;
	@NotNull(message = "{organizationTeamRolePermission.organizationTeamPermissionId.is.not.null}", groups = {
			Insert.class })
	private Integer organizationTeamPermissionId;
	private String name;
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Integer getOrganizationTeamPermissionId() {
		return organizationTeamPermissionId;
	}

	public void setOrganizationTeamPermissionId(Integer organizationTeamPermissionId) {
		this.organizationTeamPermissionId = organizationTeamPermissionId;
	}
}