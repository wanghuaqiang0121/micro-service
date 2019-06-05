package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class DoctorLevel extends BaseEntity {
    private Integer id;
    @NotNull(message = "{doctor.level.doctor.category.id.NotNull.valid}", groups = { Insert.class})
    private Integer doctorCategoryId;
    @NotBlank(message = "{doctor.level.doctor.name.id.NotNull.valid}", groups = { Insert.class})
    private String name;
    private String remark;

    private Date createDate;

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorCategoryId() {
        return doctorCategoryId;
    }

    public void setDoctorCategoryId(Integer doctorCategoryId) {
        this.doctorCategoryId = doctorCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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