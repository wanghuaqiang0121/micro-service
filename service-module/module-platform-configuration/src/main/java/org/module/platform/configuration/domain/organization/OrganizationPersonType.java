package org.module.platform.configuration.domain.organization;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.module.platform.configuration.domain.organization.OrganizationModule.getOrganizationModuleIsChoose;
import org.service.core.entity.BaseEntity;

/**
* @author <font color="red"><b>Zhang.Xiang.Zheng</b></font> 
* @date 2018年6月6日 
* @version 1.0
* @description 机构人员类型
 */
public class OrganizationPersonType extends BaseEntity{
	
	
	private Integer id;
	
	@NotBlank(message = "{organization.person.type.name.not.blank.valid}", groups = {Insert.class})
	private String name;
	
	private String code;
	
	@NotBlank(message = "{organization.person.type.role.not.blank.valid}", groups = {Insert.class})
	private String role;
	
	private Date createDate;
	
	
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

	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
