package org.module.platform.configuration.domain.organization;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月22日
 * @Version
 * @Description 医生信息表
 */
public class DoctorInfo extends BaseEntity {
	
	private Integer id;
	private Integer organizationUserId;
	//@NotNull(message = "{doctor.info.doctor.title.id.notnull.valid}", groups = { Insert.class,InsertOrganizationUser.class})
	private Integer doctorTitleId;
	private Integer grade;
	//@Length(min = 0, max = 16, message = "{doctor.info.name.length}", groups = { Insert.class,InsertOrganizationUser.class })
	//@NotBlank(message = "{doctor.info.name.notblank.valid}", groups = { Insert.class,InsertOrganizationUser.class})
	private String name;
	private String school;
	private String certification;
	private String remark;
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

	public Integer getDoctorTitleId() {
		return doctorTitleId;
	}

	public void setDoctorTitleId(Integer doctorTitleId) {
		this.doctorTitleId = doctorTitleId;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
