package org.module.organization.configure.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.service.core.entity.BaseEntity;

public class OrganizationPackageService extends BaseEntity {
	
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月20日
	 * @Version OrganizationPackageService
	 * @Description 批量添加验证类
	 */
	public interface BatchInsert{};
	
    private Integer id;
    
    @NotNull(message = "{organization.package.service.organization.service.package.id.is.not.null}" , groups = {Insert.class,SelectAll.class })
    private Integer organizationServicePackageId;

    @NotNull(message = "{organization.package.service..service.type.id.is.not.null}" , groups = {Insert.class })
    private Integer serviceTypeId;
    
    private String name;

    private BigDecimal price;

    @NotNull(message = "{organization.package.service.service.times.id.is.not.null}" , groups = {Insert.class })
    private Integer times;
    
    @NotEmpty(message = "{organization.package.service.organization.package.services.is.not.null}" , groups = {BatchInsert.class })
    private List<OrganizationPackageService> organizationPackageServices;
    
    private Integer organizationId;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OrganizationPackageService> getOrganizationPackageServices() {
		return organizationPackageServices;
	}

	public void setOrganizationPackageServices(List<OrganizationPackageService> organizationPackageServices) {
		this.organizationPackageServices = organizationPackageServices;
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

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
    
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}