package org.module.work.order.domain;

import java.util.Date;

import org.service.core.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

public class UserSignLog extends BaseEntity {
    private Integer id;

    private Integer type;

    private Integer userSignId;

    private Integer organizationUserId;

    private Integer status;

    private String explain;

    private String record;
    
   	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserSignId() {
        return userSignId;
    }

    public void setUserSignId(Integer userSignId) {
        this.userSignId = userSignId;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}