package org.module.platform.configuration.domain.organization;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年4月18日
 * @Version 
 * @Description 模块角色关联表
 */
public class ModuleOperationalRole extends BaseEntity{
	public interface ModuleOperationalRoleList{}
	
	private Integer id;
	private String name;
	@NotNull(message = "{organization.module.id.not.null.valid}", groups = { ModuleOperationalRoleList.class})
	private Integer systemModuleId;
	private Integer operationalRoleId;
	private Integer organizationId;
	private String roleName;
	private String code;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,ModuleOperationalRoleList.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,ModuleOperationalRoleList.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}
