package org.module.user.domain.itv;

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
	private Integer departmentId;	
	@NotNull(message="{doctor.organization.department.duty.organizationId.is.not.null}",groups={SelectAll.class})
	private Integer organizationId;
	private Integer organizationUserId;
	private String post;
	private String remark;
	

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
