package org.module.organization.configure.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationUserRole extends BaseEntity {
    /**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年4月18日
     * @Version OrganizationUserRole
     * @Description 机构用户在该机构下拥有和未拥有的模块列表
     */
    public interface haveAndNotHave {

	}

	private Integer id;
    @NotNull(message = "{organization.user.role.organizationUserId.notnull.valid}", groups = { Insert.class, haveAndNotHave.class})
    private Integer organizationUserId;
    @NotNull(message = "{organization.user.role.organizationRoleId.notnull.valid}", groups = { Insert.class})
    private Integer organizationRoleId;
    @NotNull(message = "{organization.user.role.moduleId.notnull.valid}", groups = { haveAndNotHave.class})
    private Integer moduleId;
    private Integer organizationId;
    private Integer status;
    
    private String operationalRoleName;

    public String getOperationalRoleName() {
		return operationalRoleName;
	}

	public void setOperationalRoleName(String operationalRoleName) {
		this.operationalRoleName = operationalRoleName;
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

    public Integer getOrganizationRoleId() {
        return organizationRoleId;
    }

    public void setOrganizationRoleId(Integer organizationRoleId) {
        this.organizationRoleId = organizationRoleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class ,haveAndNotHave.class})
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class ,haveAndNotHave.class})
	public Integer getPageSize() {
		return super.getPageSize();
	}
}