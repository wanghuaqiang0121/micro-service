package org.module.bespeak.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;


public class TeamAppointmentConfigure extends BaseEntity {
    private Integer id;

    private Integer appointmentTypeId;
    
    @NotNull(message = "{service.id.not.null.valid}", groups = {SelectOne.class})
    private Integer serviceId;	

    @NotNull(message = "{organization.team.id.not.null.valid}", groups = {SelectOne.class})
    private Integer organizationTeamId;

    private String name;

    private Integer cycle;

    private Integer status;

    private Date startDate;

    private Date endDate;
    
    /*@NotNull(message = "{appointment.date.not.null.valid}", groups = {SelectOne.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")*/
    private Date appointmentDate;
    
    private String strAppointmentDate;

    private Date createDate;

    private List<Integer> ids;
    
    
	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getStrAppointmentDate() {
		return strAppointmentDate;
	}

	public void setStrAppointmentDate(String strAppointmentDate) {
		this.strAppointmentDate = strAppointmentDate;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(Integer appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    public Integer getOrganizationTeamId() {
        return organizationTeamId;
    }

    public void setOrganizationTeamId(Integer organizationTeamId) {
        this.organizationTeamId = organizationTeamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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