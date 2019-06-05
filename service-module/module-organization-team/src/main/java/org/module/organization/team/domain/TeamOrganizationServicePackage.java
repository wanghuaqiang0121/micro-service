package org.module.organization.team.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class TeamOrganizationServicePackage extends BaseEntity {
    private Integer id;

    //@NotNull(message = "{team.organization.service.package.doctor.team.id.is.not.null}" , groups = {SelectAll.class})
    private Integer doctorTeamId;

    private Integer organizationServicePackageId;

    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer status;

    private Date createDate;

    private String remark;
    
    private Integer[] userTypeIds;
    
    private OrganizationSite site;
    
    private Organization organization;
    
    private Boolean isBasePublicHealth;
    
    private ServicePackageType servicePackageType;
    
    private OrganizationServicePackage organizationServicePackage;
    private String servicePackageTypeCode;
    private String province;
   	private String city;
   	private String area;
	private String code;
    public String getServicePackageTypeCode() {
		return servicePackageTypeCode;
	}

	public void setServicePackageTypeCode(String servicePackageTypeCode) {
		this.servicePackageTypeCode = servicePackageTypeCode;
	}

	public Organization getOrganization() {
		return organization;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public ServicePackageType getServicePackageType() {
		return servicePackageType;
	}

	public void setServicePackageType(ServicePackageType servicePackageType) {
		this.servicePackageType = servicePackageType;
	}

	public Boolean getIsBasePublicHealth() {
		return isBasePublicHealth;
	}

	public void setIsBasePublicHealth(Boolean isBasePublicHealth) {
		this.isBasePublicHealth = isBasePublicHealth;
	}

	public OrganizationServicePackage getOrganizationServicePackage() {
		return organizationServicePackage;
	}

	public void setOrganizationServicePackage(OrganizationServicePackage organizationServicePackage) {
		this.organizationServicePackage = organizationServicePackage;
	}

	public OrganizationSite getSite() {
		return site;
	}

	public void setSite(OrganizationSite site) {
		this.site = site;
	}

	public Integer[] getUserTypeIds() {
		return userTypeIds;
	}

	public void setUserTypeIds(Integer[] userTypeIds) {
		this.userTypeIds = userTypeIds;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorTeamId() {
        return doctorTeamId;
    }

    public void setDoctorTeamId(Integer doctorTeamId) {
        this.doctorTeamId = doctorTeamId;
    }

    public Integer getOrganizationServicePackageId() {
        return organizationServicePackageId;
    }

    public void setOrganizationServicePackageId(Integer organizationServicePackageId) {
        this.organizationServicePackageId = organizationServicePackageId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    
    public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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