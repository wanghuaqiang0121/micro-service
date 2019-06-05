package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class Organization extends BaseEntity {
    private Integer id;
    @NotBlank(message = "{organization.code.not.null.valid}", groups = { Insert.class})
    private String code;
    
    private Integer pid;
    @NotNull(message = "{organization.type.id.not.null.valid}", groups = { Insert.class})
    private Integer organizationTypeId;
    @NotBlank(message = "{organization.name.not.null.valid}", groups = { Insert.class})
    private String name;

    private String areaCode;

    private String contractNumberPrefix;
    @NotNull(message = "{organization.is.support.referral.not.null.valid}", groups = { Insert.class})
    private Boolean isSupportReferral;

    private Integer status;

    private String phone;

    private String logo;

    private String picture;

    private String province;

    private String city;

    private String area;

    private String street;

    private String community;

    private String address;

    private Double lng;

    private Double lat;

    private Date createDate;

    private String remark;

    
    
    private String role;
    private Boolean isAdministrative;
    
    private String persCode;
    
    private Integer judicialBoneAgeOrganizationId;
    private Integer judicialBoneAgeOrganizationTeamId;
    

	public String getPersCode() {
		return persCode;
	}

	public void setPersCode(String persCode) {
		this.persCode = persCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getOrganizationTypeId() {
        return organizationTypeId;
    }

    public void setOrganizationTypeId(Integer organizationTypeId) {
        this.organizationTypeId = organizationTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getContractNumberPrefix() {
        return contractNumberPrefix;
    }

    public void setContractNumberPrefix(String contractNumberPrefix) {
        this.contractNumberPrefix = contractNumberPrefix;
    }

    public Boolean getIsSupportReferral() {
        return isSupportReferral;
    }

    public void setIsSupportReferral(Boolean isSupportReferral) {
        this.isSupportReferral = isSupportReferral;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
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
    
    public Boolean getIsAdministrative() {
		return isAdministrative;
	}

	public void setIsAdministrative(Boolean isAdministrative) {
		this.isAdministrative = isAdministrative;
	}

	public Integer getJudicialBoneAgeOrganizationId() {
		return judicialBoneAgeOrganizationId;
	}

	public void setJudicialBoneAgeOrganizationId(Integer judicialBoneAgeOrganizationId) {
		this.judicialBoneAgeOrganizationId = judicialBoneAgeOrganizationId;
	}

	public Integer getJudicialBoneAgeOrganizationTeamId() {
		return judicialBoneAgeOrganizationTeamId;
	}

	public void setJudicialBoneAgeOrganizationTeamId(Integer judicialBoneAgeOrganizationTeamId) {
		this.judicialBoneAgeOrganizationTeamId = judicialBoneAgeOrganizationTeamId;
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