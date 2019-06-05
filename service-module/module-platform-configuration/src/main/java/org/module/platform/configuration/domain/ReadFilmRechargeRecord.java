package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

/**
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2019年2月25日
 * @Version 
 * @Description 远程阅片充值记录
 */
public class ReadFilmRechargeRecord extends BaseEntity{

	private static final long serialVersionUID = -1840644719194831913L;
	
	private Integer id;
	@NotNull(message = "{read.film.recharge.record.organization.id.not.null.valid}", groups = { Insert.class })
	private Integer organizationId;
	
	@NotNull(message = "{read.film.recharge.record.recharge.money.not.null.valid}", groups = { Insert.class })
	private Double rechargeMoney;
	
	private Double lockMoney;
	@NotBlank(message = "{read.film.recharge.record.invoice.number.not.blank.valid}", groups = { Insert.class })
	private String invoiceNumber;
	@NotNull(message = "{read.film.recharge.record.status.not.null.valid}", groups = { Insert.class })
	private Boolean status;
	private String remark;
	private Double remainingSum;
	
	private Date  createTime;
	private String organizationName;
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
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Double getRechargeMoney() {
		return rechargeMoney;
	}
	public void setRechargeMoney(Double rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Double getRemainingSum() {
		return remainingSum;
	}
	public void setRemainingSum(Double remainingSum) {
		this.remainingSum = remainingSum;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Double getLockMoney() {
		return lockMoney;
	}
	public void setLockMoney(Double lockMoney) {
		this.lockMoney = lockMoney;
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
