package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class UserType extends BaseEntity {

    private Integer id;
    @Length(min = 0, max = 32, message = "{user.type.name.length}", groups = { Insert.class})
    @NotBlank(message = "{user.type.name.not.null.valid}", groups = { Insert.class })
    private String name;
  
    @NotNull(message = "{user.type.apply.type.not.null.valid}", groups = { Insert.class })
    private Integer applyType;
  
    @NotNull(message = "{userType.isUsed.not.null.valid}", groups = { Insert.class })
    private Boolean isUsed;

    private Date createDate;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
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