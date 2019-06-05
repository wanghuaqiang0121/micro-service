package org.module.bone.age.domain;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.module.bone.age.domain.User.addMember;
import org.module.bone.age.domain.User.registerUser;
import org.service.core.entity.BaseEntity;

public class UserCertificate extends BaseEntity {
    private Integer id;

    private Integer userId;
    @NotBlank(message = "{user.certificate.type.notnull.valid}", groups = { addMember.class,registerUser.class})
    private String certificateType;
    @NotBlank(message = "{user.certificate.number.notnull.valid}", groups = { addMember.class ,registerUser.class})
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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