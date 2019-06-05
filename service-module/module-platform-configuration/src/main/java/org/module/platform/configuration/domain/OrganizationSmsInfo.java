package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationSmsInfo extends BaseEntity {
    private Integer id;

    private Integer organizationId;

    private String applicationName;

    private String appKey;

    private String appSecret;

    private String sign;

    @NotNull(message = "{organization.totalFrequency.not.null}", groups = { Update.class})
    private Integer totalFrequency;

    private Integer useFrequency;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getTotalFrequency() {
        return totalFrequency;
    }

    public void setTotalFrequency(Integer totalFrequency) {
        this.totalFrequency = totalFrequency;
    }

    public Integer getUseFrequency() {
        return useFrequency;
    }

    public void setUseFrequency(Integer useFrequency) {
        this.useFrequency = useFrequency;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}