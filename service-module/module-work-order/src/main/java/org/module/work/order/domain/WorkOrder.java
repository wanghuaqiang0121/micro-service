package org.module.work.order.domain;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

public class WorkOrder extends BaseEntity {
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月27日
	 * @Version WorkOrder
	 * @Description 工单每天预约人数
	 */
	public interface GetReservationNumber{};
	
    private Integer id;
    @NotNull(message = "{user.id.not.null.valid}", groups = { Insert.class,SelectAll.class})
    private Integer userId;
    
    private Integer launchUserId;
    
    @NotNull(message="{work.order.user.service.id.not.null}",groups={ Insert.class})
    private Integer userServiceId;

    //@NotNull(message = "{work.order.rganization.team.id.not.null.valid}", groups = { Insert.class})
    private Integer organizationTeamId;

    private Integer organizationUserId;

    private String number;

    private Integer orderSources;

    @NotNull(message = "{work.order.appointment.date.not.null.valid}", groups = { Insert.class})
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;
    
   	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointmentEndDate;

    private String remark;

    private Integer status;
    
    private Integer[] statuss;

    @NotNull(message = "{work.order.evaluate.star.not.null.valid}", groups = { Update.class})
    private Float evaluateStar;

    private String evaluateText;

    private Date evaluateDate;

    private String closeReasonCode;

    private String closeReason;

    private String notice;
    
   	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    
   	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

    private User user;
    
    @Valid
    @NotNull(message = "{work.order.userService.not.null.valid}", groups = { GetReservationNumber.class})
    private UserService userService;
    
    private OrganizationServicePackage organizationServicePackage;
    
    public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public OrganizationServicePackage getOrganizationServicePackage() {
		return organizationServicePackage;
	}

	public void setOrganizationServicePackage(OrganizationServicePackage organizationServicePackage) {
		this.organizationServicePackage = organizationServicePackage;
	}

	public Integer getLaunchUserId() {
		return launchUserId;
	}

	public void setLaunchUserId(Integer launchUserId) {
		this.launchUserId = launchUserId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer[] getStatuss() {
		return statuss;
	}

	public void setStatuss(Integer[] statuss) {
		this.statuss = statuss;
	}

	public Date getAppointmentEndDate() {
		return appointmentEndDate;
	}

	public void setAppointmentEndDate(Date appointmentEndDate) {
		this.appointmentEndDate = appointmentEndDate;
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

    public Integer getUserServiceId() {
        return userServiceId;
    }

    public void setUserServiceId(Integer userServiceId) {
        this.userServiceId = userServiceId;
    }

    public Integer getOrganizationTeamId() {
        return organizationTeamId;
    }

    public void setOrganizationTeamId(Integer organizationTeamId) {
        this.organizationTeamId = organizationTeamId;
    }

    public Integer getOrganizationUserId() {
        return organizationUserId;
    }

    public void setOrganizationUserId(Integer organizationUserId) {
        this.organizationUserId = organizationUserId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getOrderSources() {
        return orderSources;
    }

    public void setOrderSources(Integer orderSources) {
        this.orderSources = orderSources;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getEvaluateStar() {
        return evaluateStar;
    }

    public void setEvaluateStar(Float evaluateStar) {
        this.evaluateStar = evaluateStar;
    }

    public String getEvaluateText() {
        return evaluateText;
    }

    public void setEvaluateText(String evaluateText) {
        this.evaluateText = evaluateText;
    }

    public Date getEvaluateDate() {
        return evaluateDate;
    }

    public void setEvaluateDate(Date evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    public String getCloseReasonCode() {
        return closeReasonCode;
    }

    public void setCloseReasonCode(String closeReasonCode) {
        this.closeReasonCode = closeReasonCode;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    @Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,GetReservationNumber.class})
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,GetReservationNumber.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}