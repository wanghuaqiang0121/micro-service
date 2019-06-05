package org.module.platform.configuration.domain.organization;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年4月18日
 * @Version 
 * @Description 机构模块
 */
public class OrganizationModule extends BaseEntity{
	/**
	 * 
	 * @author <font color="red"><b>Chen.Yan</b></font>
	 * @Date 2018年4月18日
	 * @Version OrganizationModule
	 * @Description 查询机构关联和未关联的模块列表
	 */
	public interface getOrganizationModuleIsChoose{};
	
	private Integer id;
	@NotNull(message = "{organization.module.organization.id.not.null.valid}", groups = { Insert.class,getOrganizationModuleIsChoose.class})
	private Integer organizationId;
	@NotNull(message = "{organization.module.system.module.id.not.null.valid}", groups = { Insert.class})
	private Integer systemModuleId;
	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,getOrganizationModuleIsChoose.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,getOrganizationModuleIsChoose.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}

}
