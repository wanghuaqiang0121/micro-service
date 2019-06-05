package org.module.organization.permission.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年4月28日
 * @Version 
 * @Description 权限菜单关联表
 */
public class OperationalPermissionMenu extends BaseEntity {
	public interface GetPermissionMenuIsChoose{};
	private Integer id;
	@NotNull(message = "{operational.permission.menu.operational.permission.id.notnull.valid}", groups = { Insert.class,GetPermissionMenuIsChoose.class})
	private Integer operationalPermissionId;
	@NotNull(message = "{operational.permission.menu.operational.menu.id.notnull.valid}", groups = { Insert.class})
	private Integer operationalMenuId;
	
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
	
	
	public Integer getOperationalMenuId() {
		return operationalMenuId;
	}
	public void setOperationalMenuId(Integer operationalMenuId) {
		this.operationalMenuId = operationalMenuId;
	}
	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,GetPermissionMenuIsChoose.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,GetPermissionMenuIsChoose.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
	
	

}
