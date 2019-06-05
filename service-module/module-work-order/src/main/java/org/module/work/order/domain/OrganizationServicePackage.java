package org.module.work.order.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationServicePackage extends BaseEntity {
	
    private Integer id;
    private Integer pid;
    
    private Integer organizationId;

    private Integer auditOrganizationId;
    private Integer servicePackageTypeId;
    
    private String name;
    private BigDecimal price;
    private Float discount;

    private Integer status;
    private Integer acquisitiveType;
    private Integer acquisitive;

    private String cover;

    private Date createDate;

    private String remark;

    private String protocol;


	
	
    public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

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

    public Integer getAuditOrganizationId() {
        return auditOrganizationId;
    }

    public void setAuditOrganizationId(Integer auditOrganizationId) {
        this.auditOrganizationId = auditOrganizationId;
    }

    public Integer getServicePackageTypeId() {
        return servicePackageTypeId;
    }

    public void setServicePackageTypeId(Integer servicePackageTypeId) {
        this.servicePackageTypeId = servicePackageTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAcquisitiveType() {
        return acquisitiveType;
    }

    public void setAcquisitiveType(Integer acquisitiveType) {
        this.acquisitiveType = acquisitiveType;
    }

    public Integer getAcquisitive() {
        return acquisitive;
    }

    public void setAcquisitive(Integer acquisitive) {
        this.acquisitive = acquisitive;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    @Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class})
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class})
	public Integer getPageSize() {
		return super.getPageSize();
	}
}