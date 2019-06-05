package org.module.organization.configure.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationUserModule extends BaseEntity {
    private Integer id;

    @NotNull(message="{organization.user.module.organizationUserId.is.not.null}",groups={Insert.class,SelectAll.class})
    private Integer organizationUserId;

    @NotNull(message="{organization.user.module.organizationModuleId.is.not.null}",groups={Insert.class})
    private Integer organizationModuleId;
    
    private Integer organizationId;

    private Integer status;

    private String systemModuleName;
    
    public String getSystemModuleName() {
		return systemModuleName;
	}

	public void setSystemModuleName(String systemModuleName) {
		this.systemModuleName = systemModuleName;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationUserId() {
        return organizationUserId;
    }

    public void setOrganizationUserId(Integer organizationUserId) {
        this.organizationUserId = organizationUserId;
    }

    public Integer getOrganizationModuleId() {
        return organizationModuleId;
    }

    public void setOrganizationModuleId(Integer organizationModuleId) {
        this.organizationModuleId = organizationModuleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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