package org.module.organization.configure.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationServiceType extends BaseEntity {
    private Integer id;
    @NotNull(message = "{organization.service.type.organization.id.not.null}", groups = { Insert.class})
    private Integer organizationId;
    @NotNull(message = "{organization.service.type.service.type.id.not.null}", groups = { Insert.class})
    private Integer serviceTypeId;

    private String name;
    
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

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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