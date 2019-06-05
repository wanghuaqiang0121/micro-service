package org.module.organization.configure.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.service.core.entity.BaseEntity;


public class OrganizationPackageUserType extends BaseEntity {
	
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月20日
	 * @Version OrganizationPackageUserType
	 * @Description 批量添加校验
	 */
	public interface BatchInsert{};
	
    private Integer id;

    @NotNull(message = "{organization.package.user.type.organization.service.package.id.is.not.null}" , groups = {Insert.class })
    private Integer organizationServicePackageId;

    @NotNull(message = "{organization.package.user.type.user.type.id.is.not.null}" , groups = {Insert.class })
    private Integer userTypeId;
    
    @NotEmpty(message = "{organization.package.user.type.organization.package.user.types.is.not.null}" , groups = {BatchInsert.class })
    private List<OrganizationPackageUserType> organizationPackageUserTypes;
    
    public List<OrganizationPackageUserType> getOrganizationPackageUserTypes() {
		return organizationPackageUserTypes;
	}

	public void setOrganizationPackageUserTypes(List<OrganizationPackageUserType> organizationPackageUserTypes) {
		this.organizationPackageUserTypes = organizationPackageUserTypes;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationServicePackageId() {
        return organizationServicePackageId;
    }

    public void setOrganizationServicePackageId(Integer organizationServicePackageId) {
        this.organizationServicePackageId = organizationServicePackageId;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }
}