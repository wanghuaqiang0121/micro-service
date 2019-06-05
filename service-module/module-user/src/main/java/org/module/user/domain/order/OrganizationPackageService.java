package org.module.user.domain.order;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationPackageService extends BaseEntity {
    private Integer id;

    private Integer organizationServicePackageId;

    private Integer serviceTypeId;

    private BigDecimal price;

    private Integer times;

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