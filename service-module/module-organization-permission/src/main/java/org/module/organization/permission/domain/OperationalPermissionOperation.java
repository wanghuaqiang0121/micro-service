package org.module.organization.permission.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年4月28日
 * @Version 
 * @Description 权限操作关联表
 */
public class OperationalPermissionOperation extends BaseEntity {
	/**
	 * 
	 * @author <font color="red"><b>Chen.Yan</b></font>
	 * @Date 2018年5月2日
	 * @Version OperationalPermissionOperation
	 * @Description 查询该权限是否拥有的操作
	 */
	public interface GetPermissionOperationIsChoose{};
	private Integer id;
	@NotNull(message = "{operational.permission.operation.operational.permission.id.notnull.valid}", groups = { Insert.class,GetPermissionOperationIsChoose.class})
	private Integer operationalPermissionId;
	@NotNull(message = "{operational.permission.operational.operational.operation.id.notnull.valid}", groups = { Insert.class})
	private Integer operationalOperationId;
	
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
		
	public Integer getOperationalOperationId() {
		return operationalOperationId;
	}
	public void setOperationalOperationId(Integer operationalOperationId) {
		this.operationalOperationId = operationalOperationId;
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
