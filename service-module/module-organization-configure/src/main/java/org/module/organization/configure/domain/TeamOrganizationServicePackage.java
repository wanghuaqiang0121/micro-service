package org.module.organization.configure.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class TeamOrganizationServicePackage extends BaseEntity {
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月21日
	 * @Version TeamOrganizationServicePackage
	 * @Description 上架校验
	 */
	public interface Onshelves{};
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月21日
	 * @Version TeamOrganizationServicePackage
	 * @Description 下架校验
	 */
	public interface Offshelves{};
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月21日
	 * @Version TeamOrganizationServicePackage
	 * @Description 机构服务包对应团队授权验证
	 */
	public interface OrganizationServicePackageTeamAuthorize{};
	
    private Integer id;

    @NotNull(message = "{team.organization.service.package.doctor.team.id.is.not.null}" , groups = {Insert.class,Delete.class,SelectAll.class})
    private Integer doctorTeamId;
    
    @NotNull(message = "{team.organization.service.package.organization.service.package.id.is.not.null}" , groups = {Insert.class,Delete.class,OrganizationServicePackageTeamAuthorize.class})
    private Integer organizationServicePackageId;
    
    @NotNull(message = "{team.organization.service.package.price.is.not.null}" , groups = {Insert.class})
    private BigDecimal price;
    
    //@NotNull(message = "{team.organization.service.package.status.is.not.null}" , groups = {Insert.class})
    private Integer status;

    private Date createDate;

    private OrganizationTeam organizationTeam;
    
    private String remark;

    public OrganizationTeam getOrganizationTeam() {
		return organizationTeam;
	}

	public void setOrganizationTeam(OrganizationTeam organizationTeam) {
		this.organizationTeam = organizationTeam;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorTeamId() {
        return doctorTeamId;
    }

    public void setDoctorTeamId(Integer doctorTeamId) {
        this.doctorTeamId = doctorTeamId;
    }

    public Integer getOrganizationServicePackageId() {
        return organizationServicePackageId;
    }

    public void setOrganizationServicePackageId(Integer organizationServicePackageId) {
        this.organizationServicePackageId = organizationServicePackageId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
    @Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,OrganizationServicePackageTeamAuthorize.class})
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class ,OrganizationServicePackageTeamAuthorize.class})
	public Integer getPageSize() {
		return super.getPageSize();
	}
}