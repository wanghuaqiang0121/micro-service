package org.module.organization.configure.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class OrganizationServicePackage extends BaseEntity {
	
	public interface validOrganizationPackageName {

	}

	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationServicePackage
	 * @Description 修改用户协议验证
	 */
	public interface UpdateProtocol{};
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationServicePackage
	 * @Description 查询父机构包列表检验类
	 */
	public interface ParentOrganizationPackages{};
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationServicePackage
	 * @Description 复制自己机构服务包验证类
	 */
	public interface CopyOwnOrganizationPackage{};
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationServicePackage
	 * @Description 复制上级机构服务包验证类
	 */
	public interface CopyParentOrganizationPackage{};
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月23日
	 * @Version OrganizationServicePackage
	 * @Description 审核通过验证类
	 */
	public interface Pass{};
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月23日
	 * @Version OrganizationServicePackage
	 * @Description 审核中
	 */
	public interface Examine{};
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月23日
	 * @Version OrganizationServicePackage
	 * @Description 审核不通过验证类
	 */
	public interface NotPass{};
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月23日
	 * @Version OrganizationServicePackage
	 * @Description 上架过验证类
	 */
	public interface Shelves{};
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月23日
	 * @Version OrganizationServicePackage
	 * @Description 下架过验证类
	 */
	public interface OffShelf{};
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月23日
	 * @Version OrganizationServicePackage
	 * @Description 需要本机构审核的包验证类
	 */
	public interface NeedAuditPackages{};
	
    private Integer id;

    
    private Integer pid;
    
    private Integer organizationId;

    @NotNull(message = "{organization.service.package.audit.organization.id.is.not.null}", groups = {Insert.class,CopyOwnOrganizationPackage.class})
    private Integer auditOrganizationId;
    @NotNull(message = "{organization.service.package.service.package.type.id.is.not.null}", groups = {Insert.class,validOrganizationPackageName.class })
    private Integer servicePackageTypeId;
    @Length(min = 0, max = 64, message = "{organization.service.package.name.length}", groups = { Insert.class})
    @NotBlank(message = "{organization.service.package.name.not.null}", groups = { Insert.class,validOrganizationPackageName.class })
    private String name;
    @NotNull(message = "{organization.service.package.price.not.null.valid}", groups = { Insert.class })
    private BigDecimal price;
    @NotNull(message = "{organization.service.package.originalPrice.not.null.valid}", groups = { Insert.class })
    private BigDecimal originalPrice;
    
    /*@NotNull(message = "{organization.service.package.discount.not.null.valid}", groups = { Insert.class })
    @Min(value=0,message = "{organization.service.package.discount.min.zero}", groups = { Insert.class })
    @Max(value=(long)(9.9) ,message = "{organization.service.package.discount.max.ten}", groups = { Insert.class })
    private Float discount;*/

    private Integer status;
    @NotNull(message = "{organization.service.package.acquisitive.type.not.null}", groups = { Insert.class })
    private Integer acquisitiveType;
    @NotNull(message = "{organization.service.package.acquisitive.not.null}", groups = { Insert.class })
    private Integer acquisitive;

    private String cover;

    private Date createDate;

    private String remark;

    @NotBlank(message = "{organization.service.package.protocol.is.not.null}",groups = {UpdateProtocol.class})
    private String protocol;
    
    @NotBlank(message = "{organization.service.package.auditRemark.is.not.null}",groups = {NotPass.class,Pass.class})
    private String auditRemark;

    private List<OrganizationPackageUserType> organizationPackageUserTypes;

	private List<OrganizationPackageService> packageServices;
	
	private Integer organizationTeamId;
	
    public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public List<OrganizationPackageUserType> getOrganizationPackageUserTypes() {
		return organizationPackageUserTypes;
	}

	public void setOrganizationPackageUserTypes(List<OrganizationPackageUserType> organizationPackageUserTypes) {
		this.organizationPackageUserTypes = organizationPackageUserTypes;
	}

	public List<OrganizationPackageService> getPackageServices() {
		return packageServices;
	}

	public void setPackageServices(List<OrganizationPackageService> packageServices) {
		this.packageServices = packageServices;
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

    public Integer getAuditOrganizationId() {
        return auditOrganizationId;
    }

    public void setAuditOrganizationId(Integer auditOrganizationId) {
        this.auditOrganizationId = auditOrganizationId;
    }

    public Integer getServicePackageTypeId() {
        return servicePackageTypeId;
    }

    public void setServicePackageTypeId(Integer servicePackageTypeId) {
        this.servicePackageTypeId = servicePackageTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

 /*   public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }*/

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAcquisitiveType() {
        return acquisitiveType;
    }

    public void setAcquisitiveType(Integer acquisitiveType) {
        this.acquisitiveType = acquisitiveType;
    }

    public Integer getAcquisitive() {
        return acquisitive;
    }

    public void setAcquisitive(Integer acquisitive) {
        this.acquisitive = acquisitive;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    public Integer getOrganizationTeamId() {
		return organizationTeamId;
	}

	public void setOrganizationTeamId(Integer organizationTeamId) {
		this.organizationTeamId = organizationTeamId;
	}

	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,ParentOrganizationPackages.class,NeedAuditPackages.class})
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,ParentOrganizationPackages.class,NeedAuditPackages.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}