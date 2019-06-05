package org.module.organization.configure.domain.doctor;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;
/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月19日
 * @Version 
 * @Description  机构用户、机构、部门关联表
 */
public class DoctorOrganizationDepartmentDuty extends BaseEntity {
	public  interface GetOrganizationUserNotTeamMember{};
	
	private Integer id;
	@NotNull(message = "{doctor.organization.department.duty.organization.user.id.notnull.valid}", groups = { Insert.class})
	private Integer organizationUserId;
	
	private Integer departmentId;
	
	private Integer organizationId;
	
	private Integer status;
	
	private String post;
	
	private Boolean isManager;
	
	private String remark;
	
	private String authorizeAptitude;
	@NotNull(message = "{doctor.organization.department.duty.organization.team.id.notnull.valid}", groups = { GetOrganizationUserNotTeamMember.class})
	private Integer organizationTeamId;
	private OrganizationUser organizationUser;
	private Integer organizationUserStatus;
	private DoctorInfo doctorInfo;
	
	private Boolean isLocal;
	
	
    private String province;
    private String city;
    private String area;
    private String street;
    
    private String workNumber;
	
	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getOrganizationUserId() {
		return organizationUserId;
	}

	public void setOrganizationUserId(Integer organizationUserId) {
		this.organizationUserId = organizationUserId;
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

	public Integer getOrganizationTeamId() {
		return organizationTeamId;
	}

	public void setOrganizationTeamId(Integer organizationTeamId) {
		this.organizationTeamId = organizationTeamId;
	}

	public OrganizationUser getOrganizationUser() {
		return organizationUser;
	}

	public void setOrganizationUser(OrganizationUser organizationUser) {
		this.organizationUser = organizationUser;
	}

	public Boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}

	public Integer getOrganizationUserStatus() {
		return organizationUserStatus;
	}

	public void setOrganizationUserStatus(Integer organizationUserStatus) {
		this.organizationUserStatus = organizationUserStatus;
	}

	public DoctorInfo getDoctorInfo() {
		return doctorInfo;
	}

	public void setDoctorInfo(DoctorInfo doctorInfo) {
		this.doctorInfo = doctorInfo;
	}

	public String getAuthorizeAptitude() {
		return authorizeAptitude;
	}

	public void setAuthorizeAptitude(String authorizeAptitude) {
		this.authorizeAptitude = authorizeAptitude;
	}

	
	public Boolean getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(Boolean isLocal) {
		this.isLocal = isLocal;
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
