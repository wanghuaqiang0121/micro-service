package org.module.organization.configure.domain.doctor;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.module.organization.configure.domain.OrganizationUserCertificate;
import org.service.core.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月22日
 * @Version
 * @Description 机构用户表
 */
public class OrganizationUser extends BaseEntity {
	public interface validCertificateRepeat {

	}

	public interface AccountRepeat {}

	public interface PhoneRepeat {};

	/**
	 * 
	 * @author <font color="red"><b>Chen.Yan</b></font>
	 * @Date 2018年3月23日
	 * @Version OrganizationUser
	 * @Description 新增机构用户必填信息
	 */
	public interface InsertOrganizationUser{};

	private Integer id;
	@Length(min = 0, max = 16, message = "{organization.user.name.length}", groups = { Insert.class,InsertOrganizationUser.class })
	@NotBlank(message = "{organization.user.name.notblank.valid}", groups = { Insert.class,InsertOrganizationUser.class})
	private String name;	
	@NotNull(message = "{organization.user.sex.notnull.valid}", groups = { Insert.class,InsertOrganizationUser.class })
	private Integer sex;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@NotBlank(message = "{organization.user.phone.notblank.valid}", groups = { Insert.class,InsertOrganizationUser.class,PhoneRepeat.class })
	private String phone;
	private String photo;
	private Integer phoneStatus;
	private Integer status;
	@Length(min = 0, max = 64, message = "organization.user.account.length}", groups = { Insert.class,InsertOrganizationUser.class })
	@NotBlank(message = "{organization.user.account.notblank.valid}", groups = { Insert.class,InsertOrganizationUser.class,AccountRepeat.class })
	private String account;
	private String password;
	private boolean isInitPassword;
	private Date createDate;
	private String remark;
	private String post;
	private Integer departmentId;
    //@Valid
	//@NotNull(message = "{organization.user.doctor.info.notnull.valid}", groups = { InsertOrganizationUser.class})
	private DoctorInfo doctorInfo;
    //@Valid
   	//@NotNull(message = "{organization.user.doctor.organization.department.duty.notnull.valid}", groups = { InsertOrganizationUser.class})
	private DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty;
   
    @Valid
    @NotNull(message = "{organization.user.organizationUserCertificates.notnull.valid}", groups = { InsertOrganizationUser.class,validCertificateRepeat.class})
    private List<OrganizationUserCertificate> organizationUserCertificates;
    
	private Integer organizationId;
	
	
	@NotNull(message = "{organization.user.person.type.id.notnull.valid}", groups = { Insert.class,InsertOrganizationUser.class })
	private Integer organizationPersonTypeId;
	private String certificateType;
	private String certificateTypeName;
	private String certificateNumber;
	private String positive;
	private String opposite;
	
	 private String workNumber;
	 
	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public String getPost() {
		return post;
	}
	
	public String getCertificateTypeName() {
		return certificateTypeName;
	}

	public void setCertificateTypeName(String certificateTypeName) {
		this.certificateTypeName = certificateTypeName;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public List<OrganizationUserCertificate> getOrganizationUserCertificates() {
		return organizationUserCertificates;
	}

	public void setOrganizationUserCertificates(List<OrganizationUserCertificate> organizationUserCertificates) {
		this.organizationUserCertificates = organizationUserCertificates;
	}

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

	

	public Integer getOrganizationPersonTypeId() {
		return organizationPersonTypeId;
	}

	public void setOrganizationPersonTypeId(Integer organizationPersonTypeId) {
		this.organizationPersonTypeId = organizationPersonTypeId;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isInitPassword() {
		return isInitPassword;
	}

	public void setInitPassword(boolean isInitPassword) {
		this.isInitPassword = isInitPassword;
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
	

	public DoctorInfo getDoctorInfo() {
		return doctorInfo;
	}

	public void setDoctorInfo(DoctorInfo doctorInfo) {
		this.doctorInfo = doctorInfo;
	}

	public DoctorOrganizationDepartmentDuty getDoctorOrganizationDepartmentDuty() {
		return doctorOrganizationDepartmentDuty;
	}

	public void setDoctorOrganizationDepartmentDuty(DoctorOrganizationDepartmentDuty doctorOrganizationDepartmentDuty) {
		this.doctorOrganizationDepartmentDuty = doctorOrganizationDepartmentDuty;
	}

	public Integer getPhoneStatus() {
		return phoneStatus;
	}

	public void setPhoneStatus(Integer phoneStatus) {
		this.phoneStatus = phoneStatus;
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
