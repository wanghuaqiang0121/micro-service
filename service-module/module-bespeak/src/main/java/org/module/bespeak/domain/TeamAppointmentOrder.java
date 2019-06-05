package org.module.bespeak.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TeamAppointmentOrder extends BaseEntity {
    private Integer id;

    private Integer appointmentNo;

    @NotNull(message = "{user.id.not.null.valid}", groups = {Insert.class})
    private Integer userId;
    @NotNull(message = "{user.service.id.not.null.valid}", groups = {Insert.class})
    private Integer userServiceId;
    @NotNull(message = "{teamAppointmentConfigureDetailId.not.null.valid}", groups = {Insert.class})
    private Integer teamAppointmentConfigureDetailId;
    @NotNull(message = "{appointmentDate.not.null.valid}", groups = {Insert.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date appointmentDate;

    private Integer status;

    private Date createDate;

    public Integer getUserServiceId() {
		return userServiceId;
	}

	public void setUserServiceId(Integer userServiceId) {
		this.userServiceId = userServiceId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(Integer appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTeamAppointmentConfigureDetailId() {
        return teamAppointmentConfigureDetailId;
    }

    public void setTeamAppointmentConfigureDetailId(Integer teamAppointmentConfigureDetailId) {
        this.teamAppointmentConfigureDetailId = teamAppointmentConfigureDetailId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
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
   	@NotNull(message = "{page.empty}", groups = { SelectAll.class})
   	public Integer getPage() {
   		return super.getPage();
   	}

   	@Override
   	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class})
   	public Integer getPageSize() {
   		return super.getPageSize();
   	}
}