package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationWechatInfo extends BaseEntity {
    private Integer id;
    @NotNull(message = "{organization.wechat.info.organization.id.not.null.valid}", groups = { Insert.class})
    private Integer organizationId;

    private String merchantNumber;
    
    //@NotBlank(message = "{organization.wechat.info.wechat.name.notblank.valid}", groups = { Insert.class})
    private String wechatName;
    
    private String appId;
    
    private String appSecret;

    private String payKey;

    private String wechatId;

    private String wechatKey;

    private Date createDate;
    
    private String verType;

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

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getWechatKey() {
        return wechatKey;
    }

    public void setWechatKey(String wechatKey) {
        this.wechatKey = wechatKey;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	
	public String getVerType() {
		return verType;
	}

	public void setVerType(String verType) {
		this.verType = verType;
	}

	@Override
   	@NotNull(message = "{page.empty}", groups = { SelectAll.class })
   	public Integer getPage() {
   		return super.getPage();
   	}

   	@Override
   	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class })
   	public Integer getPageSize() {
   		return super.getPageSize();
   	}
}