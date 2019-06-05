package org.module.organization.user.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.shape.Rectangle;

public class Organization extends BaseEntity {
    private Integer id;
    
    private String code;

    private Integer pid;
    
    private Integer organizationTypeId;


    private Integer createType;

    private String name;

    private Integer status;

    private String phone;

    private String logo;

    private String picture;

    private Date createDate;

    private String remark;

    private String province;
   	private String city;
   	private String area;
   	private String street;
   	private String community;
   	private String address;
   	private Double lng;
   	private Double lat;
   	private Integer minRaidus;
   	private Integer maxRaidus;
   	
   	public void setRectangle(Rectangle rectangle) {
	}
	public Rectangle getRectangle() {
		if (getLat() != null && getLng() != null && getMaxRaidus() != null) {
			SpatialContext geo = SpatialContext.GEO;
			Rectangle rectangle = (Rectangle) geo.getDistCalc().calcBoxByDistFromPt(geo.makePoint(lng, lat), maxRaidus, geo, null);
			return rectangle;
		}
		return null;
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

	public Integer getMinRaidus() {
		return minRaidus;
	}

	public void setMinRaidus(Integer minRaidus) {
		this.minRaidus = minRaidus;
	}

	public Integer getMaxRaidus() {
		return maxRaidus;
	}

	public void setMaxRaidus(Integer maxRaidus) {
		this.maxRaidus = maxRaidus;
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

   
    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class})
	public Integer getPageSize() {
		return super.getPageSize();
	}
}