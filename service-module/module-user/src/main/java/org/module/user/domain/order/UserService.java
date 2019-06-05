package org.module.user.domain.order;

import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.module.user.domain.User;
import org.service.core.entity.BaseEntity;

public class UserService extends BaseEntity {
    public interface SelectServices {

	}

	private Integer id;

    @NotNull(message = "{user.service.userId.is.not.null}",groups={SelectAll.class})
    private Integer userId;

    private Integer userServicePackageOrderId;

    @NotNull(message = "{user.service.serviceTypeId.is.not.null}",groups={SelectServices.class})
    private Integer serviceTypeId;
    
    private String serviceTypeCode;
    
    private String businessCode;
    
    private String name;
 
    private Integer times;

    private Integer lockTimes;

    private Integer useTimes;

    private Date createTime;

    private Date updateTime;

    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
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

    public Integer getUserServicePackageOrderId() {
        return userServicePackageOrderId;
    }

    public void setUserServicePackageOrderId(Integer userServicePackageOrderId) {
        this.userServicePackageOrderId = userServicePackageOrderId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getLockTimes() {
        return lockTimes;
    }

    public void setLockTimes(Integer lockTimes) {
        this.lockTimes = lockTimes;
    }

    public Integer getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(Integer useTimes) {
        this.useTimes = useTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    @Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,SelectServices.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,SelectServices.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}

}