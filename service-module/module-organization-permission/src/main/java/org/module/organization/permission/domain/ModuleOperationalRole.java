package org.module.organization.permission.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年4月28日
 * @Version 
 * @Description 模块角色关联表
 */
public class ModuleOperationalRole extends BaseEntity {
	/**
	 * 
	 * @author <font color="red"><b>Chen.Yan</b></font>
	 * @Date 2018年5月2日
	 * @Version ModuleOperationalRole
	 * @Description -查询该模块是否拥有的角色
	 */
	public interface GetModuleRoleIsChoose{}
	
	private Integer id;
	//@Length(min = 0, max = 32, message = "{module.operational.role.name.length}", groups = { Insert.class})
	//@NotBlank(message = "{module.operational.role.name.notblank.valid}", groups = { Insert.class})
	private String name;
	@NotNull(message = "{module.operational.role.system.module.id.notnull.valid}", groups = { Insert.class,GetModuleRoleIsChoose.class})
	private Integer systemModuleId;
	@NotNull(message = "{module.operational.role.system.operational.role.id.notnull.valid}", groups = { Insert.class})
	private Integer operationalRoleId;
	
	private String code;
	
	
	
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
	
	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,GetModuleRoleIsChoose.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,GetModuleRoleIsChoose.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
	
	

}
