package org.module.organization.configure.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class OrganizationSite extends BaseEntity {
    private Integer id;

    private Integer organizationId;
    @Length(min = 0, max = 32, message = "{organization.site.name.length}", groups = { Insert.class})
    @NotBlank(message = "{Organization.site.name.not.blank}", groups = { Insert.class})
    private String name;
    //@NotBlank(message = "{Organization.site.picture.not.blank}", groups = { Insert.class})
    private String picture;
    @Length(min = 0, max = 32, message = "{organization.site.province.length}", groups = { Insert.class})
    @NotBlank(message = "{Organization.site.province.not.blank}", groups = { Insert.class})
    private String province;
    //@NotBlank(message = "{Organization.site.city.not.blank}", groups = { Insert.class})
    private String city;
    @NotBlank(message = "{Organization.site.area.not.blank}", groups = { Insert.class})
    @Length(min = 0, max = 32, message = "{organization.site.area.length}", groups = { Insert.class})
    private String area;
    @NotBlank(message = "{Organization.site.street.not.blank}", groups = { Insert.class})
    @Length(min = 0, max = 32, message = "{organization.site.street.length}", groups = { Insert.class})
    private String street;
    @NotBlank(message = "{Organization.site.address.not.blank}", groups = { Insert.class})
    @Length(min = 0, max = 32, message = "{organization.site.address.length}", groups = { Insert.class})
    private String address;
    @NotNull(message = "{Organization.site.lng.not.null}", groups = { Insert.class})
    private Double lng;
    @NotNull(message = "{Organization.site.lat.not.null}", groups = { Insert.class})
    private Double lat;
    
    private Date createDate;
    //@NotBlank(message = "{Organization.site.remark.not.blank}", groups = { Insert.class})
    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    @Override
   	@NotNull(message = "{page.empty}", groups = { SelectAll.class})
   	public Integer getPage() {
   		return super.getPage();
   	}

   	@Override
   	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class })
   	public Integer getPageSize() {
   		return super.getPageSize();
   	}
}