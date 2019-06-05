package org.module.organization.configure.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月20日
 * @Version
 * @Description 机构团队表
 */
public class OrganizationTeam extends BaseEntity {
	public interface validTeamNameRepeat {

	}

	private Integer id;

	private Integer organizationId;
	//@NotNull(message = "{organization.team.organization.site.id.valid}", groups = { Insert.class })
	private Integer organizationSiteId;
	@Length(min = 0, max = 128, message = "{organization.team.name.length}", groups = { Insert.class})
	@NotBlank(message = "{organization.team.name.notblank.valid}", groups = { Insert.class,validTeamNameRepeat.class })
	private String name;

	private String logo;

	private String picture;

	private Integer status;

	private Date createDate;
	
	private String phone;
	
	private String remark;

	private String experties;

	private String protocol;
	@NotNull(message = "{organization.team.organization.user.id.valid}", groups = { Insert.class })
	private Integer organizationUserId;
	
	private OrganizationSite organizationSite;
	
	private Organization organization;

	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getOrganizationSiteId() {
		return organizationSiteId;
	}

	public void setOrganizationSiteId(Integer organizationSiteId) {
		this.organizationSiteId = organizationSiteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getExperties() {
		return experties;
	}

	public void setExperties(String experties) {
		this.experties = experties;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Integer getOrganizationUserId() {
		return organizationUserId;
	}

	public void setOrganizationUserId(Integer organizationUserId) {
		this.organizationUserId = organizationUserId;
	}

	
	public OrganizationSite getOrganizationSite() {
		return organizationSite;
	}

	public void setOrganizationSite(OrganizationSite organizationSite) {
		this.organizationSite = organizationSite;
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
