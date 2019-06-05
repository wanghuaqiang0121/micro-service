package org.module.work.order.domain;

import java.util.Date;

import org.service.core.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

public class WorkOrderLog extends BaseEntity {
    private Integer id;

    private Integer logType;
    
    private Integer launchType;

    private Integer workOrderId;

    private Integer operationUserId;

    private Integer status;

    private String record;
    
   	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public Integer getLaunchType() {
		return launchType;
	}

	public void setLaunchType(Integer launchType) {
		this.launchType = launchType;
	}

	public Integer getOperationUserId() {
		return operationUserId;
	}

	public void setOperationUserId(Integer operationUserId) {
		this.operationUserId = operationUserId;
	}

	public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}