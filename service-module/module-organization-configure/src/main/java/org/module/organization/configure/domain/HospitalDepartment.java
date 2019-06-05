package org.module.organization.configure.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年8月14日
 * @Version 
 * @Description 医院科室表
 */
public class HospitalDepartment extends BaseEntity {
    public interface Repeat {}

	private Integer id;

    private Integer medicalOrganizationInfoId;

    @NotBlank(message = "{hospital.department.name.notblank.valid}", groups = { Insert.class,Repeat.class})
    private String name;

    private Date createDate;

    private String remark;
    
    private Integer organizationId;
    
    @NotNull(message = "{hospital.department.status.notblank.valid}", groups = { Insert.class})
    private Integer status;
    

    public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedicalOrganizationInfoId() {
        return medicalOrganizationInfoId;
    }

    public void setMedicalOrganizationInfoId(Integer medicalOrganizationInfoId) {
        this.medicalOrganizationInfoId = medicalOrganizationInfoId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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