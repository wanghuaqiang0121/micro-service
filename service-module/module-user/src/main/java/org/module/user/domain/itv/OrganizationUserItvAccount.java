package org.module.user.domain.itv;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class OrganizationUserItvAccount extends BaseEntity {
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年4月9日
	 * @Version UserItvAccount
	 * @Description itv绑定医生列表校验
	 */
	public interface ItvOrganizationUsers{};
	
	public interface validDoctorPassword{};
	
	public interface Login{};
	
	public interface ValidToken{};
	public interface Unbind{};

	
    private Integer id;
    @NotNull(message = "{organization.user.itv.account.organizationUserId.is.not.null}", groups = {ValidToken.class,Login.class,validDoctorPassword.class} )
    private Integer organizationUserId;
    @Length(min = 0, max = 64, message = "{organization.user.itv.account.itvSn.length}", groups = { Insert.class})
    @NotBlank(message = "{organization.user.itv.account.itvSn.is.not.null}", groups = {Login.class,ItvOrganizationUsers.class,Insert.class,validDoctorPassword.class} )
    private String itvSn;
    @Length(min = 0, max = 32, message = "{organization.user.itv.account.password.length}", groups = { Insert.class})
    @NotBlank(message = "{organization.user.itv.account.password.is.not.null}", groups = {Insert.class,validDoctorPassword.class} )
    private String password;

    private Integer status;

    private Date createDate;

    @NotBlank(message = "{organization.user.itv.account.validCode.is.not.null}", groups = { Insert.class})
	private String validCode;
    @NotBlank(message = "{organization.user.itv.account.phone.is.not.null}", groups = { Insert.class})
    private String phone;
    
    public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
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

    public Integer getOrganizationUserId() {
        return organizationUserId;
    }

    public void setOrganizationUserId(Integer organizationUserId) {
        this.organizationUserId = organizationUserId;
    }

    public String getItvSn() {
        return itvSn;
    }

    public void setItvSn(String itvSn) {
        this.itvSn = itvSn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Override
   	@NotNull(message = "{page.empty}", groups = { SelectAll.class,ItvOrganizationUsers.class })
   	public Integer getPage() {
   		return super.getPage();
   	}

   	@Override
   	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,ItvOrganizationUsers.class })
   	public Integer getPageSize() {
   		return super.getPageSize();
   	}
}