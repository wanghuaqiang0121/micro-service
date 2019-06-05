package org.module.platform.configuration.domain.organization;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationRole extends BaseEntity {
    /**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年6月6日
     * @Version OrganizationRole
     * @Description 批量新增
     */
    public interface batchInsert {

	}

	private Integer id;

    @NotNull(message = "{organization.id.not.null.valid}", groups = { Insert.class, SelectAll.class,batchInsert.class})
    private Integer organizationId;

    @NotNull(message = "{organization.module.id.not.null.valid}", groups = { Insert.class, SelectAll.class,batchInsert.class})
    private Integer systemModuleId;
    
    @NotNull(message = "{organization.operational.role.id.not.null.valid}", groups = { Insert.class})
    private Integer operationalRoleId;
    
    //@NotEmpty(message = "{organization.operational.role.id.not.null.valid}", groups = { batchInsert.class})
    private List<Integer> operationalRoles;
    
    
    public List<Integer> getOperationalRoles() {
		return operationalRoles;
	}

	public void setOperationalRoles(List<Integer> operationalRoles) {
		this.operationalRoles = operationalRoles;
	}

	private Integer status;

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
	
	public Integer getSystemModuleId() {
		return systemModuleId;
	}

	public void setSystemModuleId(Integer systemModuleId) {
		this.systemModuleId = systemModuleId;
	}

	public Integer getOperationalRoleId() {
		return operationalRoleId;
	}

	public void setOperationalRoleId(Integer operationalRoleId) {
		this.operationalRoleId = operationalRoleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}