package org.module.organization.configure.domain;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.module.organization.configure.domain.doctor.OrganizationUser.InsertOrganizationUser;
import org.service.core.entity.BaseEntity;

public class OrganizationUserCertificate extends BaseEntity {
    private Integer id;

    private Integer organizationUserId;
    
    @NotBlank(message = "{organization.user.certificateType.notblank.valid}", groups = { InsertOrganizationUser.class })
    private String certificateType;
    
    @NotBlank(message = "{organization.user.certificateNumber.notblank.valid}", groups = { InsertOrganizationUser.class })
    private String certificateNumber;

    private String positive;

    private String opposite;

    private String images;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationUserId() {
        return organizationUserId;
    }

    public void setOrganizationUserId(Integer organizationUserId) {
        this.organizationUserId = organizationUserId;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getOpposite() {
        return opposite;
    }

    public void setOpposite(String opposite) {
        this.opposite = opposite;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
    
}