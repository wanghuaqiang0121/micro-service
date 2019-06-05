package org.module.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity.Insert;

public class Recipient implements Serializable{
	private static final long serialVersionUID = -7837867198793219428L;
	private Integer id;
	@NotNull(message = "{recipient.type.not.null}", groups = { Insert.class })
	private Integer type;
	private String content;
	private Integer organizationId;
	private Integer organizationUserId;
	@NotBlank(message = "{recipient.called.number.not.blank}", groups = { Insert.class })
	private String calledNumber;
	private Integer consumeTimes;
	private Integer status;
	private Date sendDate;
	private Date createDate;
	private String messge;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getOrganizationUserId() {
		return organizationUserId;
	}

	public void setOrganizationUserId(Integer organizationUserId) {
		this.organizationUserId = organizationUserId;
	}

	public String getCalledNumber() {
		return calledNumber;
	}

	public void setCalledNumber(String calledNumber) {
		this.calledNumber = calledNumber;
	}

	public Integer getConsumeTimes() {
		return consumeTimes;
		/*int l = content.length();
		if (l <= 70) {
			return 1;
		} else {
			return (int) Math.ceil(l / (double) 67);
		}*/
	}

	public void setConsumeTimes(Integer consumeTimes) {
		this.consumeTimes = consumeTimes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMessge() {
		return messge;
	}

	public void setMessge(String messge) {
		this.messge = messge;
	}
}
