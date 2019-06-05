package org.module.platform.configuration.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationOrganizationTeamRole extends BaseEntity {
    private Integer id;
    @NotNull(message = "{organization.organization.team.role.organization.id.not.null.valid}", groups = { Insert.class,SelectAll.class })
    private Integer organizationId;
    @NotNull(message = "{organization.organization.team.role.organization.team.role.id.not.null.valid}", groups = { Insert.class })
    private Integer organizationTeamRoleId;
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