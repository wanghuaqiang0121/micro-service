package org.module.organization.permission.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年4月28日
 * @Version 
 * @Description 角色权限关联表
 */
public class OperationalRolePermission extends BaseEntity {
	/**
	 * 
	 * @author <font color="red"><b>Chen.Yan</b></font>
	 * @Date 2018年5月2日
	 * @Version OperationalRolePermission
	 * @Description 查询该角色是否拥有的权限
	 */
	public interface GetPermissionOperationIsChoose{};
	private Integer id;
	@NotNull(message = "{operational.role.permission.operational.permission.id.notnull.valid}", groups = { Insert.class})
	private Integer operationalPermissionId;
	@NotNull(message = "{operational.role.permission.operational.role.id.notnull.valid}", groups = { Insert.class,GetPermissionOperationIsChoose.class})
	private Integer operationalRoleId;
	
	private String code;
	private String name;
	
	
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
	
	public Integer getOperationalPermissionId() {
		return operationalPermissionId;
	}
	public void setOperationalPermissionId(Integer operationalPermissionId) {
		this.operationalPermissionId = operationalPermissionId;
	}
	
	
	public Integer getOperationalRoleId() {
		return operationalRoleId;
	}
	public void setOperationalRoleId(Integer operationalRoleId) {
		this.operationalRoleId = operationalRoleId;
	}
	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,GetPermissionOperationIsChoose.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,GetPermissionOperationIsChoose.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
	
	

}
