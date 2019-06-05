package org.module.organization.configure.domain;

import java.util.Date;
import org.service.core.entity.BaseEntity;

public class OrganizationServicePackageLog extends BaseEntity {
    private Integer id;

    private Integer organizationServicePackageId;

    private Integer auditOrganizationId;

    private Integer organizationUserId;

    private Integer status;

    private Date createDate;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationServicePackageId() {
        return organizationServicePackageId;
    }

    public void setOrganizationServicePackageId(Integer organizationServicePackageId) {
        this.organizationServicePackageId = organizationServicePackageId;
    }

    public Integer getAuditOrganizationId() {
        return auditOrganizationId;
    }

    public void setAuditOrganizationId(Integer auditOrganizationId) {
        this.auditOrganizationId = auditOrganizationId;
    }

    public Integer getOrganizationUserId() {
        return organizationUserId;
    }

    public void setOrganizationUserId(Integer organizationUserId) {
        this.organizationUserId = organizationUserId;
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
}