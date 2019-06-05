package org.module.organization.configure.domain.doctor;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;


/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月22日
 * @Version
 * @Description 医生信息表
 */
public class DoctorInfo extends BaseEntity {
	
	public interface ValidCertificationRepeat {

	}

	private Integer id;
	
	@NotNull(message = "{doctor.info.organization.user.id.notnull.valid}", groups = { Insert.class})
	private Integer organizationUserId;
	@NotNull(message = "{doctor.info.hospitalDepartmentId.notnull.valid}", groups = { Insert.class})
	private Integer hospitalDepartmentId;
	@NotNull(message = "{doctor.info.doctorLevelId.notnull.valid}", groups = { Insert.class})
	private Integer doctorLevelId;
	
	private String grade;
	//@Length(min = 0, max = 16, message = "{doctor.info.name.length}", groups = { Insert.class })
	//@NotBlank(message = "{doctor.info.name.notblank.valid}", groups = { Insert.class})
	private String name;
	private String school;
	@NotBlank(message = "{doctor.info.certification.notblank.valid}", groups = { ValidCertificationRepeat.class})
	private String certification;
	private String certificationPhoto;
	private Boolean isProfessor;
	private String post;
	private String remark;
	private Date createDate;
	
	private List<DoctorSkill> doctorSkills;
	
	
	public Boolean getIsProfessor() {
		return isProfessor;
	}

	public void setIsProfessor(Boolean isProfessor) {
		this.isProfessor = isProfessor;
	}

	public List<DoctorSkill> getDoctorSkills() {
		return doctorSkills;
	}

	public void setDoctorSkills(List<DoctorSkill> doctorSkills) {
		this.doctorSkills = doctorSkills;
	}

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

	public Integer getHospitalDepartmentId() {
		return hospitalDepartmentId;
	}

	public void setHospitalDepartmentId(Integer hospitalDepartmentId) {
		this.hospitalDepartmentId = hospitalDepartmentId;
	}

	public Integer getDoctorLevelId() {
		return doctorLevelId;
	}

	public void setDoctorLevelId(Integer doctorLevelId) {
		this.doctorLevelId = doctorLevelId;
	}


	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
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

	public String getCertificationPhoto() {
		return certificationPhoto;
	}

	public void setCertificationPhoto(String certificationPhoto) {
		this.certificationPhoto = certificationPhoto;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
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
