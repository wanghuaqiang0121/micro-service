package org.module.user.domain.itv;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class UserItvAccount extends BaseEntity {
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年4月9日
	 * @Version UserItvAccount
	 * @Description itv绑定用户列表校验
	 */
	public interface ItvUsers{};
	
	public interface Login{};
	
	public interface ValidToken{};
	
	public interface ValidPassword{};
	
	public interface Unbind{};
	
	public interface LoginOut{};
	
    private Integer id;
    //@NotNull(message = "{user.itv.account.userId.is.not.null}", groups = {Login.class,ValidToken.class,ValidPassword.class} )
    private Integer userId;
    @Length(min = 0, max = 64, message = "{user.itv.account.itvSn.length}", groups = { Insert.class})
    @NotBlank(message = "{user.itv.account.itvSn.is.not.null}", groups = {Login.class,ItvUsers.class,Insert.class,ValidPassword.class} )
    private String itvSn;
    @Length(min = 0, max = 32, message = "{user.itv.account.password.length}", groups = { Insert.class})
    @NotBlank(message = "{user.itv.account.password.is.not.null}", groups = {Insert.class,ValidPassword.class} )
    private String password;

    @NotBlank(message = "{user.itv.account.validCode.is.not.null}", groups = { Insert.class})
	private String validCode;
    
    private Integer status;

    private Date createDate;

    @NotBlank(message = "{user.itv.account.phone.is.not.null}", groups = { Insert.class})
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,ItvUsers.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,ItvUsers.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}

}