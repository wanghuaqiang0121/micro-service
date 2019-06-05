package org.module.user.domain.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class UserServicePackageOrder extends BaseEntity {
    /**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年3月28日
     * @Version UserServicePackageOrder
     * @Description 取消订单
     */
    public interface refuseOrder {

	}

	/**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年3月28日
     * @Version UserServicePackageOrder
     * @Description 确认订单
     */
    public interface sureOrder {

	}

	/**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年3月28日
     * @Version UserServicePackageOrder
     * @Description 线上新增订单
     */
    public interface onlineOrder {

	}

	private Integer id;
	@NotNull(message = "{user.id.not.null.valid}", groups = { onlineOrder.class})
    private Integer userId;
	@NotNull(message = "{organization.doctor.team.service.package.id.notnull.valid}", groups = { 
			onlineOrder.class})
    private Integer teamOrganizationServicePackageId;
    
    private Integer organizationServicePackageId;

    private Integer doctorTeamId;

    private String orderNo;

    private BigDecimal price;

    private String evidenceSn;

    private String evidenceFile;

    private Integer type;

    private Integer status;

    private Integer[] statuss;
    
    private Date createDate;

    private Date expireDate;

    private Date completeDate;

    private String remark;
    
    private String merchantNumber;
    private String appId;
    private String appSecret;
    
    private String payKey;
    
    private String wechatId;
    
    private String wechatKey;

    /* 调用微信查询订单接口需要的参数 */
    /* 公众账号ID */
    private String appid;
    /* 商户号 */
    private String mch_id;
    /* 微信订单号 */
    private String transaction_id;
    /* 随机字符串 */
    private String nonce_str;
    /* 签名 */
    private String sign;
    /* 签名类型 */
    private String sign_type;
    
    private String data;
    
    private String key;
    
    
    
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
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

    public Integer getTeamOrganizationServicePackageId() {
		return teamOrganizationServicePackageId;
	}

	public void setTeamOrganizationServicePackageId(Integer teamOrganizationServicePackageId) {
		this.teamOrganizationServicePackageId = teamOrganizationServicePackageId;
	}

	public Integer getOrganizationServicePackageId() {
        return organizationServicePackageId;
    }

    public void setOrganizationServicePackageId(Integer organizationServicePackageId) {
        this.organizationServicePackageId = organizationServicePackageId;
    }

    public Integer getDoctorTeamId() {
        return doctorTeamId;
    }

    public void setDoctorTeamId(Integer doctorTeamId) {
        this.doctorTeamId = doctorTeamId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getEvidenceSn() {
        return evidenceSn;
    }

    public void setEvidenceSn(String evidenceSn) {
        this.evidenceSn = evidenceSn;
    }

    public String getEvidenceFile() {
        return evidenceFile;
    }

    public void setEvidenceFile(String evidenceFile) {
        this.evidenceFile = evidenceFile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer[] getStatuss() {
		return statuss;
	}

	public void setStatuss(Integer[] statuss) {
		this.statuss = statuss;
	}

	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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