package org.module.organization.user.domain;

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
	
	private Integer id;
	@NotNull(message = "{doctor.organization.department.duty.department.id.notnull.valid}", groups = { Insert.class})
	private Integer departmentId;	
	private Integer organizationId;
	@NotNull(message = "{doctor.organization.department.duty.organization.user.id.notnull.valid}", groups = { Insert.class})
	private Integer organizationUserId;
	private String post;
	private String remark;
	private Integer organizationTeamId;
	private OrganizationUser organizationUser;

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
