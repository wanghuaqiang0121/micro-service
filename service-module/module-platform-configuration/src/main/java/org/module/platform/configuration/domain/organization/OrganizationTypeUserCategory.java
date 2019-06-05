package org.module.platform.configuration.domain.organization;

import javax.validation.constraints.NotNull;

import org.module.platform.configuration.domain.organization.OrganizationModule.getOrganizationModuleIsChoose;
import org.service.core.entity.BaseEntity;

/**
* @author <font color="red"><b>Zhang.Xiang.Zheng</b></font> 
* @date 2018年6月6日 
* @version 1.0
* @description 机构类型用户类型关联表
 */
public class OrganizationTypeUserCategory extends BaseEntity{
	
	
	private Integer id;
	
	@NotNull(message = "{organization.type.id.not.null.valid}", groups = {Insert.class})
	private Integer organizationTypeId;
	
	@NotNull(message = "{organization.person.type.id.not.null.valid}", groups = {Insert.class})
	private Integer organizationPersonTypeId;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrganizationTypeId() {
		return organizationTypeId;
	}

	public void setOrganizationTypeId(Integer organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public Integer getOrganizationPersonTypeId() {
		return organizationPersonTypeId;
	}

	public void setOrganizationPersonTypeId(Integer organizationPersonTypeId) {
		this.organizationPersonTypeId = organizationPersonTypeId;
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
