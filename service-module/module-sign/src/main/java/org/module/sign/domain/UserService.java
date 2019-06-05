package org.module.sign.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.module.sign.domain.WorkOrder.GetReservationNumber;
import org.service.core.entity.BaseEntity;

public class UserService extends BaseEntity {
    private Integer id;

    private Integer userId;

    private Integer userServicePackageOrderId;
    
    @NotNull(message = "{user.service.service.type.not.null}", groups = { GetReservationNumber.class})
    private Integer serviceTypeId;

    private Integer times;

    private Integer lockTimes;

    private Integer useTimes;

    private Date createTime;

    private Date updateTime;

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
}