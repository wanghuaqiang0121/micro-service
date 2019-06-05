package org.module.organization.configure.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationOrganizationTeamRole extends BaseEntity {
    private Integer id;
   // @NotNull(message = "{organization.organization.team.role.organization.id.not.null.valid}", groups = { Insert.class })
    private Integer organizationId;
   // @NotNull(message = "{organization.organization.team.role.organization.team.role.id.not.null.valid}", groups = { Insert.class })
    private Integer organizationTeamRoleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getOrganizationTeamRoleId() {
        return organizationTeamRoleId;
    }

    public void setOrganizationTeamRoleId(Integer organizationTeamRoleId) {
        this.organizationTeamRoleId = organizationTeamRoleId;
    }
    
    @Override
   	@NotNull(message = "{page.empty}", groups = { SelectAll.class})
   	public Integer getPage() {
   		return super.getPage();
   	}

   	@Override
   	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class })
   	public Integer getPageSize() {
   		return super.getPageSize();
   	}
}