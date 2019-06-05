package org.module.platform.configuration.domain.organization;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.module.platform.configuration.domain.organization.OrganizationUser.InsertOrganizationUser;
import org.service.core.entity.BaseEntity;


/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年7月19日
 * @Version
 * @Description 机构用户、机构、部门关联表
 */
public class DoctorOrganizationDepartmentDuty extends BaseEntity {

	private Integer id;
	//@NotNull(message = "{doctor.organization.department.duty.department.id.notnull.valid}", groups = { Insert.class})
	private Integer departmentId;
	@NotNull(message = "{doctor.organization.department.duty.organization.id.not.null.valid}", groups = { InsertOrganizationUser.class})
	private Integer organizationId;
	@NotNull(message = "{doctor.organization.department.duty.organization.user.id.notnull.valid}", groups = {
			Insert.class })
	private Integer organizationUserId;
	
	private Integer status;
	
	private String post;
	private String remark;
	@NotBlank(message = "{doctor.organization.department.duty.authorize.aptitude.not.null.valid}", groups = { InsertOrganizationUser.class})
	private String authorizeAptitude;
	private Boolean isManager;
	private Boolean isLocal;
	private String workNumber;
	
	
	public Boolean getIsLocal() {
		return isLocal;
	}
	public void setIsLocal(Boolean isLocal) {
		this.isLocal = isLocal;
	}
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
	public String getAuthorizeAptitude() {
		return authorizeAptitude;
	}
	public void setAuthorizeAptitude(String authorizeAptitude) {
		this.authorizeAptitude = authorizeAptitude;
	}
	public Boolean getIsManager() {
		return isManager;
	}
	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}
	
	

}
