package org.module.organization.configure.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationRole extends BaseEntity {
    private Integer id;

    private Integer organizationId;

    private Integer moduleOperationalRoleId;
    
    private Integer organizationUserId;

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

    public Integer getModuleOperationalRoleId() {
        return moduleOperationalRoleId;
    }

    public void setModuleOperationalRoleId(Integer moduleOperationalRoleId) {
        this.moduleOperationalRoleId = moduleOperationalRoleId;
    }
    
    public Integer getOrganizationUserId() {
		return organizationUserId;
	}

	public void setOrganizationUserId(Integer organizationUserId) {
		this.organizationUserId = organizationUserId;
	}

	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}